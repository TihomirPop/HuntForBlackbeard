package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public class GameState {

    private static Player currentPlayer = Players.PIRATE;
    private static Integer turnCount = 0;

    private GameState() {
    }

    public static void endTurn() {
        turnCount++;
        currentPlayer.resetMoves();
        currentPlayer = currentPlayer == Players.HUNTER
                ? Players.PIRATE
                : Players.HUNTER;
    }

    public static boolean canCurrentPlayerMove() {
        return currentPlayer.canUseMove();
    }

    public static boolean isCurrentPlayersPiece(Piece piece) {
        return currentPlayer.getPieces().contains(piece);
    }

    public static void currentPlayerMoves() {
        if (canCurrentPlayerMove()) {
            currentPlayer.useMove();
        } else {
            throw new IllegalStateException("Current player cannot move, no moves left.");
        }
    }

    public static Set<Piece> getCurrentPlayerPieces() {
        return currentPlayer.getPieces();
    }

    public static Integer getCurrentPlayerMoves() {
        return currentPlayer.getNumberOfMoves();
    }

    public static boolean isFirstTurn() {
        return turnCount == 0;
    }

    public static boolean isFirstMove() {
        return currentPlayer.isFirstMove();
    }

}
