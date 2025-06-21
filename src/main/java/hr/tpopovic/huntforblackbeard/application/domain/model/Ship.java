package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public final class Ship extends Piece {

    Ship(Name name) {
        super(name);
    }

    @Override
    public Set<Location> getAvailableDestinations() {
        return location.getOverWaterLocations();
    }

    @Override
    public void move(Location destination) {
        if (location.getOverWaterLocations().contains(destination)) {
            this.location.removePiece(this);
            this.location = destination;
            this.location.addPiece(this);
        } else {
            throw new IllegalArgumentException("Cannot move to the specified destination: " + destination.getName());
        }
    }

}