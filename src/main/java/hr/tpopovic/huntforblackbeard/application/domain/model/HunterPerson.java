package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public final class HunterPerson extends HunterPiece {

    HunterPerson(Name name) {
        super(name);
    }

    @Override
    public Set<Location> getAvailableDestinations() {
        return location.getOverLandLocations();
    }

}