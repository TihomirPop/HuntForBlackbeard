package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public final class PirateShip extends Piece {

    PirateShip(Name name) {
        super(name);
    }

    @Override
    public Set<Location> getAvailableDestinations() {
        if(GameState.isFirstTurn() && GameState.isFirstMove()) {
            return Locations.getPirateStartingLocations();
        }
        return location.getOverWaterLocations();
    }

    @Override
    public void changeLocation(Location destination) {
        GameState.addLocationDiscoveredByPirate(destination);
        super.changeLocation(destination);
    }

    @Override
    public void move(Location destination) {
        if (getAvailableDestinations().contains(destination)) {
            location.setPirateSighted(true);
            changeLocation(destination);
        } else {
            throw new IllegalArgumentException("Cannot move to the specified destination: " + destination.getName());
        }
    }

}
