package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.port.in.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameController {

    @FXML
    AnchorPane gamePane;
    @FXML
    ComboBox<String> selectedPieceComboBox;
    @FXML
    ImageView jane;
    @FXML
    ImageView ranger;
    @FXML
    ImageView brand;
    @FXML
    ImageView adventure;
    @FXML
    Text numberOfMovesText;
    @FXML
    Button finishTurnButton;
    @FXML
    Button searchForPiratesButton;

    private FXLocations locations;
    private FXPieces pieces;
    private FXPiece currentlySelectedPiece;
    private NumberOfMovesHandler numberOfMovesHandler;
    private final ForMovingPieces forMovingPieces = IocContainer.getInstance(ForMovingPieces.class);
    private final ForUpdatingGameState forUpdatingGameState = IocContainer.getInstance(ForUpdatingGameState.class);
    private final ForFinishingTurn forFinishingTurn = IocContainer.getInstance(ForFinishingTurn.class);
    private final ForDiscoveringPirateSightings forDiscoveringPirateSightings = IocContainer.getInstance(ForDiscoveringPirateSightings.class);

    @FXML
    public void initialize() {
        FXLocations.Builder locationsBuilder = FXLocations.builder();
        List<Node> gamePaneChildren = List.copyOf(gamePane.getChildren());
        for (Node node : gamePaneChildren) {
            if (node instanceof Button button) {
                FXLocationInitializer.initialize(button, gamePane, locationsBuilder);
            }
        }

        locations = locationsBuilder.build();
        pieces = FXPieces.builder()
                .setJane(jane)
                .setRanger(ranger)
                .setBrand(brand)
                .setAdventure(adventure)
                .build();

        ObservableList<String> playerPieceNames = FXCollections.observableList(pieces.getPlayerPieceNames());
        selectedPieceComboBox.setItems(playerPieceNames);
        String selectedPieceName = playerPieceNames.getFirst();
        selectedPieceComboBox.setValue(selectedPieceName);
        currentlySelectedPiece = pieces.findByName(selectedPieceName);
        updateMapWithAvailablePositionsForCurrentlySelectedPiece();

        FXLocation jamesRiverLocation = locations.findById("jamesRiver");
        pieces.getJane().changeLocation(jamesRiverLocation);
        pieces.getRanger().changeLocation(jamesRiverLocation);
        pieces.getBrand().changeLocation(jamesRiverLocation);
        if (Application.PLAYER_TYPE == Player.Type.HUNTER) {
            pieces.getAdventure().imageView().setVisible(false);
            disableButtons();
        } else {
            if(searchForPiratesButton.getParent() instanceof Pane pane) {
                pane.getChildren().remove(searchForPiratesButton);
            }
        }

        numberOfMovesHandler = new NumberOfMovesHandler(
                forMovingPieces,
                numberOfMovesText
        );
        numberOfMovesHandler.updateNumberOfMoves();

        SignalUpdateHandler signalUpdateHandler = new SignalUpdateHandler(
                forUpdatingGameState,
                numberOfMovesHandler,
                pieces,
                locations,
                selectedPieceComboBox,
                finishTurnButton,
                searchForPiratesButton
        );
        SignalUpdateServerSocket signalUpdateServerSocket = new SignalUpdateServerSocket(signalUpdateHandler);
        signalUpdateServerSocket.start();
    }

    @FXML
    void onPieceSelected() {
        currentlySelectedPiece = pieces.findByName(selectedPieceComboBox.getValue());
        updateMapWithAvailablePositionsForCurrentlySelectedPiece();
    }

    @FXML
    void onMovementButtonPressed(ActionEvent event) {
        Piece.Name currentlySelectedPieceName = Piece.Name.findByName(selectedPieceComboBox.getValue());
        FXLocation fxLocation = locations.findByButton(event.getSource());
        Location.Name location = Location.Name.findById(fxLocation.id());
        MovementCommand movementCommand = new MovementCommand(currentlySelectedPieceName, location);
        MovementResult result = forMovingPieces.move(movementCommand);
        switch (result) {
            case MovementResult.Success success -> movementSuccess(success, fxLocation);
            case MovementResult.Failure failure -> movementFailure(failure);
        }
    }

    @FXML
    void onFinishTurnButtonPressed() {
        forFinishingTurn.finishTurn()
                .thenAccept(result -> {
                    if (result instanceof TurnFinishResult.Failure failure) {
                        AlertManager.showInfo("Turn Finish Error", failure.getMessage());
                    }
                });
        disableButtons();
    }


    @FXML
    void onSearchForPiratesButtonPressed() {
        Piece.Name pieceName = Piece.Name.findByName(currentlySelectedPiece.name());
        PirateSightingStartCommand command = new PirateSightingStartCommand(pieceName);
        PirateSightingResult result = forDiscoveringPirateSightings.startDiscovery(command);
        switch (result) {
            case PirateSightingResult.Found _ -> pieces.getAdventure().imageView().setVisible(true);
            case PirateSightingResult.Sighted sighted -> locations.findById(sighted.getLocation().id).pirateSightingImageView().setVisible(true);
            case PirateSightingResult.NotSighted _ -> AlertManager.showInfo("Pirate Sighting", "No pirates sighted at this location.");
            case PirateSightingResult.Failure failure -> AlertManager.showInfo("Pirate Sighting Failure", failure.getMessage());
        }
    }

    private void disableButtons() {
        locations.forEach(location -> location.button().setDisable(true));
        selectedPieceComboBox.setDisable(true);
        finishTurnButton.setDisable(true);
        searchForPiratesButton.setDisable(true);
    }

    private void movementSuccess(MovementResult.Success success, FXLocation fxLocation) {
        updateMapWithAvailablePositionsForCurrentlySelectedPiece();
        currentlySelectedPiece.changeLocation(fxLocation);
        numberOfMovesHandler.setNumberOfMoves(success.getNumberOfMoves());
    }

    private void movementFailure(MovementResult.Failure failure) {
        AlertManager.showInfo("Movement Error", failure.getMessage());
    }

    private void updateMapWithAvailablePositionsForCurrentlySelectedPiece() {
        MovementLocationQuery query = new MovementLocationQuery(currentlySelectedPiece.name());
        MovementLocationResult result = forMovingPieces.fetchAvailableMovementLocations(query);
        if (result instanceof MovementLocationResult.Success success) {
            Map<Boolean, Set<FXLocation>> movementAvailableToLocations = locations.partitionByContains(success.getLocations());
            movementAvailableToLocations.getOrDefault(true, Set.of())
                    .forEach(location -> location.button().setVisible(true));
            movementAvailableToLocations.getOrDefault(false, Set.of())
                    .forEach(location -> location.button().setVisible(false));
        }
    }

}
