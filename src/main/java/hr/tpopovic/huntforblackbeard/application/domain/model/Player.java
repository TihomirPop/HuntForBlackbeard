package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public class Player {

    public static final Set<Piece> PLAYERS_PIECES = Set.of(
            Pieces.HUNTER_SHIP_JANE,
            Pieces.HUNTER_SHIP_RANGER,
            Pieces.HUNTER_CAPTAIN_BRAND
    );
    private static final int STARTING_NUMBER_OF_MOVES = 7;

    private static Integer numberOfMoves = STARTING_NUMBER_OF_MOVES;

    private Player() {
    }

    public static Integer getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void useMove() {
        if (numberOfMoves > 0) {
            numberOfMoves--;
        } else {
            throw new IllegalStateException("No moves left");
        }
    }

    public static boolean canUseMove() {
        return numberOfMoves > 0;
    }

    public static void resetMoves() {
        numberOfMoves = STARTING_NUMBER_OF_MOVES;
    }

}
