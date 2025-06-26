package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public abstract sealed class Piece permits Ship, Person {

    private final Name name;
    protected Location location;

    protected Piece(Name name) {
        requireNonNull(name, "Name cannot be null");
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    void setLocation(Location location) {
        requireNonNull(location, "Location cannot be null");
        this.location = location;
        this.location.addPiece(this);
    }

    public Location getLocation() {
        return location;
    }

    public void changeLocation(Location destination) {
        this.location.removePiece(this);
        this.location = destination;
        this.location.addPiece(this);
    }

    public abstract Set<Location> getAvailableDestinations();
    public abstract void move(Location destination);

    public enum Name {
        HUNTER_SHIP_JANE("Jane"),
        HUNTER_SHIP_RANGER("Ranger"),
        HUNTER_CAPTAIN_BRAND("Brand"),
        PIRATE_SHIP_ADVENTURE("Adventure");

        private final String displayName;

        Name(String displayName) {
            this.displayName = displayName;
        }

        public static Name findByName(String name) {
            requireNonNull(name, "Name cannot be null");
            return switch (name.toUpperCase()) {
                case "JANE" -> HUNTER_SHIP_JANE;
                case "RANGER" -> HUNTER_SHIP_RANGER;
                case "BRAND" -> HUNTER_CAPTAIN_BRAND;
                case "ADVENTURE" -> PIRATE_SHIP_ADVENTURE;
                default -> throw new IllegalArgumentException("Unknown piece name: " + name);
            };
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}
