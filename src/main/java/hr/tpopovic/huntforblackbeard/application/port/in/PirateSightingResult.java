package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;

import java.util.Set;

public abstract sealed class PirateSightingResult {



    private PirateSightingResult() {
    }

    public static final class Found extends PirateSightingResult {

    }

    public static final class Sighted extends PirateSightingResult {

        private final Location.Name location;
        private final Set<Location.Name> availableDestinations;

        public Sighted(Location.Name location, Set<Location.Name> availableDestinations) {
            this.location = location;
            this.availableDestinations = availableDestinations;
        }

        public Location.Name getLocation() {
            return location;
        }

        public Set<Location.Name> getAvailableDestinations() {
            return availableDestinations;
        }
    }

    public static final class NotSighted extends PirateSightingResult {

    }

    public static final class Failure extends PirateSightingResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
