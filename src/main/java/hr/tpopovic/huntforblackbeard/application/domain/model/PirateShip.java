package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public final class PirateShip extends Ship {

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

}
