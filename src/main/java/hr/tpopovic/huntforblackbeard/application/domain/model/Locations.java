package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;
import java.util.stream.Collectors;

public class Locations {

    public static final Location TOPSAIL_INLET = new Location.Water.Ocean(Location.Name.TOPSAIL_INLET);
    public static final Location FISH_TOWN = new Location.Land.Town(Location.Name.FISH_TOWN);
    public static final Location NEUS_RIVER = new Location.Land.Anchorage(Location.Name.NEUS_RIVER);
    public static final Location BATH_TOWN = new Location.Land.Town(Location.Name.BATH_TOWN);
    public static final Location NEW_BERN = new Location.Land.Blank(Location.Name.NEW_BERN);
    public static final Location HUNTING_QUARTER_SOUND = new Location.Water.Sound(Location.Name.HUNTING_QUARTER_SOUND);
    public static final Location CORE_BANKS = new Location.Land.Anchorage(Location.Name.CORE_BANKS);
    public static final Location WEST_PAMLICO_SOUND = new Location.Water.Sound(Location.Name.WEST_PAMLICO_SOUND);
    public static final Location OCRACOKE_INLET = new Location.Water.Ocean(Location.Name.OCRACOKE_INLET);
    public static final Location OCRACOKE_ISLAND = new Location.Land.Anchorage(Location.Name.OCRACOKE_ISLAND);
    public static final Location EAST_PAMLICO_SOUND = new Location.Water.Sound(Location.Name.EAST_PAMLICO_SOUND);
    public static final Location HATTERAS_BANK = new Location.Land.Anchorage(Location.Name.HATTERAS_BANK);
    public static final Location CAPE_HATTERAS = new Location.Water.Ocean(Location.Name.CAPE_HATTERAS);
    public static final Location MACHAPUNGA_BLUFF = new Location.Land.Anchorage(Location.Name.MACHAPUNGA_BLUFF);
    public static final Location ROANOKE_RIVER = new Location.Land.Anchorage(Location.Name.ROANOKE_RIVER);
    public static final Location QUEEN_ANNES_CREEK = new Location.Land.Town(Location.Name.QUEEN_ANNES_CREEK);
    public static final Location ALBEMARLE_COUNTY = new Location.Land.Blank(Location.Name.ALBEMARLE_COUNTY);
    public static final Location BATS_GRAVE = new Location.Land.Anchorage(Location.Name.BATS_GRAVE);
    public static final Location PASQUOTANK_RIVER = new Location.Land.Anchorage(Location.Name.PASQUOTANK_RIVER);
    public static final Location NOTS_ISLAND = new Location.Land.Anchorage(Location.Name.NOTS_ISLAND);
    public static final Location ALBEMARLE_SOUND = new Location.Water.Sound(Location.Name.ALBEMARLE_SOUND);
    public static final Location ROANOKE_ISLAND = new Location.Land.Anchorage(Location.Name.ROANOKE_ISLAND);
    public static final Location CURRITUCK_SOUND = new Location.Water.Sound(Location.Name.CURRITUCK_SOUND);
    public static final Location GUN_INLET = new Location.Water.Ocean(Location.Name.GUN_INLET);
    public static final Location ROANOKE_INLET = new Location.Water.Ocean(Location.Name.ROANOKE_INLET);
    public static final Location CURRITUCK_INLET = new Location.Water.Ocean(Location.Name.CURRITUCK_INLET);
    public static final Location JAMES_RIVER = new Location.Land.Blank(Location.Name.JAMES_RIVER);
    public static final Location CAPE_HENRY = new Location.Water.Blank(Location.Name.CAPE_HENRY);

    private static final Set<Location> locationSet = Set.of(
            TOPSAIL_INLET, FISH_TOWN, NEUS_RIVER, BATH_TOWN, NEW_BERN,
            HUNTING_QUARTER_SOUND, CORE_BANKS, WEST_PAMLICO_SOUND, OCRACOKE_INLET,
            OCRACOKE_ISLAND, EAST_PAMLICO_SOUND, HATTERAS_BANK, CAPE_HATTERAS,
            MACHAPUNGA_BLUFF, ROANOKE_RIVER, QUEEN_ANNES_CREEK, ALBEMARLE_COUNTY,
            BATS_GRAVE, PASQUOTANK_RIVER, NOTS_ISLAND, ALBEMARLE_SOUND,
            ROANOKE_ISLAND, CURRITUCK_SOUND, GUN_INLET, ROANOKE_INLET,
            CURRITUCK_INLET, JAMES_RIVER, CAPE_HENRY
    );

