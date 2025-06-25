package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public record FXPiece(
        String name,
        ImageView imageView,
        Type type
) {

    public void changeLocation(FXLocation fxLocation) {
        if (imageView.getParent() instanceof VBox parentVBox) {
            parentVBox.getChildren().remove(imageView);
        }
        switch (type) {
            case HUNTER -> fxLocation.vBoxRight().getChildren().add(imageView);
            case PIRATE -> fxLocation.vBoxLeft().getChildren().add(imageView);
        }
    }

    enum Type {
        HUNTER,
        PIRATE
    }

}
