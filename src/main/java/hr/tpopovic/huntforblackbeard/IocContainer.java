package hr.tpopovic.huntforblackbeard;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class IocContainer {

    private static final Map<Class<?>, Object> classInstances = new HashMap<>();
    private static final Set<Class<?>> managedClasses = new HashSet<>();

    private IocContainer() {
    }

    public static void addClassToManage(Class<?> newClass) {
        managedClasses.add(newClass);
        managedClasses.addAll(List.of(newClass.getInterfaces()));
    }

    public static  <T> T getInstance(Class<T> type) {
        if (!managedClasses.contains(type)) {
            throw new IllegalArgumentException("Class or one of its dependencies isn't managed by the IoC container.");
        }

        Class<?> managedClass = type;
        if(type.isInterface()) {
            managedClass =  managedClasses.stream()
                    .filter(clazz -> !clazz.isInterface())
                    .filter(type::isAssignableFrom)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No implementation found for the interface: " + type.getName()));
        }

        if (!classInstances.containsKey(managedClass)) {
            classInstances.put(managedClass, createInstance(managedClass));
        }

        return type.cast(classInstances.get(managedClass));
    }

    private static  <T> T createInstance(Class<T> type) {
        if (type.getConstructors().length > 1) {
            throw new IllegalStateException("There is more than one constructor for this class.");
        }

        try {
            @SuppressWarnings("unchecked")
            Constructor<T> constructor = (Constructor<T>) type.getConstructors()[0];

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] dependencies = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getInstance(parameterTypes[i]);
            }

            return constructor.newInstance(dependencies);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}