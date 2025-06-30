package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;

import java.util.Set;

public abstract sealed class MovementLocationResult {

    private MovementLocationResult() {
    }

    public static final class Success extends MovementLocationResult {

        private final Set<Location.Name> locations;

        public Success(Set<Location.Name> locations) {
            this.locations = locations;
        }

        public Set<Location.Name> getLocations() {
            return locations;
        }

    }

    public static final class Failure extends MovementLocationResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
