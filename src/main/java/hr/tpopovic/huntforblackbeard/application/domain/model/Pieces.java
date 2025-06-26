package hr.tpopovic.huntforblackbeard.application.domain.model;

public class Pieces {

    public static final Piece HUNTER_SHIP_JANE = new Ship(Piece.Name.HUNTER_SHIP_JANE);
    public static final Piece HUNTER_SHIP_RANGER = new Ship(Piece.Name.HUNTER_SHIP_RANGER);
    public static final Piece HUNTER_CAPTAIN_BRAND = new Person(Piece.Name.HUNTER_CAPTAIN_BRAND);
    public static final Piece PIRATE_SHIP_ADVENTURE = new Ship(Piece.Name.PIRATE_SHIP_ADVENTURE);

    private Pieces() {
        throw new IllegalStateException("Utility class");
    }

    public static void initialize() {
        HUNTER_SHIP_JANE.setLocation(Locations.JAMES_RIVER);
        HUNTER_SHIP_RANGER.setLocation(Locations.JAMES_RIVER);
        HUNTER_CAPTAIN_BRAND.setLocation(Locations.JAMES_RIVER);
        PIRATE_SHIP_ADVENTURE.setLocation(Locations.JAMES_RIVER);
    }

    public static Piece getPieceByName(Piece.Name name) {
        return switch (name) {
            case HUNTER_SHIP_JANE -> HUNTER_SHIP_JANE;
            case HUNTER_SHIP_RANGER -> HUNTER_SHIP_RANGER;
            case HUNTER_CAPTAIN_BRAND -> HUNTER_CAPTAIN_BRAND;
            case PIRATE_SHIP_ADVENTURE -> PIRATE_SHIP_ADVENTURE;
        };
    }

}
