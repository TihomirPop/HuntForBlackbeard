package hr.tpopovic.huntforblackbeard.application.domain.model;

public class Pieces {

    public static final Piece HUNTER_SHIP_JANE = new Ship(Piece.Name.HUNTER_SHIP_JANE, Locations.JAMES_RIVER);
    public static final Piece HUNTER_SHIP_RANGER = new Ship(Piece.Name.HUNTER_SHIP_RANGER, Locations.JAMES_RIVER);
    public static final Piece HUNTER_CAPTAIN_BRAND = new Person(Piece.Name.HUNTER_CAPTAIN_BRAND, Locations.JAMES_RIVER);
    public static final Piece PIRATE_SHIP_ADVENTURE = new Ship(Piece.Name.PIRATE_SHIP_ADVENTURE);

    private Pieces() {
        throw new IllegalStateException("Utility class");
    }

}
