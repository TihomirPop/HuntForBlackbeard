package hr.tpopovic.huntforblackbeard.application.port.out;

import java.io.File;

import static java.util.Objects.requireNonNull;

public record DocumentationGenerationCommand(
        File outputFile
) {

    public DocumentationGenerationCommand {
        requireNonNull(outputFile, "Output file cannot be null");
    }

}
