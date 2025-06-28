package hr.tpopovic.huntforblackbeard.adapter.out;

import hr.tpopovic.huntforblackbeard.application.port.out.DocumentationGenerationCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.DocumentationGenerationResult;
import hr.tpopovic.huntforblackbeard.application.port.out.ForGeneratingDocumentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class HtmlDocumentationGenerator implements ForGeneratingDocumentation {

    @Override
    public DocumentationGenerationResult generate(DocumentationGenerationCommand command) {
        requireNonNull(command, "DocumentationGenerationCommand cannot be null");
        File outputFile = command.outputFile();
        String htmlContent = HtmlDocumentationBuilder.build();

        try {
            Path outputPath = outputFile.toPath();
            Files.write(outputPath, htmlContent.getBytes());
            return new DocumentationGenerationResult.Success();
        } catch (IOException e) {
            return new DocumentationGenerationResult.Failure(e.getMessage());
        }
    }

}
