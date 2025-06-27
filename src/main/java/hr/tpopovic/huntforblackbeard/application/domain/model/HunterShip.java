package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public final class HunterShip extends HunterPiece {

    HunterShip(Name name) {
        super(name);
    }

    @Override
    public Set<Location> getAvailableDestinations() {
        return location.getOverWaterLocations();
    }

}
