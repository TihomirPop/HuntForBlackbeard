package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

public class GameController {

    @FXML
    AnchorPane gamePane;

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
    }

}
