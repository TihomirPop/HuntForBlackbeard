package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class MovementResult {

    private MovementResult() {
    }

    public static final class Success extends MovementResult {

    }

    public static final class Failure extends MovementResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getReason() {
            return message;
        }

    }

}
