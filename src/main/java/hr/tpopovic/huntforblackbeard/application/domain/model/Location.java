package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;


public abstract sealed class Location {

    private final String name;
    private final Set<Location> overWaterLocations = new HashSet<>();
    private final Set<Location> overLandLocations = new HashSet<>();
    private final Set<Piece> pieces = new HashSet<>();

    private Location(String name) {
        requireNonNull(name, "Name cannot be null");
        this.name = name;
    }

    void addOverWaterLocations(Location... locations) {
        requireNonNull(locations, "Locations cannot be null");
        Collections.addAll(this.overWaterLocations, locations);
    }

    void addOverLandLocations(Location... locations) {
        requireNonNull(locations, "Locations cannot be null");
        Collections.addAll(this.overLandLocations, locations);
    }

    public Set<Location> getOverWaterLocations() {
        return Collections.unmodifiableSet(overWaterLocations);
    }

    public Set<Location> getOverLandLocations() {
        return Collections.unmodifiableSet(overLandLocations);
    }

    public String getName() {
        return name;
    }

    public void addPiece(Piece piece) {
        requireNonNull(piece, "Piece cannot be null");
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        requireNonNull(piece, "Piece cannot be null");
        this.pieces.remove(piece);
    }

    public Set<Piece> getPieces() {
        return Collections.unmodifiableSet(pieces);
    }

    public abstract static sealed class Water extends Location {

        private Water(String name) {
            super(name);
        }

        @Override
        void addOverLandLocations(Location... locations) {
            throw new UnsupportedOperationException("Water locations cannot have over land locations.");
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
