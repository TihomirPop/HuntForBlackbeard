package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.port.out.DocumentationGenerationCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.DocumentationGenerationResult;
import hr.tpopovic.huntforblackbeard.application.port.out.ForGeneratingDocumentation;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class DocumentationGenerator {

    private final ForGeneratingDocumentation forGeneratingDocumentation = IocContainer.getInstance(ForGeneratingDocumentation.class);

    public void generate(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save docs");
        fileChooser.setInitialFileName("HuntForBlackbeard.html");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.html")
        );
        File file = fileChooser.showSaveDialog(window);
        DocumentationGenerationCommand command = new DocumentationGenerationCommand(file);
        DocumentationGenerationResult result = forGeneratingDocumentation.generate(command);
        switch (result) {
            case DocumentationGenerationResult.Failure failure -> {
            }
            case DocumentationGenerationResult.Success success -> {
            }
        }
    }

}
