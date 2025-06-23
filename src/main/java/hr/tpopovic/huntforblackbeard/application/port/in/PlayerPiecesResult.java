package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;

import java.util.Set;

public abstract sealed class PlayerPiecesResult {

    private PlayerPiecesResult() {
    }

    public static final class Success extends PlayerPiecesResult {

        private final Set<Piece.Name> pieces;

        public Success(Set<Piece.Name> pieces) {
            this.pieces = pieces;
        }

        public Set<Piece.Name> getPieces() {
            return pieces;
        }

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
