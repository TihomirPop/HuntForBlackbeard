package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public abstract sealed class Location {

    private final String name;
    private final Set<Location> overWaterLocations = new HashSet<>();
    private final Set<Location> overLandLocations = new HashSet<>();

    private Location(String name) {
        this.name = name;
    }

    void addOverWaterLocations(Location... locations) {
        Collections.addAll(this.overWaterLocations, locations);
    }

    void addOverLandLocations(Location... locations) {
        Collections.addAll(this.overLandLocations, locations);
    }

    public abstract static sealed class Water extends Location {

        private Water(String name) {
            super(name);
        }

        public static final class Sound extends Water {

            Sound(String name) {
                super(name);
            }

        }

        public static final class Ocean extends Water {

            Ocean(String name) {
                super(name);
            }

        }

        public static final class Blank extends Water {

            Blank(String name) {
                super(name);
            }

        }

    }

    public abstract static sealed class Land extends Location {

        private Land(String name) {
            super(name);
        }

        public static final class Town extends Land {

            Town(String name) {
                super(name);
            }

        }

        public static final class Anchorage extends Land {

            Anchorage(String name) {
                super(name);
            }

        }

        public static final class Blank extends Land {

            Blank(String name) {
                super(name);
            }

        }

    }

}
