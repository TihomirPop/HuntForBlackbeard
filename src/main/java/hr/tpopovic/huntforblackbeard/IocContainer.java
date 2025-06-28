package hr.tpopovic.huntforblackbeard;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static java.util.Objects.requireNonNull;

public class IocContainer {

    private static final Map<Class<?>, Object> classInstances = new HashMap<>();
    private static final Set<Class<?>> managedClasses = new HashSet<>();

    private IocContainer() {
    }

    public static void addClassToManage(Class<?> newClass) {
        managedClasses.add(newClass);
        managedClasses.addAll(List.of(newClass.getInterfaces()));
    }

    public static void addInstanceToManage(Class<?> type, Object instance) {
        requireNonNull(instance, "Instance cannot be null");
        requireNonNull(type, "Type cannot be null");
        managedClasses.add(type);
        classInstances.put(type, instance);
    }

    public static <T> T getInstance(Class<T> type) {
        if (!managedClasses.contains(type)) {
            throw new IllegalArgumentException("Class or one of its dependencies isn't managed by the IoC container.");
        }

        if (!classInstances.containsKey(type)) {
            classInstances.put(type, createInstance(type));
        }

        return type.cast(classInstances.get(type));
    }

    private static <T> T createInstance(Class<T> type) {
        if (type.isInterface()) {
            Class<?> managedClass = managedClasses.stream()
                    .filter(clazz -> !clazz.isInterface())
                    .filter(type::isAssignableFrom)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No implementation found for the interface: " + type.getName()));

            return type.cast(getInstance(managedClass));
        }

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
            throw new IllegalStateException("Failed to create an instance of " + type.getName(), e); //TODO: make custom exceptions
        }
    }

}