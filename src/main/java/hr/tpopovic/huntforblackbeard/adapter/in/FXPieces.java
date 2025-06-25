package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
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
        return switch (Application.PLAYER_TYPE) {
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

    public static Builder builder() {
        return new Builder();
    }

    public void forEach(Consumer<FXPiece> consumer) {
        consumer.accept(jane);
        consumer.accept(ranger);
        consumer.accept(brand);
        consumer.accept(adventure);
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
