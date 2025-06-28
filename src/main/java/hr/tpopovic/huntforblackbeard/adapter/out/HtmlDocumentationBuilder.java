package hr.tpopovic.huntforblackbeard.adapter.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
            classSections.append(classSection);
        });
        return HTML_TEMPLATE.formatted(classSections.toString());
    }

    private static void appendExtends(StringBuilder classSection, Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if(superclass != null && !superclass.equals(Object.class)) {
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

        private static Class<?> getClassByName (String className){
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class not found: " + className, e);
            }
        }

    }
