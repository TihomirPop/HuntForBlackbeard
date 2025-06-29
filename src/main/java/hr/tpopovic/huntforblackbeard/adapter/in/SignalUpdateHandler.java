package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.ioc.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.service.GameState;
import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.port.in.ForUpdatingGameState;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateResult;
import hr.tpopovic.huntforblackbeard.socket.Response;
import hr.tpopovic.huntforblackbeard.socket.SignalUpdateRequest;
import hr.tpopovic.huntforblackbeard.socket.SignalUpdateResponse;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class SignalUpdateHandler {

    private static final Logger log = LoggerFactory.getLogger(SignalUpdateHandler.class);

    private final ForUpdatingGameState forUpdatingGameState = IocContainer.getInstance(ForUpdatingGameState.class);
    private final NumberOfMovesHandler numberOfMovesHandler;
    private final MovementFetcher movementFetcher;
    private final FXPieces pieces;
    private final FXLocations locations;
    private final ComboBox<String> selectedPieceComboBox;
    private final Button finishTurnButton;
    private final Button searchForPiratesButton;


    public SignalUpdateHandler(
            NumberOfMovesHandler numberOfMovesHandler, MovementFetcher movementFetcher,
            FXPieces pieces,
            FXLocations locations,
            ComboBox<String> selectedPieceComboBox,
            Button finishTurnButton,
            Button searchForPiratesButton
    ) {
        this.numberOfMovesHandler = numberOfMovesHandler;
        this.movementFetcher = movementFetcher;
        this.pieces = pieces;
        this.locations = locations;
        this.selectedPieceComboBox = selectedPieceComboBox;
        this.finishTurnButton = finishTurnButton;
        this.searchForPiratesButton = searchForPiratesButton;
    }

    public SignalUpdateResponse update(SignalUpdateRequest request) {
        UpdateGameStateCommand command = new UpdateGameStateCommand(
                Location.Name.findById(request.getJaneLocation()),
                Location.Name.findById(request.getRangerLocation()),
                Location.Name.findById(request.getBrandLocation()),
                Location.Name.findById(request.getAdventureLocation()),
                request.getPirateSightings()
                        .stream()
                        .map(Location.Name::findById)
                        .collect(Collectors.toSet()),
                GameState.Winner.findByName(request.getWinner())
        );

        UpdateGameStateResult result = forUpdatingGameState.update(command);

        return switch (result) {
            case UpdateGameStateResult.Success _ -> success(request);
            case UpdateGameStateResult.Failure failure -> failure(failure);
        };
    }

    private SignalUpdateResponse success(SignalUpdateRequest request) {
        Platform.runLater(() -> updateFrontend(request));
        return new SignalUpdateResponse(Response.Result.SUCCESS);
    }

    private SignalUpdateResponse failure(UpdateGameStateResult.Failure failure) {
        log.error("Signal update failed: {}", failure.getMessage());
        return new SignalUpdateResponse(Response.Result.FAILURE);
    }

    private void updateFrontend(SignalUpdateRequest request) {
        pieces.getJane().changeLocation(locations.findById(request.getJaneLocation()));
        pieces.getRanger().changeLocation(locations.findById(request.getRangerLocation()));
        pieces.getBrand().changeLocation(locations.findById(request.getBrandLocation()));
        pieces.getAdventure().changeLocation(locations.findById(request.getAdventureLocation()));
        locations.forEach(location -> {
            location.button().setDisable(false);
            location.pirateSightingImageView().setVisible(false);
        });
        selectedPieceComboBox.setDisable(false);
        finishTurnButton.setDisable(false);
        searchForPiratesButton.setDisable(false);
        numberOfMovesHandler.updateNumberOfMoves();
        if(AppProperties.getPlayerType() == Player.Type.HUNTER) {
            pieces.getAdventure().imageView().setVisible(false);
        }
        pieces.forEach(piece -> piece.setStartedSearching(false));
        FXPiece currentlySelectedPiece = pieces.findByName(selectedPieceComboBox.getValue());
        movementFetcher.updateMapWithAvailablePositionsForCurrentlySelectedPiece(currentlySelectedPiece);
        switch(GameState.Winner.findByName(request.getWinner())) {
            case ONGOING -> {
                // The game is ongoing, no action needed
            }
            case HUNTER -> {
                AlertManager.showInfo("Game Over", "The Hunter has won the game!");
                disableAllButtons();
            }
            case PIRATE -> {
                AlertManager.showInfo("Game Over", "The Pirate has won the game!");
                disableAllButtons();
            }
        }
    }

    private void disableAllButtons() {
        finishTurnButton.setDisable(true);
        searchForPiratesButton.setDisable(true);
        selectedPieceComboBox.setDisable(true);
        locations.forEach(location -> location.button().setDisable(true));
    }

}
