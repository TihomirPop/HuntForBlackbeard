package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.jndi.JndiProperties;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;

import static java.util.Objects.requireNonNull;

class FXLocationInitializer {

    private static final int V_BOX_HEIGHT = 90;
    private static final int V_BOX_WIDTH = 30;
    private static final int V_BOX_SPACING = 10;

    private FXLocationInitializer() {
    }

    static void initialize(Button button, AnchorPane gamePane, FXLocations.Builder locationsBuilder) {
        double buttonX = button.getLayoutX();
        double buttonY = button.getLayoutY();
        String buttonId = button.getId();

        VBox vBoxRight = createVboxRight(buttonId, buttonX, buttonY);
        VBox vBoxLeft = createVboxLeft(buttonId, buttonX, buttonY);
        ImageView pirateSightingImageView = createPirateSightingImageView(buttonId);
        vBoxLeft.getChildren().add(pirateSightingImageView);

        gamePane.getChildren().add(vBoxRight);
        gamePane.getChildren().add(vBoxLeft);

        locationsBuilder.addLocation(new FXLocation(
                buttonId.replaceFirst("Button$", ""),
                button,
                vBoxRight,
                vBoxLeft,
                pirateSightingImageView
        ));
    }

    private static VBox createVboxRight(String buttonId, double buttonX, double buttonY) {
        VBox vBoxRight = new VBox();
        vBoxRight.setId("%sVBoxRight".formatted(buttonId));
        vBoxRight.setAlignment(Pos.TOP_CENTER);
        vBoxRight.setLayoutX(buttonX + 31);
        vBoxRight.setLayoutY(buttonY - 87);
        vBoxRight.setPrefHeight(V_BOX_HEIGHT);
        vBoxRight.setPrefWidth(V_BOX_WIDTH);
        vBoxRight.setSpacing(V_BOX_SPACING);
        return vBoxRight;
    }

    private static VBox createVboxLeft(String buttonId, double buttonX, double buttonY) {
        VBox vBoxLeft = new VBox();
        vBoxLeft.setId("%sVBoxLeft".formatted(buttonId));
        vBoxLeft.setAlignment(Pos.TOP_CENTER);
        vBoxLeft.setLayoutX(buttonX - 38);
        vBoxLeft.setLayoutY(buttonY - 87);
        vBoxLeft.setPrefHeight(V_BOX_HEIGHT);
        vBoxLeft.setPrefWidth(V_BOX_WIDTH);
        vBoxLeft.setSpacing(V_BOX_SPACING);
        return vBoxLeft;
    }

    private static ImageView createPirateSightingImageView(String buttonId) {
        URL resource = FXLocationInitializer.class.getResource(JndiProperties.getSightingImagePath());
        requireNonNull(resource, "Resource not found: sighting.png");
        Image image = new Image(resource.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setId("%sPirateSightingImageView".formatted(buttonId));
        imageView.setFitHeight(20);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        imageView.setVisible(false);
        return imageView;
    }

}
