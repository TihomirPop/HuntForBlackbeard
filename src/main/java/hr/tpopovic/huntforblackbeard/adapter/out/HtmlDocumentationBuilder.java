package hr.tpopovic.huntforblackbeard.adapter.out;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static hr.tpopovic.huntforblackbeard.adapter.out.HtmlDocumentationTemplates.*;

public class HtmlDocumentationBuilder {

    private static final String CLASS_PATH = "./target/classes/";
    private static final String CLASS_EXTENSION = ".class";
    public static final String NBSP = "&nbsp;";

    private HtmlDocumentationBuilder() {
    }

    public static String build() throws HtmlDocumentBuilderException {
        StringBuilder classSections = new StringBuilder();
        for (String className : getAllClassNames()) {
            appendClassSection(className, classSections);
        }
        return HTML_TEMPLATE.formatted(classSections.toString());
    }

    private static List<String> getAllClassNames() throws HtmlDocumentBuilderException {
        try (var stream = Files.walk(Path.of(CLASS_PATH))) {
            return stream.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(string -> string.endsWith(CLASS_EXTENSION))
                    .filter(string -> !string.endsWith("module-info.class"))
                    .map(path -> path.substring(CLASS_PATH.length(), path.length() - CLASS_EXTENSION.length()))
                    .map(path -> path.replace("\\", "."))
                    .toList();
        } catch (IOException e) {
            throw new HtmlDocumentBuilderException("Failed to read class files from path: " + CLASS_PATH, e);
        }
    }

    private static void appendClassSection(String className, StringBuilder classSections) throws HtmlDocumentBuilderException {
        Class<?> clazz = getClassByName(className);
        StringBuilder classSection = new StringBuilder();
        classSection.append(CLASS_SECTION_TEMPLATE.formatted(clazz.getName()));
        appendClassType(classSection, clazz);
        appendExtends(classSection, clazz);
        appendInterfaces(classSection, clazz);
        appendConstructors(classSection, clazz);
        appendMethods(classSection, clazz);
        appendFields(classSection, clazz);
        classSections.append(classSection);
    }

    private static Class<?> getClassByName(String className) throws HtmlDocumentBuilderException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new HtmlDocumentBuilderException("Class not found: " + className, e);
        }
    }

    private static void appendClassType(StringBuilder classSection, Class<?> clazz) {
        String classType;
        if (clazz.isAnnotation()) {
            classType = "Annotation";
        } else if (clazz.isEnum()) {
            classType = "Enum";
        } else if (clazz.isInterface()) {
            classType = "Interface";
        } else if (clazz.isRecord()) {
            classType = "Record";
        } else {
            classType = "Class";
        }

        classSection.append(TYPE_TEMPLATE.formatted(classType));
    }

    private static void appendExtends(StringBuilder classSection, Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            String extendsPart = EXTENDS_TEMPLATE.formatted(superclass.getName());
            classSection.append(extendsPart);
        }
    }

    private static void appendInterfaces(StringBuilder classSection, Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length == 0) {
            return;
        }
        StringBuilder implementsPart = new StringBuilder();
        for (Class<?> anInterface : interfaces) {
            String interfaceItem = LIST_ITEM_TEMPLATE.formatted(anInterface.getName());
            implementsPart.append(interfaceItem);
        }
        classSection.append(IMPLEMENTS_TEMPLATE.formatted(implementsPart.toString()));
    }

    private static void appendConstructors(StringBuilder classSection, Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            return;
        }
        StringBuilder constructorsPart = new StringBuilder();
        for (Constructor<?> constructor : constructors) {
            StringBuilder constructorItem = new StringBuilder();
            constructorItem.append(Modifier.toString(constructor.getModifiers()))
                    .append(NBSP)
                    .append(constructor.getName());
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            appendParameters(parameterTypes, constructorItem);

            constructorsPart.append(LIST_ITEM_TEMPLATE.formatted(constructorItem));
        }
        classSection.append(CONSTRUCTORS_TEMPLATE.formatted(constructorsPart.toString()));
    }

    private static void appendParameters(Class<?>[] parameterTypes, StringBuilder item) {
        if (parameterTypes.length > 0) {
            item.append("(<br>");
            for (int i = 0; i < parameterTypes.length; i++) {
                item.append("&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append(parameterTypes[i].getName());
                if (i < parameterTypes.length - 1) {
                    item.append(",<br>");
                } else {
                    item.append("<br>");
                }
            }
            item.append(")");
        } else {
            item.append("()");
        }
    }

    private static void appendMethods(StringBuilder classSection, Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            return;
        }
        StringBuilder methodsPart = new StringBuilder();
        for (Method method : methods) {
            StringBuilder methodItem = new StringBuilder();
            methodItem.append(Modifier.toString(method.getModifiers()))
                    .append(NBSP)
                    .append(method.getReturnType().getName())
                    .append(NBSP)
                    .append(method.getName());

            Class<?>[] parameterTypes = method.getParameterTypes();
            appendParameters(parameterTypes, methodItem);

            methodsPart.append(LIST_ITEM_TEMPLATE.formatted(methodItem));
        }
        classSection.append(METHODS_TEMPLATE.formatted(methodsPart.toString()));
    }

    private static void appendFields(StringBuilder classSection, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return;
        }
        StringBuilder attributesPart = new StringBuilder();
        for (Field field : fields) {
            StringBuilder fieldItem = new StringBuilder();
            fieldItem.append(Modifier.toString(field.getModifiers()))
                    .append(NBSP)
                    .append(field.getType().getName())
                    .append(NBSP)
                    .append(field.getName());

            attributesPart.append(LIST_ITEM_TEMPLATE.formatted(fieldItem));
        }
        classSection.append(FIELDS_TEMPLATE.formatted(attributesPart.toString()));
    }

}
