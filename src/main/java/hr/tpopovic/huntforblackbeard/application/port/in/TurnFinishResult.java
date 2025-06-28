package hr.tpopovic.huntforblackbeard.application.port.in;

public abstract sealed class TurnFinishResult {

    private TurnFinishResult() {
    }

    public static final class GameOngoing extends TurnFinishResult {

    }

    public static final class HunterWins extends TurnFinishResult {

    }

    public static final class PirateWins extends TurnFinishResult {

    }

    public static final class Failure extends TurnFinishResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