    static {
        TOPSAIL_INLET.addOverWaterLocations(FISH_TOWN, HUNTING_QUARTER_SOUND, OCRACOKE_INLET);
        FISH_TOWN.addOverWaterLocations(TOPSAIL_INLET, HUNTING_QUARTER_SOUND);
        FISH_TOWN.addOverLandLocations(NEW_BERN);
        NEUS_RIVER.addOverWaterLocations(HUNTING_QUARTER_SOUND, WEST_PAMLICO_SOUND);
        NEUS_RIVER.addOverLandLocations(NEW_BERN, BATH_TOWN);
        BATH_TOWN.addOverWaterLocations(WEST_PAMLICO_SOUND);
        BATH_TOWN.addOverLandLocations(NEW_BERN, NEUS_RIVER, ROANOKE_RIVER);
        NEW_BERN.addOverLandLocations(FISH_TOWN, NEUS_RIVER, BATH_TOWN);
        HUNTING_QUARTER_SOUND.addOverWaterLocations(FISH_TOWN, TOPSAIL_INLET, CORE_BANKS, OCRACOKE_INLET, NEUS_RIVER);
        CORE_BANKS.addOverWaterLocations(HUNTING_QUARTER_SOUND, WEST_PAMLICO_SOUND);
        WEST_PAMLICO_SOUND.addOverWaterLocations(NEUS_RIVER, CORE_BANKS, OCRACOKE_ISLAND, EAST_PAMLICO_SOUND, MACHAPUNGA_BLUFF, BATH_TOWN);
        OCRACOKE_INLET.addOverWaterLocations(TOPSAIL_INLET, HUNTING_QUARTER_SOUND, OCRACOKE_ISLAND, CAPE_HATTERAS);
        OCRACOKE_ISLAND.addOverWaterLocations(OCRACOKE_INLET, WEST_PAMLICO_SOUND, EAST_PAMLICO_SOUND);
        EAST_PAMLICO_SOUND.addOverWaterLocations(WEST_PAMLICO_SOUND, OCRACOKE_ISLAND, HATTERAS_BANK, MACHAPUNGA_BLUFF, ROANOKE_ISLAND);
        HATTERAS_BANK.addOverWaterLocations(EAST_PAMLICO_SOUND);
        CAPE_HATTERAS.addOverWaterLocations(OCRACOKE_INLET, GUN_INLET);
        MACHAPUNGA_BLUFF.addOverWaterLocations(WEST_PAMLICO_SOUND, EAST_PAMLICO_SOUND);
        MACHAPUNGA_BLUFF.addOverLandLocations(ROANOKE_RIVER);
        ROANOKE_RIVER.addOverWaterLocations(QUEEN_ANNES_CREEK, BATS_GRAVE, ALBEMARLE_SOUND);
        ROANOKE_RIVER.addOverLandLocations(BATH_TOWN, MACHAPUNGA_BLUFF, QUEEN_ANNES_CREEK);
        QUEEN_ANNES_CREEK.addOverWaterLocations(ROANOKE_RIVER, BATS_GRAVE, ALBEMARLE_SOUND);
        QUEEN_ANNES_CREEK.addOverLandLocations(ROANOKE_RIVER, BATS_GRAVE, ALBEMARLE_COUNTY);
        ALBEMARLE_COUNTY.addOverLandLocations(QUEEN_ANNES_CREEK, JAMES_RIVER);
        BATS_GRAVE.addOverWaterLocations(ALBEMARLE_SOUND, QUEEN_ANNES_CREEK, ROANOKE_RIVER);
        BATS_GRAVE.addOverLandLocations(QUEEN_ANNES_CREEK, PASQUOTANK_RIVER);
        PASQUOTANK_RIVER.addOverWaterLocations(ALBEMARLE_SOUND);
        PASQUOTANK_RIVER.addOverLandLocations(BATS_GRAVE);
        NOTS_ISLAND.addOverWaterLocations(CURRITUCK_SOUND, CURRITUCK_INLET);
        ALBEMARLE_SOUND.addOverWaterLocations(ROANOKE_RIVER, QUEEN_ANNES_CREEK, BATS_GRAVE, PASQUOTANK_RIVER, ROANOKE_ISLAND, ROANOKE_INLET, CURRITUCK_SOUND);
        ROANOKE_ISLAND.addOverWaterLocations(EAST_PAMLICO_SOUND, GUN_INLET, ROANOKE_INLET, ALBEMARLE_SOUND, CURRITUCK_SOUND);
        CURRITUCK_SOUND.addOverWaterLocations(ALBEMARLE_SOUND, ROANOKE_ISLAND, ROANOKE_INLET, NOTS_ISLAND);
        GUN_INLET.addOverWaterLocations(CAPE_HATTERAS, ROANOKE_ISLAND, ROANOKE_INLET);
        ROANOKE_INLET.addOverWaterLocations(GUN_INLET, ROANOKE_ISLAND, ALBEMARLE_SOUND, CURRITUCK_SOUND, CURRITUCK_INLET);
        CURRITUCK_INLET.addOverWaterLocations(NOTS_ISLAND, ROANOKE_INLET);
        JAMES_RIVER.addOverWaterLocations(CAPE_HENRY);
        JAMES_RIVER.addOverLandLocations(ALBEMARLE_COUNTY);
        CAPE_HENRY.addOverWaterLocations(JAMES_RIVER, CURRITUCK_INLET, ROANOKE_INLET, GUN_INLET, CAPE_HATTERAS, OCRACOKE_INLET, TOPSAIL_INLET);
    }

