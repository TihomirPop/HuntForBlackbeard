package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.port.in.ForMovingPieces;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class GameInitializer {

    private final AnchorPane gamePane;
    private final ImageView jane;
    private final ImageView ranger;
    private final ImageView brand;
    private final ImageView adventure;
    private final ComboBox<String> selectedPieceComboBox;
    private final Button finishTurnButton;
    private final Button searchForPiratesButton;
    private final Text numberOfMovesText;
    private final ForMovingPieces forMovingPieces = IocContainer.getInstance(ForMovingPieces.class);

    public GameInitializer(
            AnchorPane gamePane,
            ImageView jane,
            ImageView ranger,
            ImageView brand,
            ImageView adventure,
            ComboBox<String> selectedPieceComboBox,
            Button finishTurnButton,
            Button searchForPiratesButton, Text numberOfMovesText
    ) {
        this.gamePane = gamePane;
        this.jane = jane;
        this.ranger = ranger;
        this.brand = brand;
        this.adventure = adventure;
        this.selectedPieceComboBox = selectedPieceComboBox;
        this.finishTurnButton = finishTurnButton;
        this.searchForPiratesButton = searchForPiratesButton;
        this.numberOfMovesText = numberOfMovesText;
    }

    InitializationResult initialize() {
        FXLocations.Builder locationsBuilder = FXLocations.builder();
        List<Node> gamePaneChildren = List.copyOf(gamePane.getChildren());
        for (Node node : gamePaneChildren) {
            if (node instanceof Button button) {
                FXLocationInitializer.initialize(button, gamePane, locationsBuilder);
            }
        }

        FXLocations locations = locationsBuilder.build();
        FXPieces pieces = FXPieces.builder()
                .setJane(jane)
                .setRanger(ranger)
                .setBrand(brand)
                .setAdventure(adventure)
                .build();

        ObservableList<String> playerPieceNames = FXCollections.observableList(pieces.getPlayerPieceNames());
        selectedPieceComboBox.setItems(playerPieceNames);
        String selectedPieceName = playerPieceNames.getFirst();
        selectedPieceComboBox.setValue(selectedPieceName);
        FXPiece currentlySelectedPiece = pieces.findByName(selectedPieceName);
        MovementFetcher movementFetcher = new MovementFetcher(locations);
        movementFetcher.updateMapWithAvailablePositionsForCurrentlySelectedPiece(currentlySelectedPiece);

        FXLocation jamesRiverLocation = locations.findById("jamesRiver");
        pieces.getJane().changeLocation(jamesRiverLocation);
        pieces.getRanger().changeLocation(jamesRiverLocation);
        pieces.getBrand().changeLocation(jamesRiverLocation);
        if (Application.PLAYER_TYPE == Player.Type.HUNTER) {
            pieces.getAdventure().imageView().setVisible(false);
            locations.forEach(location -> location.button().setDisable(true));
            selectedPieceComboBox.setDisable(true);
            finishTurnButton.setDisable(true);
            searchForPiratesButton.setDisable(true);
        } else {
            if (searchForPiratesButton.getParent() instanceof Pane pane) {
                pane.getChildren().remove(searchForPiratesButton);
            }
        }

        NumberOfMovesHandler numberOfMovesHandler = new NumberOfMovesHandler(
                forMovingPieces,
                numberOfMovesText
        );
        numberOfMovesHandler.updateNumberOfMoves();

        SignalUpdateHandler signalUpdateHandler = new SignalUpdateHandler(
                numberOfMovesHandler,
                movementFetcher,
                pieces,
                locations,
                selectedPieceComboBox,
                finishTurnButton,
                searchForPiratesButton
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
