package hr.tpopovic.huntforblackbeard.application.port.out;

import hr.tpopovic.huntforblackbeard.application.domain.model.ReplayTurn;

import java.util.List;

public abstract sealed class GetReplayResult {

    private GetReplayResult() {
    }

    public static final class Success extends GetReplayResult {

        private final List<ReplayTurn> replayTurns;

        public Success(List<ReplayTurn> replayTurns) {
            this.replayTurns = replayTurns;
        }

        public List<ReplayTurn> getReplayTurns() {
            return replayTurns;
        }

    }

    public static final class Failure extends GetReplayResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
