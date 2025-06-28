package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public final class FXPiece {

    private final String name;
    private final ImageView imageView;
    private final Type type;
    private boolean startedSearching = false;

    public FXPiece(
            String name,
            ImageView imageView,
            Type type
    ) {
        this.name = name;
        this.imageView = imageView;
        this.type = type;
    }

    public void changeLocation(FXLocation fxLocation) {
        if (imageView.getParent() instanceof Pane parentVBox) {
            parentVBox.getChildren().remove(imageView);
        }
        switch (type) {
            case HUNTER -> fxLocation.vBoxRight().getChildren().add(imageView);
            case PIRATE -> fxLocation.vBoxLeft().getChildren().add(imageView);
        }
    }

    public String name() {
        return name;
    }

    public ImageView imageView() {
        return imageView;
    }

    public Type type() {
        return type;
    }

    public boolean hasStartedSearching() {
        return startedSearching;
    }

    public void setStartedSearching(boolean startedSearching) {
        this.startedSearching = startedSearching;
    }

    enum Type {
        HUNTER,
        PIRATE
    }

}
