package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.adapter.out.SignalUpdateClientSocket;
import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.domain.service.GameStateUpdateService;
import hr.tpopovic.huntforblackbeard.application.domain.service.MovementService;
import hr.tpopovic.huntforblackbeard.application.domain.service.TurnFinishingService;
import hr.tpopovic.huntforblackbeard.application.port.in.*;
import hr.tpopovic.huntforblackbeard.application.port.out.ForSignalingUpdate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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

    private FXLocations locations;
    private FXPieces pieces;
    private FXPiece currentlySelectedPiece;
    private NumberOfMovesHandler numberOfMovesHandler;
    private final ForMovingPieces forMovingPieces = IocContainer.getInstance(ForMovingPieces.class);
    private final ForUpdatingGameState forUpdatingGameState = IocContainer.getInstance(ForUpdatingGameState.class);
    private final ForFinishingTurn forFinishingTurn = IocContainer.getInstance(ForFinishingTurn.class);

    @FXML
    public void initialize() {
        FXLocations.Builder locationsBuilder = FXLocations.builder();
        List<Node> gamePaneChildren = List.copyOf(gamePane.getChildren());
        for (Node node : gamePaneChildren) {
            if (node instanceof Button button) {
                double buttonX = button.getLayoutX();
                double buttonY = button.getLayoutY();
                String buttonId = button.getId();

                VBox vBoxRight = new VBox();
                vBoxRight.setId("%sVBoxRight".formatted(buttonId));
                vBoxRight.setAlignment(Pos.TOP_CENTER);
                vBoxRight.setLayoutX(buttonX + 31);
                vBoxRight.setLayoutY(buttonY - 87);
                vBoxRight.setPrefHeight(90);
                vBoxRight.setPrefWidth(30);
                vBoxRight.setSpacing(10);
                VBox vBoxLeft = new VBox();
                vBoxLeft.setId("%sVBoxLeft".formatted(buttonId));
                vBoxLeft.setAlignment(Pos.TOP_CENTER);
                vBoxLeft.setLayoutX(buttonX - 38);
                vBoxLeft.setLayoutY(buttonY - 87);
                vBoxLeft.setPrefHeight(90);
                vBoxLeft.setPrefWidth(30);
                vBoxLeft.setSpacing(10);

                gamePane.getChildren().add(vBoxRight);
                gamePane.getChildren().add(vBoxLeft);

                locationsBuilder.addLocation(new FXLocation(
                        buttonId.replaceFirst("Button$", ""),
                        button,
                        vBoxRight,
                        vBoxLeft
                ));
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
                finishTurnButton
        );
        SignalUpdateServerSocket signalUpdateServerSocket = new SignalUpdateServerSocket(signalUpdateHandler);
        signalUpdateServerSocket.start();
    }

    @FXML
    void onPieceSelected(ActionEvent event) {
        currentlySelectedPiece = pieces.findByName(selectedPieceComboBox.getValue());
    }

    @FXML
    void onMovementButtonPressed(ActionEvent event) {
        Piece.Name currentlySelectedPieceName = Piece.Name.findByName(selectedPieceComboBox.getValue());
        FXLocation fxLocation = locations.findByButton(event.getSource());
        Location.Name location = Location.Name.findById(fxLocation.id());
        ForMovingPieces forMovingPieces = new MovementService();
        MovementCommand movementCommand = new MovementCommand(currentlySelectedPieceName, location);
        MovementResult result = forMovingPieces.move(movementCommand);
        switch (result) {
            case MovementResult.Success success -> movementSuccess(success, fxLocation);
            case MovementResult.Failure failure -> movementFailure(failure);
        }
    }

    @FXML
    void onFinishTurnButtonPressed(ActionEvent event) {
        forFinishingTurn.finishTurn();
        locations.forEach(location -> location.button().setDisable(true));
        selectedPieceComboBox.setDisable(true);
        finishTurnButton.setDisable(true);
    }

    private void movementSuccess(MovementResult.Success success, FXLocation fxLocation) {
        updateMapWithAvailablePositionsForCurrentlySelectedPiece();
        currentlySelectedPiece.changeLocation(fxLocation);
        numberOfMovesHandler.setNumberOfMoves(success.getNumberOfMoves());
    }

    private void movementFailure(MovementResult.Failure failure) {

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
