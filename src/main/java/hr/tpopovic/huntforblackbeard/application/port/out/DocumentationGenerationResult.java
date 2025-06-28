package hr.tpopovic.huntforblackbeard.application.port.out;

public abstract sealed class DocumentationGenerationResult {

    private DocumentationGenerationResult() {
    }

    public static final class Success extends DocumentationGenerationResult {

    }

    public static final class Failure extends DocumentationGenerationResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
