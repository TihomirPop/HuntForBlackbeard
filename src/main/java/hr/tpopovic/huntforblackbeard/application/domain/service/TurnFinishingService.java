package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.GameState;
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
    public TurnFinishResult finishTurn() {
        GameState.endTurn();
        SignalUpdateCommand command = new SignalUpdateCommand(
                Pieces.HUNTER_SHIP_JANE.getLocation().getName(),
                Pieces.HUNTER_SHIP_RANGER.getLocation().getName(),
                Pieces.HUNTER_CAPTAIN_BRAND.getLocation().getName(),
                Pieces.PIRATE_SHIP_ADVENTURE.getLocation().getName()
        );
        CompletableFuture<SignalUpdateResult> result = forSignalingUpdate.signal(command); //todo: handle async properly
//        return switch (result) {
//            case SignalUpdateResult.Success _ -> success();
//            case SignalUpdateResult.Failure failure -> failure(failure);
//        };
        return new TurnFinishResult.Success();
    }

    private TurnFinishResult success() {
        return new TurnFinishResult.Success();
    }

    private TurnFinishResult failure(SignalUpdateResult.Failure failure) {
        return new TurnFinishResult.Failure(failure.getMessage());
    }

}
