package hr.tpopovic.huntforblackbeard.application.port.out;

public abstract sealed class SignalUpdateResult {

    private SignalUpdateResult() {
    }

    public static final class Success extends SignalUpdateResult {

    }

    public static final class Failure extends SignalUpdateResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
