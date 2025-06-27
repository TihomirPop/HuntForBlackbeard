package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public record FXLocation(
        String id,
        Button button,
        VBox vBoxRight,
        VBox vBoxLeft,
        ImageView pirateSightingImageView
) {

}
