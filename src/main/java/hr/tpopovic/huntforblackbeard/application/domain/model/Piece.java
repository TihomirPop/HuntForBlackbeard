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

        public String getDisplayName() {
            return displayName;
        }
    }

}
