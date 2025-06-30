package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class MovementResult {

    private MovementResult() {
    }

    public static final class Success extends MovementResult {

        private final Integer numberOfMoves;

        public Success(Integer numberOfMoves) {
            this.numberOfMoves = numberOfMoves;
        }

        public Integer getNumberOfMoves() {
            return this.numberOfMoves;
        }

    }

    public static final class Failure extends MovementResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
