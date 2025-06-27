package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;

public abstract sealed class PirateSightingResult {

    private PirateSightingResult() {
    }

    public static final class Found extends PirateSightingResult {

    }

    public static final class Sighted extends PirateSightingResult {

        private final Location.Name location;

        public Sighted(Location.Name location) {
            this.location = location;
        }

        public Location.Name getLocation() {
            return location;
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
