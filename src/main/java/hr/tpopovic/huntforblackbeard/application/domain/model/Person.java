package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public final class Person extends Piece {

    Person(Name name) {
        super(name);
    }

    @Override
    public Set<Location> getAvailableDestinations() {
        return location.getOverLandLocations();
    }

    @Override
    public void move(Location destination) {
        if (location.getOverLandLocations().contains(destination)) {
            changeLocation(destination);
        } else {
            throw new IllegalArgumentException("Cannot move to the specified destination: " + destination.getName());
        }
    }

}