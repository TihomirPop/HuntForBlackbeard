package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public class Player {

    private final Type type;
    private final Set<Piece> pieces;
    private final int startingNumberOfMoves;
    private Integer numberOfMoves;

    public Player(Type type, Set<Piece> pieces, int startingNumberOfMoves) {
        this.type = type;
        this.pieces = pieces;
        this.startingNumberOfMoves = startingNumberOfMoves;
        this.numberOfMoves = startingNumberOfMoves;
    }

    public Integer getNumberOfMoves() {
        return numberOfMoves;
    }

    public void useMove() {
        if (numberOfMoves > 0) {
            numberOfMoves--;
        } else {
            throw new IllegalStateException("No moves left");
        }
    }

    public boolean canUseMove() {
        return numberOfMoves > 0;
    }

    public void resetMoves() {
        numberOfMoves = startingNumberOfMoves;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public enum Type {
        HUNTER,
        PIRATE
    }

}
