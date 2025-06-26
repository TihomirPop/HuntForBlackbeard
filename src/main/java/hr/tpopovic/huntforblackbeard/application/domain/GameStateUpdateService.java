package hr.tpopovic.huntforblackbeard.application.domain;

import hr.tpopovic.huntforblackbeard.application.port.in.ForUpdatingGameState;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateResult;

public class GameStateUpdateService implements ForUpdatingGameState {

    @Override
    public UpdateGameStateResult update(UpdateGameStateCommand command) {
        return null;
    }

}
