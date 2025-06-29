package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.ioc.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.port.in.ForMovingPieces;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.List;

public class GameInitializer {


    private final ForMovingPieces forMovingPieces = IocContainer.getInstance(ForMovingPieces.class);
    private final GameController gameController;

    public GameInitializer(GameController gameController) {
        this.gameController = gameController;
    }

    InitializationResult initialize() {
        FXLocations.Builder locationsBuilder = FXLocations.builder();
        List<Node> gamePaneChildren = List.copyOf(gameController.gamePane.getChildren());
        for (Node node : gamePaneChildren) {
            if (node instanceof Button button) {
                FXLocationInitializer.initialize(button, gameController.gamePane, locationsBuilder);
            }
        }

        FXLocations locations = locationsBuilder.build();
        FXPieces pieces = FXPieces.builder()
                .setJane(gameController.jane)
                .setRanger(gameController.ranger)
                .setBrand(gameController.brand)
                .setAdventure(gameController.adventure)
                .build();

        ObservableList<String> playerPieceNames = FXCollections.observableList(pieces.getPlayerPieceNames());
        gameController.selectedPieceComboBox.setItems(playerPieceNames);
        String selectedPieceName = playerPieceNames.getFirst();
        gameController.selectedPieceComboBox.setValue(selectedPieceName);
        FXPiece currentlySelectedPiece = pieces.findByName(selectedPieceName);
        MovementFetcher movementFetcher = new MovementFetcher(locations);
        movementFetcher.updateMapWithAvailablePositionsForCurrentlySelectedPiece(currentlySelectedPiece);

        FXLocation jamesRiverLocation = locations.findById("jamesRiver");
        pieces.getJane().changeLocation(jamesRiverLocation);
        pieces.getRanger().changeLocation(jamesRiverLocation);
        pieces.getBrand().changeLocation(jamesRiverLocation);
        if (AppProperties.getPlayerType() == Player.Type.HUNTER) {
            pieces.getAdventure().imageView().setVisible(false);
            locations.forEach(location -> location.button().setDisable(true));
            gameController.selectedPieceComboBox.setDisable(true);
            gameController.finishTurnButton.setDisable(true);
            gameController.searchForPiratesButton.setDisable(true);
        } else {
            if (gameController.searchForPiratesButton.getParent() instanceof Pane pane) {
                pane.getChildren().remove(gameController.searchForPiratesButton);
            }
        }

        NumberOfMovesHandler numberOfMovesHandler = new NumberOfMovesHandler(
                forMovingPieces,
                gameController.numberOfMovesText
        );
        numberOfMovesHandler.updateNumberOfMoves();

        SignalUpdateHandler signalUpdateHandler = new SignalUpdateHandler(
                numberOfMovesHandler,
                movementFetcher,
                pieces,
                locations,
                gameController.selectedPieceComboBox,
                gameController.finishTurnButton,
                gameController.searchForPiratesButton
        );
        SignalUpdateServerSocket signalUpdateServerSocket = new SignalUpdateServerSocket(signalUpdateHandler);
        signalUpdateServerSocket.start();

        return new InitializationResult(
                locations,
                pieces,
                currentlySelectedPiece,
                numberOfMovesHandler,
                movementFetcher
        );
    }
}
