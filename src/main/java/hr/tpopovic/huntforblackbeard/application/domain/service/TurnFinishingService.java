package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.GameState;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFinishingTurn;
import hr.tpopovic.huntforblackbeard.application.port.in.TurnFinishResult;

public class TurnFinishingService implements ForFinishingTurn {

    @Override
    public TurnFinishResult finishTurn() {
        GameState.endTurn();
        return new TurnFinishResult.Success();
    }

}
