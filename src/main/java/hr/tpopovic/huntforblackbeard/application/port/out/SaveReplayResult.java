package hr.tpopovic.huntforblackbeard.application.port.out;

public abstract sealed class SaveReplayResult {

    private SaveReplayResult() {
    }

    public static final class Success extends SaveReplayResult {

    }

    public static final class Failure extends SaveReplayResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
