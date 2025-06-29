package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.*;
import hr.tpopovic.huntforblackbeard.application.port.in.ForUpdatingGameState;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateResult;
import hr.tpopovic.huntforblackbeard.application.port.out.ForReplaying;
import hr.tpopovic.huntforblackbeard.application.port.out.SaveReplayCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SaveReplayResult;

import java.util.List;

public class GameStateUpdateService implements ForUpdatingGameState {

    private final ForReplaying forReplaying;

    public GameStateUpdateService(ForReplaying forReplaying) {
        this.forReplaying = forReplaying;
    }

    @Override
    public UpdateGameStateResult update(UpdateGameStateCommand command) {
        updateLocation(command.janeLocation(), Pieces.HUNTER_SHIP_JANE);
        updateLocation(command.rangerLocation(), Pieces.HUNTER_SHIP_RANGER);
        updateLocation(command.brandLocation(), Pieces.HUNTER_CAPTAIN_BRAND);
        updateLocation(command.adventureLocation(), Pieces.PIRATE_SHIP_ADVENTURE);
        command.pirateSightings()
                .stream()
                .map(Locations::getLocationByName)
                .forEach(location -> location.setPirateSighted(true));
        GameState.endTurn();

        return updateReplay(command);
    }

    private void updateLocation(Location.Name newLocationName, Piece piece) {
        Location pieceLocation = Locations.getLocationByName(newLocationName);
        if(!pieceLocation.equals(piece.getLocation())) {
            piece.changeLocation(pieceLocation);
            ReplayManager.makeMove(piece, pieceLocation);
        }
    }

    private UpdateGameStateResult updateReplay(UpdateGameStateCommand command) {
        if(command.winner().equals(GameState.Winner.ONGOING)) {
            ReplayManager.endTurn();
            return new UpdateGameStateResult.Success();
        }

        List<ReplayTurn> replayTurns = ReplayManager.endGame();
        SaveReplayCommand replayCommand = new SaveReplayCommand(replayTurns);
        SaveReplayResult result = forReplaying.save(replayCommand);
        return switch (result) {
            case SaveReplayResult.Success _ -> new UpdateGameStateResult.Success();
            case SaveReplayResult.Failure failure -> new UpdateGameStateResult.Failure(failure.getMessage());
        };
    }

}
