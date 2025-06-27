package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;


public abstract sealed class Location {

    private final Name name;
    private final Set<Location> overWaterLocations = new HashSet<>();
    private final Set<Location> overLandLocations = new HashSet<>();
    private final Set<Piece> pieces = new HashSet<>();
    private boolean pirateSighted = false;

    private Location(Name name) {
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

    public Name getName() {
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

    public boolean isPirateSighted() {
        return pirateSighted;
    }

    public void setPirateSighted(boolean pirateSighted) {
        this.pirateSighted = pirateSighted;
    }

    public abstract static sealed class Water extends Location {

        private Water(Name name) {
            super(name);
        }

        @Override
        void addOverLandLocations(Location... locations) {
            throw new UnsupportedOperationException("Water locations cannot have over land locations.");
        }

        public static final class Sound extends Water {

            Sound(Name name) {
                super(name);
            }

        }

        public static final class Ocean extends Water {

            Ocean(Name name) {
                super(name);
            }

        }

        public static final class Blank extends Water {

            Blank(Name name) {
                super(name);
            }

        }

    }

    public abstract static sealed class Land extends Location {

        private Land(Name name) {
            super(name);
        }

        public static final class Town extends Land {

            Town(Name name) {
                super(name);
            }

        }

        public static final class Anchorage extends Land {

            Anchorage(Name name) {
                super(name);
            }

        }

        public static final class Blank extends Land {

            Blank(Name name) {
                super(name);
            }

        }

    }

    public enum Name {
        TOPSAIL_INLET("Topsail Inlet", "topsailInlet"),
        FISH_TOWN("Fish Town", "fishTown"),
        NEUS_RIVER("Neus River", "neusRiver"),
        BATH_TOWN("Bath Town", "bathTown"),
        NEW_BERN("New Bern", "newBern"),
        HUNTING_QUARTER_SOUND("Hunting Quarter Sound", "huntingQuarterSound"),
        CORE_BANKS("Core Banks", "coreBanks"),
        WEST_PAMLICO_SOUND("West Pamlico Sound", "westPamlicoSound"),
        OCRACOKE_INLET("Ocracoke Inlet", "ocracokeInlet"),
        OCRACOKE_ISLAND("Ocracoke Island", "ocracokeIsland"),
        EAST_PAMLICO_SOUND("East Pamlico Sound", "eastPamlicoSound"),
        HATTERAS_BANK("Hatteras Bank", "hatterasBank"),
        CAPE_HATTERAS("Cape Hatteras", "capeHatteras"),
        MACHAPUNGA_BLUFF("Machapunga Bluff", "machapungaBluff"),
        ROANOKE_RIVER("Roanoke River", "roanokeRiver"),
        QUEEN_ANNES_CREEK("Queen Anne's Creek", "queenAnnesCreek"),
        ALBEMARLE_COUNTY("Albemarle County", "albemarleCounty"),
        BATS_GRAVE("Bats Grave", "batsGrave"),
        PASQUOTANK_RIVER("Pasquotank River", "pasquotankRiver"),
        NOTS_ISLAND("Nots Island", "notsIsland"),
        ALBEMARLE_SOUND("Albemarle Sound", "albemarleSound"),
        ROANOKE_ISLAND("Roanoke Island", "roanokeIsland"),
        CURRITUCK_SOUND("Currituck Sound", "currituckSound"),
        GUN_INLET("Gun Inlet", "gunInlet"),
        ROANOKE_INLET("Roanoke Inlet", "roanokeInlet"),
        CURRITUCK_INLET("Currituck Inlet", "currituckInlet"),
        JAMES_RIVER("James River", "jamesRiver"),
        CAPE_HENRY("Cape Henry", "capeHenry");

        public final String displayName;
        public final String id;

        Name(String displayName, String id) {
            this.displayName = displayName;
            this.id = id;
        }

        public static Location.Name findById(String id) {
            return Arrays.stream(Name.values())
                    .filter(name -> name.id.equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid location name id"));
        }
    }

}
