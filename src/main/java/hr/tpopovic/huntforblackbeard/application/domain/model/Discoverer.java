package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.HashSet;
import java.util.Set;

public final class Discoverer extends Piece {

    public Discoverer(Name name) {
        super(name);
    }

    @Override
    public Set<Location> getAvailableDestinations() {
        Set<Location> availableLocations = new HashSet<>();
        availableLocations.addAll(location.getOverWaterLocations());
        availableLocations.addAll(location.getOverLandLocations());
        availableLocations.removeAll(Locations.getPirateSightings());
        return availableLocations;
    }

}