    private Locations() {
        throw new IllegalStateException("Utility class");
    }

    public static Location getLocationByName(Location.Name locationName) {
        return switch (locationName) {
            case TOPSAIL_INLET -> TOPSAIL_INLET;
            case FISH_TOWN -> FISH_TOWN;
            case NEUS_RIVER -> NEUS_RIVER;
            case BATH_TOWN -> BATH_TOWN;
            case NEW_BERN -> NEW_BERN;
            case HUNTING_QUARTER_SOUND -> HUNTING_QUARTER_SOUND;
            case CORE_BANKS -> CORE_BANKS;
            case WEST_PAMLICO_SOUND -> WEST_PAMLICO_SOUND;
            case OCRACOKE_INLET -> OCRACOKE_INLET;
            case OCRACOKE_ISLAND -> OCRACOKE_ISLAND;
            case EAST_PAMLICO_SOUND -> EAST_PAMLICO_SOUND;
            case HATTERAS_BANK -> HATTERAS_BANK;
            case CAPE_HATTERAS -> CAPE_HATTERAS;
            case MACHAPUNGA_BLUFF -> MACHAPUNGA_BLUFF;
            case ROANOKE_RIVER -> ROANOKE_RIVER;
            case QUEEN_ANNES_CREEK -> QUEEN_ANNES_CREEK;
            case ALBEMARLE_COUNTY -> ALBEMARLE_COUNTY;
            case BATS_GRAVE -> BATS_GRAVE;
            case PASQUOTANK_RIVER -> PASQUOTANK_RIVER;
            case NOTS_ISLAND -> NOTS_ISLAND;
            case ALBEMARLE_SOUND -> ALBEMARLE_SOUND;
            case ROANOKE_ISLAND -> ROANOKE_ISLAND;
            case CURRITUCK_SOUND -> CURRITUCK_SOUND;
            case GUN_INLET -> GUN_INLET;
            case ROANOKE_INLET -> ROANOKE_INLET;
            case CURRITUCK_INLET -> CURRITUCK_INLET;
            case JAMES_RIVER -> JAMES_RIVER;
            case CAPE_HENRY -> CAPE_HENRY;
        };
    }

    public static Set<Location> getPirateStartingLocations() {
        return locationSet.stream()
                .filter(location -> !(location instanceof Location.Land.Town))
                .filter(location -> !(location instanceof Location.Land.Blank))
                .filter(location -> !(location instanceof Location.Water.Blank))
                .collect(Collectors.toSet());
    }

    public static Set<Location.Name> getPirateSightingNames() {
        return locationSet.stream()
                .filter(Location::isPirateSighted)
                .map(Location::getName)
                .collect(Collectors.toSet());
    }

}
