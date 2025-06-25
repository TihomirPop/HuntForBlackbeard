package hr.tpopovic.huntforblackbeard.adapter.in;

import java.util.HashSet;
import java.util.Set;

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
