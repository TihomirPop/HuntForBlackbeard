package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class PlayerPiecesResult {

    private PlayerPiecesResult() {
    }

    public static final class Success extends PlayerPiecesResult {

    }

    public static final class Failure extends PlayerPiecesResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
