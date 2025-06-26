package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class UpdateGameStateResult {

    private UpdateGameStateResult() {
    }

    public static final class Success extends UpdateGameStateResult {

    }

    public static final class Failure extends UpdateGameStateResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
