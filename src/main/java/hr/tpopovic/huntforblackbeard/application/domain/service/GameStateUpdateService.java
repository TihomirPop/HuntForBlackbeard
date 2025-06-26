package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.GameState;
import hr.tpopovic.huntforblackbeard.application.domain.model.Locations;
import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.application.port.in.ForUpdatingGameState;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateResult;

public class GameStateUpdateService implements ForUpdatingGameState {

    @Override
    public UpdateGameStateResult update(UpdateGameStateCommand command) {
        Pieces.HUNTER_SHIP_JANE.changeLocation(Locations.getLocationByName(command.janeLocation()));
        Pieces.HUNTER_SHIP_RANGER.changeLocation(Locations.getLocationByName(command.rangerLocation()));
        Pieces.HUNTER_CAPTAIN_BRAND.changeLocation(Locations.getLocationByName(command.brandLocation()));
        Pieces.PIRATE_SHIP_ADVENTURE.changeLocation(Locations.getLocationByName(command.adventureLocation()));
        GameState.endTurn();
        return new UpdateGameStateResult.Success();
    }

}
