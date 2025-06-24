package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.NumberOfMoves;

public abstract sealed class NumberOfMovesResult {

    private NumberOfMovesResult() {
    }

    public static final class Success extends NumberOfMovesResult {

        private final Integer numberOfMoves;

        public Success(Integer numberOfMoves) {
            this.numberOfMoves = numberOfMoves;
        }

        public Integer getNumberOfMoves() {
            return this.numberOfMoves;
        }

    }

    public static final class Failure extends NumberOfMovesResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
