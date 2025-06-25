package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FXLocations {
    private final Set<FXLocation> locations;

    private FXLocations(Set<FXLocation> locations) {
        this.locations = locations;
    }

    public FXLocation findById(String id) {
        return locations.stream()
                .filter(location -> location.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No location found with id: " + id));
    }

    public FXLocation findByButton(Object button) {
        return locations.stream()
                .filter(location -> location.button().equals(button))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No location found with button: " + button));
    }

    public Map<Boolean, Set<FXLocation>> partitionByContains(Set<Location.Name> locationNames) {
        Set<String> locationIds = locationNames.stream()
                .map(name -> name.id)
                .collect(Collectors.toSet());
        return this.locations.stream()
                .collect(Collectors.partitioningBy(
                        location -> locationIds.contains(location.id()),
                        Collectors.toSet()
                ));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Set<FXLocation> locations = new HashSet<>();

        private Builder() {
        }

        public Builder addLocation(FXLocation location) {
            locations.add(location);
            return this;
        }

        public FXLocations build() {
            return new FXLocations(locations);
        }
    }

}
