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

    void setLocation(Location location) {
        requireNonNull(location, "Location cannot be null");
        this.location = location;
        this.location.addPiece(this);
    }

    public abstract Set<Location> getAvailableDestinations();

    public abstract void move(Location destination);

    protected enum Name {
        HUNTER_SHIP_JANE,
        HUNTER_SHIP_RANGER,
        HUNTER_CAPTAIN_BRAND,
        PIRATE_SHIP_ADVENTURE
    }

}
