package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class TurnFinishResult {

    private TurnFinishResult() {
    }

    public static final class Success extends TurnFinishResult {

    }

    public static final class Failure extends TurnFinishResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getReason() {
            return message;
        }

    }

}
