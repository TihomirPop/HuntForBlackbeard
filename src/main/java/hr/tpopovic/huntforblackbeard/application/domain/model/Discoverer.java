package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class Discoverer extends Piece {

    private final Set<Location> pirateSightings = new HashSet<>();

    public Discoverer(Name name) {
        super(name);
    }

    @Override
    public Set<Location> getAvailableDestinations() {
        Set<Location> availableLocations = new HashSet<>();
        availableLocations.addAll(location.getOverWaterLocations());
        availableLocations.addAll(location.getOverLandLocations());
        availableLocations.removeAll(pirateSightings);
        return availableLocations;
    }

    public void addPirateSighting(Location location) {
        requireNonNull(location, "Location cannot be null");
        pirateSightings.add(location);
    }

    public void clearPirateSightings() {
        pirateSightings.clear();
    }

    public Set<Location> getPirateSightings() {
        return new HashSet<>(pirateSightings);
    }

}
