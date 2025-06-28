package hr.tpopovic.huntforblackbeard.adapter.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static hr.tpopovic.huntforblackbeard.adapter.out.HtmlDocumentationTemplates.*;

public class HtmlDocumentationBuilder {

    private static final Logger log = LoggerFactory.getLogger(HtmlDocumentationBuilder.class);
    private static final String CLASS_PATH = "./target/classes/";
    private static final String CLASS_EXTENSION = ".class";

    public static String build() {
        StringBuilder classSections = new StringBuilder();
        getAllClassNames().forEach(className -> {
            Class<?> clazz = getClassByName(className);
            StringBuilder classSection = new StringBuilder();
            classSection.append(CLASS_SECTION_TEMPLATE.formatted(clazz.getName()));
            appendExtends(classSection, clazz);
            appendInterfaces(classSection, clazz);
            appendConstructors(classSection, clazz);
            classSections.append(classSection);
        });
        return HTML_TEMPLATE.formatted(classSections.toString());
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
                    .append("&nbsp;")
                    .append(constructor.getName());

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length > 0) {
                constructorItem.append("(<br>");
                for (int i = 0; i < parameterTypes.length; i++) {
                    constructorItem.append("&nbsp;&nbsp;&nbsp;&nbsp;")
                            .append(parameterTypes[i].getName());
                    if (i < parameterTypes.length - 1) {
                        constructorItem.append(",<br>");
                    } else {
                        constructorItem.append("<br>");
                    }
                }
                constructorItem.append(")");
            } else {
                constructorItem.append("()");
            }

            constructorsPart.append(LIST_ITEM_TEMPLATE.formatted(constructorItem));
        }
        classSection.append(CONSTRUCTORS_TEMPLATE.formatted(constructorsPart.toString()));
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

    private static void appendExtends(StringBuilder classSection, Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            String extendsPart = EXTENDS_TEMPLATE.formatted(superclass.getName());
            classSection.append(extendsPart);
        }
    }

    private static List<String> getAllClassNames() {
        try (var stream = Files.walk(Path.of(CLASS_PATH))) {
            return stream.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(string -> string.endsWith(CLASS_EXTENSION))
                    .filter(string -> !string.endsWith("module-info.class"))
                    .map(path -> path.substring(CLASS_PATH.length(), path.length() - CLASS_EXTENSION.length()))
                    .map(path -> path.replace("\\", "."))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        }
    }

}
