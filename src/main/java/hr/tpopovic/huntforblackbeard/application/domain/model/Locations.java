package hr.tpopovic.huntforblackbeard.application.domain.model;

public class Locations {

    public static final Location TOPSAIL_INLET = new Location.Water.Ocean("Topsail Inlet");
    public static final Location FISH_TOWN = new Location.Land.Town("Fish Town");
    public static final Location NEUS_RIVER = new Location.Land.Anchorage("Neuse River");
    public static final Location BATH_TOWN = new Location.Land.Town("Bath Town");
    public static final Location NEW_BERN = new Location.Land.Blank("New Bern");
    public static final Location HUNTING_QUARTER_SOUND = new Location.Water.Sound("Hunting Quarter Sound");
    public static final Location CORE_BANKS = new Location.Land.Anchorage("Core Banks");
    public static final Location WEST_PAMLICO_SOUND = new Location.Water.Sound("West Pamlico Sound");
    public static final Location OCRACOKE_INLET = new Location.Water.Ocean("Ocracoke Inlet");
    public static final Location OCRACOKE_ISLAND = new Location.Land.Anchorage("Ocracoke Island");
    public static final Location EAST_PAMLICO_SOUND = new Location.Water.Sound("East Pamlico Sound");
    public static final Location HATTERAS_BANK = new Location.Land.Anchorage("Hatteras Bank");
    public static final Location CAPE_HATTERAS = new Location.Water.Ocean("Cape Hatteras");
    public static final Location MACHAPUNGA_BLUFF = new Location.Land.Anchorage("Machapunga Bluff");
    public static final Location ROANOKE_RIVER = new Location.Land.Anchorage("Roanoke River");
    public static final Location QUEEN_ANNES_CREEK = new Location.Land.Town("Queen Anne's Creek");
    public static final Location ALBEMARLE_COUNTY = new Location.Land.Blank("Albemarle County");
    public static final Location BATS_GRAVE = new Location.Land.Anchorage("Bats Grave");
    public static final Location PASQUOTANK_RIVER = new Location.Land.Anchorage("Pasquotank River");
    public static final Location NOTS_ISLAND = new Location.Land.Anchorage("Nots Island");
    public static final Location ALBEMARLE_SOUND = new Location.Water.Sound("Albemarle Sound");
    public static final Location ROANOKE_ISLAND = new Location.Land.Anchorage("Roanoke Island");
    public static final Location CURRITUCK_SOUND = new Location.Water.Sound("Currituck Sound");
    public static final Location GUN_INLET = new Location.Water.Ocean("Gun Inlet");
    public static final Location ROANOKE_INLET = new Location.Water.Ocean("Roanoke Inlet");
    public static final Location CURRITUCK_INLET = new Location.Water.Ocean("Currituck Inlet");
    public static final Location JAMES_RIVER = new Location.Water.Blank("James River");
    public static final Location CAPE_HENRY = new Location.Water.Blank("Cape Henry");

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
        QUEEN_ANNES_CREEK.addOverLandLocations(ROANOKE_RIVER, BATH_TOWN, MACHAPUNGA_BLUFF, ALBEMARLE_COUNTY);
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

}
