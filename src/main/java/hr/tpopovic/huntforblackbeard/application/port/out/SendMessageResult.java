package hr.tpopovic.huntforblackbeard.application.port.out;

public abstract sealed class SendMessageResult {

    private SendMessageResult() {
    }

    public static final class Success extends SendMessageResult {

    }

    public static final class Failure extends SendMessageResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
