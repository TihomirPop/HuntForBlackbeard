package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public abstract sealed class Piece permits Ship, Person {

    private final Name name;
    protected Location location;

    public abstract Set<Location> getAvailableDestinations();

    public abstract void move(Location destination);

    protected Piece(Name name) {
        this.name = name;
    }

    protected Piece(Name name, Location location) {
        this.name = name;
        this.location = location;
    }

    protected enum Name {
        HUNTER_SHIP_JANE,
        HUNTER_SHIP_RANGER,
        HUNTER_CAPTAIN_BRAND,
        PIRATE_SHIP_ADVENTURE
    }
}
