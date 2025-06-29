package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.*;

import java.util.ArrayList;
import java.util.List;

public class ReplayManager {

    private static final List<ReplayTurn> replayTurns = new ArrayList<>();
    private static ReplayTurn currentReplayTurn = new ReplayTurn(
            Player.Type.PIRATE,
            new ArrayList<>()
    );

    private ReplayManager() {
    }

    public static void makeMove(Piece piece, Location location) {
        ReplayMove replayMove = new ReplayMove(
                piece.getName(),
                location.getName()
        );
        currentReplayTurn.moves()
                .add(replayMove);
    }

    public static void endTurn() {
        replayTurns.add(currentReplayTurn);
        Player.Type nextPlayerType = currentReplayTurn.playerType() == Player.Type.PIRATE
                ? Player.Type.HUNTER
                : Player.Type.PIRATE;
        currentReplayTurn = new ReplayTurn(
                nextPlayerType,
                new ArrayList<>()
        );
    }

    public static List<ReplayTurn> endGame() {
        replayTurns.add(currentReplayTurn);
        return replayTurns;
    }

}
