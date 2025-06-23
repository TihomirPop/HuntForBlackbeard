package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.domain.service.MovementService;
import hr.tpopovic.huntforblackbeard.application.domain.service.PlayerPiecesService;
import hr.tpopovic.huntforblackbeard.application.port.in.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void initialize() {
        var gamePaneChildren = List.copyOf(gamePane.getChildren());
        for (var node : gamePaneChildren) {
            if (node instanceof Button button) {
                double buttonX = button.getLayoutX();
                double buttonY = button.getLayoutY();

                VBox vBoxRight = new VBox();
                vBoxRight.setId("%sVBoxRight".formatted(button.getId()));
                vBoxRight.setAlignment(Pos.TOP_CENTER);
                vBoxRight.setLayoutX(buttonX + 31);
                vBoxRight.setLayoutY(buttonY - 87);
                vBoxRight.setPrefHeight(90);
                vBoxRight.setPrefWidth(30);
                vBoxRight.setSpacing(10);
                VBox vBoxLeft = new VBox();
                vBoxLeft.setId("%sVBoxLeft".formatted(button.getId()));
                vBoxLeft.setAlignment(Pos.TOP_CENTER);
                vBoxLeft.setLayoutX(buttonX - 38);
                vBoxLeft.setLayoutY(buttonY - 87);
                vBoxLeft.setPrefHeight(90);
                vBoxLeft.setPrefWidth(30);
                vBoxLeft.setSpacing(10);

                gamePane.getChildren().add(vBoxRight);
                gamePane.getChildren().add(vBoxLeft);
            }
        }

        ForFetchingPlayerPieces forFetchingPlayerPieces = new PlayerPiecesService();
        PlayerPiecesQuery query = switch (Application.PLAYER_TYPE) {
            case "HUNTER" -> new PlayerPiecesQuery(Player.Type.HUNTER);
            case "PIRATE" -> new PlayerPiecesQuery(Player.Type.PIRATE);
            default -> throw new IllegalArgumentException("Unknown player type: " + Application.PLAYER_TYPE);
        };
        PlayerPiecesResult result = forFetchingPlayerPieces.fetch(query);
        if(result instanceof PlayerPiecesResult.Success success) {
            List<String> pieceNames = success.getPieces()
                    .stream()
                    .map(Piece.Name::getDisplayName)
                    .toList();
            selectedPieceComboBox.setItems(FXCollections.observableList(pieceNames));
            selectedPieceComboBox.setValue(pieceNames.getFirst());
            updateMapWithAvailablePositionsForGivenPiece(Pieces.HUNTER_SHIP_JANE.getName());
        }

        gamePane.getChildren().removeAll(jane, ranger, brand, adventure);
        Node vBoxRight = gamePane.lookup("#jamesRiverButtonVBoxRight");
        if( vBoxRight instanceof VBox vBox) {
            vBox.getChildren().addAll(jane, ranger, brand);
        }
        Node vBoxLeft = gamePane.lookup("#jamesRiverButtonVBoxLeft");
        if( vBoxLeft instanceof VBox vBox) {
            adventure.setVisible(false);
            vBox.getChildren().add(adventure);
        }

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(4242)){
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> processSerializableClient(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    protected void processSerializableClient(Socket clientSocket) {
        try (
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());)
        {
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
        System.out.println("Selected piece: " + selectedPieceComboBox.getValue());
    }

    private void updateMapWithAvailablePositionsForGivenPiece(Piece.Name pieceName) {
        ForMovingPieces forMovingPieces = new MovementService();
        MovementLocationQuery query = new MovementLocationQuery(pieceName);
        MovementLocationResult result = forMovingPieces.fetchAvailableMovementLocations(query);
        if (result instanceof MovementLocationResult.Success success) {
            Set<String> availablePositions = success.getLocations()
                    .stream()
                    .map(location -> location.id)
                    .map("%sButton"::formatted)
                    .collect(Collectors.toSet());
            for (var node : gamePane.getChildren()) {
                if (node instanceof Button button) {
                    String buttonId = button.getId();
                    if (availablePositions.contains(buttonId)) {
                        button.setVisible(true);
                    }
                }
            }
        }
    }

}
