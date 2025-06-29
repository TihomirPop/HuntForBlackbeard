package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.Locations;
import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.application.domain.model.ReplayTurn;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFinishingTurn;
import hr.tpopovic.huntforblackbeard.application.port.in.TurnFinishResult;
import hr.tpopovic.huntforblackbeard.application.port.out.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TurnFinishingService implements ForFinishingTurn {

    private final ForSignalingUpdate forSignalingUpdate;
    private final ForReplaying forReplaying;

    public TurnFinishingService(ForSignalingUpdate forSignalingUpdate, ForReplaying forReplaying) {
        this.forSignalingUpdate = forSignalingUpdate;
        this.forReplaying = forReplaying;
    }

    @Override
    public CompletableFuture<TurnFinishResult> finishTurn() {
        GameState.Winner winner = GameState.getWinner();
        GameState.endTurn();
        SignalUpdateCommand command = new SignalUpdateCommand(
                Pieces.HUNTER_SHIP_JANE.getLocation().getName(),
                Pieces.HUNTER_SHIP_RANGER.getLocation().getName(),
                Pieces.HUNTER_CAPTAIN_BRAND.getLocation().getName(),
                Pieces.PIRATE_SHIP_ADVENTURE.getLocation().getName(),
                Locations.getPirateSightingNames(),
                winner
        );
        return forSignalingUpdate.signal(command)
                .thenApply(result ->
                        switch (result) {
                            case SignalUpdateResult.Success _ -> success(winner);
                            case SignalUpdateResult.Failure failure -> failure(failure);
                        }
                );
    }

    private TurnFinishResult success(GameState.Winner winner) {
        return switch (winner) {
            case ONGOING -> gameOngoing();
            case PIRATE -> winnerPirate();
            case HUNTER -> winnerHunter();
        };
    }

    private static TurnFinishResult.GameOngoing gameOngoing() {
        ReplayManager.endTurn();
        return new TurnFinishResult.GameOngoing();
    }

    private TurnFinishResult winnerPirate() {
        return saveReplay(new TurnFinishResult.PirateWins());
    }

    private TurnFinishResult saveReplay(TurnFinishResult winnerResult) {
        List<ReplayTurn> replayTurns = ReplayManager.endGame();
        SaveReplayCommand command = new SaveReplayCommand(replayTurns);
        SaveReplayResult result = forReplaying.save(command);
        return switch (result) {
            case SaveReplayResult.Success _ -> winnerResult;
            case SaveReplayResult.Failure failure -> new TurnFinishResult.Failure(failure.getMessage());
        };
    }

    private TurnFinishResult winnerHunter() {
        return saveReplay(new TurnFinishResult.HunterWins());
    }

    private TurnFinishResult failure(SignalUpdateResult.Failure failure) {
        return new TurnFinishResult.Failure(failure.getMessage());
    }

}
