package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.domain.service.MovementService;
import hr.tpopovic.huntforblackbeard.application.port.in.*;
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

    private FXLocations locations;
    private FXPieces pieces;
    private FXPiece currentlySelectedPiece;

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

        gamePane.getChildren().removeAll(jane, ranger, brand, adventure);
        Node vBoxRight = gamePane.lookup("#jamesRiverButtonVBoxRight");
        if (vBoxRight instanceof VBox vBox) {
            vBox.getChildren().addAll(jane, ranger, brand);
        }
        Node vBoxLeft = gamePane.lookup("#jamesRiverButtonVBoxLeft");
        if (vBoxLeft instanceof VBox vBox) {
            adventure.setVisible(false);
            vBox.getChildren().add(adventure);
        }

        ForMovingPieces forMovingPieces = new MovementService();
        NumberOfMovesQuery numberOfMovesQuery = new NumberOfMovesQuery(Application.PLAYER_TYPE);
        NumberOfMovesResult numberOfMovesResult = forMovingPieces.fetchNumberOfAvailableMoves(numberOfMovesQuery);
        if (numberOfMovesResult instanceof NumberOfMovesResult.Success success) {
            numberOfMovesText.setText("Remaining moves: %s".formatted(success.getNumberOfMoves()));
        }
//        new Thread(() -> {
//            try (ServerSocket serverSocket = new ServerSocket(4242)){
//                while (true) {
//                    Socket clientSocket = serverSocket.accept();
//                    new Thread(() -> processSerializableClient(clientSocket)).start();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    protected void processSerializableClient(Socket clientSocket) {
        try (
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());) {
//            GameState receivedGameState = (GameState) ois.readObject();
//            refreshGameState(receivedGameState, currentGameState);
            oos.writeObject(Boolean.TRUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest() {
        try (Socket clientSocket = new Socket("localhost", 4242)) {
            sendSerializableRequest(clientSocket);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendSerializableRequest(Socket client) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
//        oos.writeObject(gameState);
//        log.info("Game state received confirmation: " + ois.readObject());
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

    private void movementSuccess(MovementResult.Success success, FXLocation fxLocation) {
        updateMapWithAvailablePositionsForCurrentlySelectedPiece();
        currentlySelectedPiece.changeLocation(fxLocation);
        numberOfMovesText.setText("Remaining moves: %s".formatted(success.getNumberOfMoves()));
    }

    private void movementFailure(MovementResult.Failure failure) {

    }

    private void updateMapWithAvailablePositionsForCurrentlySelectedPiece() {
        ForMovingPieces forMovingPieces = new MovementService();
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
