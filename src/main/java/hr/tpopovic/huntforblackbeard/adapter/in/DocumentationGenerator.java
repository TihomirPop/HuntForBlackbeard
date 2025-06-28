package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class DocumentationGenerator {

    private DocumentationGenerator() {
    }

    public static void generate(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save docs");
        fileChooser.setInitialFileName("HuntForBlackbeard.html");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.html")
        );
        File file = fileChooser.showSaveDialog(window);


    }

}
