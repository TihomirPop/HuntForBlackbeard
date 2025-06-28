package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.GameState;
import hr.tpopovic.huntforblackbeard.application.domain.model.Locations;
import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFinishingTurn;
import hr.tpopovic.huntforblackbeard.application.port.in.TurnFinishResult;
import hr.tpopovic.huntforblackbeard.application.port.out.ForSignalingUpdate;
import hr.tpopovic.huntforblackbeard.application.port.out.SignalUpdateCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SignalUpdateResult;

import java.util.concurrent.CompletableFuture;

public class TurnFinishingService implements ForFinishingTurn {

    private final ForSignalingUpdate forSignalingUpdate;

    public TurnFinishingService(ForSignalingUpdate forSignalingUpdate) {
        this.forSignalingUpdate = forSignalingUpdate;
    }

    @Override
    public CompletableFuture<TurnFinishResult> finishTurn() {
        GameState.endTurn();
        GameState.Winner winner = GameState.getWinner();
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
            case ONGOING -> new TurnFinishResult.GameOngoing();
            case PIRATE -> new TurnFinishResult.PirateWins();
            case HUNTER -> new TurnFinishResult.HunterWins();
        };
    }

    private TurnFinishResult failure(SignalUpdateResult.Failure failure) {
        return new TurnFinishResult.Failure(failure.getMessage());
    }

}
