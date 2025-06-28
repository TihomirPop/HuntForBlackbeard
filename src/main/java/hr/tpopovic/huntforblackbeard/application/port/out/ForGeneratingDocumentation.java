package hr.tpopovic.huntforblackbeard.application.port.out;

public interface ForGeneratingDocumentation {

    DocumentationGenerationResult generate(DocumentationGenerationCommand command);

}
