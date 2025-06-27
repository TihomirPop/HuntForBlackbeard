package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class PirateSightingResult {

    private PirateSightingResult() {
    }

    public static final class Found extends PirateSightingResult {

    }

    public static final class Sighted extends PirateSightingResult {

    }

    public static final class NotSighted extends PirateSightingResult {

    }

    public static final class Failure extends PirateSightingResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
