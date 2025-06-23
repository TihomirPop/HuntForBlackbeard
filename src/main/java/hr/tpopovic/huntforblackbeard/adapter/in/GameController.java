package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.application.domain.model.*;
import hr.tpopovic.huntforblackbeard.application.domain.service.MovementService;
import hr.tpopovic.huntforblackbeard.application.domain.service.PlayerPiecesService;
import hr.tpopovic.huntforblackbeard.application.port.in.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GameController {

    @FXML
    AnchorPane gamePane;
    @FXML
    ComboBox<String> selectedPieceComboBox;

    @FXML
    public void initialize() {
        var gamePaneChildren = List.copyOf(gamePane.getChildren());
        for (var node : gamePaneChildren) {
            if (node instanceof Button button) {
                double buttonX = button.getLayoutX();
                double buttonY = button.getLayoutY();

                VBox vBoxRight = new VBox();
                vBoxRight.setAlignment(Pos.TOP_CENTER);
                vBoxRight.setLayoutX(buttonX + 31);
                vBoxRight.setLayoutY(buttonY - 87);
                vBoxRight.setPrefHeight(90);
                vBoxRight.setPrefWidth(30);
                vBoxRight.setBackground(Background.fill(Color.rgb(255, 0, 0)));
                vBoxRight.setSpacing(10);
                VBox vBoxLeft = new VBox();
                vBoxLeft.setAlignment(Pos.TOP_CENTER);
                vBoxLeft.setLayoutX(buttonX - 38);
                vBoxLeft.setLayoutY(buttonY - 87);
                vBoxLeft.setPrefHeight(90);
                vBoxLeft.setPrefWidth(30);
                vBoxLeft.setBackground(Background.fill(Color.rgb(0, 0, 255)));
                vBoxLeft.setSpacing(10);

                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(GameController.class.getResourceAsStream("jane.png"))));
                imageView.setFitHeight(20);
                imageView.setPreserveRatio(true);
                ImageView imageView2 = new ImageView(new Image(Objects.requireNonNull(GameController.class.getResourceAsStream("jane.png"))));
                imageView2.setFitHeight(20);
                imageView2.setPreserveRatio(true);
                vBoxRight.getChildren().add(imageView);
                vBoxRight.getChildren().add(imageView2);


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
