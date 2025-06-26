package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.port.in.ForUpdatingGameState;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateResult;
import hr.tpopovic.huntforblackbeard.message.Response;
import hr.tpopovic.huntforblackbeard.message.SignalUpdateRequest;
import hr.tpopovic.huntforblackbeard.message.SignalUpdateResponse;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignalUpdateHandler {

    private static final Logger log = LoggerFactory.getLogger(SignalUpdateHandler.class);

    private final ForUpdatingGameState forUpdatingGameState;
    private final FXPieces pieces;
    private final FXLocations locations;
    private final ComboBox<String> selectedPieceComboBox;
    private final Button finishTurnButton;


    public SignalUpdateHandler(ForUpdatingGameState forUpdatingGameState, FXPieces pieces, FXLocations locations, ComboBox<String> selectedPieceComboBox,
            Button finishTurnButton
    ) {
        this.forUpdatingGameState = forUpdatingGameState;
        this.pieces = pieces;
        this.locations = locations;
        this.selectedPieceComboBox = selectedPieceComboBox;
        this.finishTurnButton = finishTurnButton;
    }

    public SignalUpdateResponse update(SignalUpdateRequest request) {
        UpdateGameStateCommand command = new UpdateGameStateCommand(
                Location.Name.findById(request.getJaneLocation()),
                Location.Name.findById(request.getRangerLocation()),
                Location.Name.findById(request.getBrandLocation()),
                Location.Name.findById(request.getAdventureLocation())
        );

        UpdateGameStateResult result = forUpdatingGameState.update(command);

        Platform.runLater(() -> updateFrontend(request));

        return switch (result) {
            case UpdateGameStateResult.Success _ -> success();
            case UpdateGameStateResult.Failure failure -> failure(failure);
        };
    }

    private void updateFrontend(SignalUpdateRequest request) {
        pieces.getJane().changeLocation(locations.findById(request.getJaneLocation()));
        pieces.getRanger().changeLocation(locations.findById(request.getRangerLocation()));
        pieces.getBrand().changeLocation(locations.findById(request.getBrandLocation()));
        pieces.getAdventure().changeLocation(locations.findById(request.getAdventureLocation()));
        locations.forEach(location -> location.button().setDisable(false));
        selectedPieceComboBox.setDisable(false);
        finishTurnButton.setDisable(false);
    }

    private SignalUpdateResponse success() {
        return new SignalUpdateResponse(Response.Result.SUCCESS);
    }

    private SignalUpdateResponse failure(UpdateGameStateResult.Failure failure) {
        log.error("Signal update failed: {}", failure.getMessage());
        return new SignalUpdateResponse(Response.Result.FAILURE);
    }

}
