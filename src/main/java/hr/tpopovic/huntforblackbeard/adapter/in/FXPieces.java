package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.function.Consumer;

public class FXPieces {

    private final FXPiece jane;
    private final FXPiece ranger;
    private final FXPiece brand;
    private final FXPiece adventure;

    private FXPieces(
            FXPiece jane,
            FXPiece ranger,
            FXPiece brand,
            FXPiece adventure
    ) {
        this.jane = jane;
        this.ranger = ranger;
        this.brand = brand;
        this.adventure = adventure;
    }

    public List<String> getPlayerPieceNames() {
        return switch (AppProperties.getPlayerType()) {
            case HUNTER -> List.of(jane.name(), ranger.name(), brand.name());
            case PIRATE -> List.of(adventure.name());
        };
    }

    public FXPiece findByName(String selectedPieceName) {
        return switch (selectedPieceName) {
            case "Jane" -> jane;
            case "Ranger" -> ranger;
            case "Brand" -> brand;
            case "Adventure" -> adventure;
            default -> throw new IllegalArgumentException("No piece found with name: " + selectedPieceName);
        };
    }

    public FXPiece getJane() {
        return jane;
    }

    public FXPiece getRanger() {
        return ranger;
    }

    public FXPiece getBrand() {
        return brand;
    }

    public FXPiece getAdventure() {
        return adventure;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void forEach(Consumer<FXPiece> action) {
        action.accept(jane);
        action.accept(ranger);
        action.accept(brand);
        action.accept(adventure);
    }

    public FXPiece findByName(Piece.Name name) {
        return switch (name) {
            case HUNTER_SHIP_JANE -> jane;
            case HUNTER_SHIP_RANGER -> ranger;
            case HUNTER_CAPTAIN_BRAND -> brand;
            case PIRATE_SHIP_ADVENTURE -> adventure;
            default -> throw new IllegalArgumentException("No piece found with name: " + name);
        };
    }

    public static class Builder {

        private FXPiece jane;
        private FXPiece ranger;
        private FXPiece brand;
        private FXPiece adventure;

        private Builder() {
        }

        public Builder setJane(ImageView jane) {
            this.jane = new FXPiece("Jane", jane, FXPiece.Type.HUNTER);
            return this;
        }

        public Builder setRanger(ImageView ranger) {
            this.ranger = new FXPiece("Ranger", ranger, FXPiece.Type.HUNTER);
            return this;
        }

        public Builder setBrand(ImageView brand) {
            this.brand = new FXPiece("Brand", brand, FXPiece.Type.HUNTER);
            return this;
        }

        public Builder setAdventure(ImageView adventure) {
            this.adventure = new FXPiece("Adventure", adventure, FXPiece.Type.PIRATE);
            return this;
        }

        public FXPieces build() {
            return new FXPieces(jane, ranger, brand, adventure);
        }

    }

}
