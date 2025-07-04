package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.function.Predicate;

public class Pieces {

    public static final Piece HUNTER_SHIP_JANE = new HunterShip(Piece.Name.HUNTER_SHIP_JANE);
    public static final Piece HUNTER_SHIP_RANGER = new HunterShip(Piece.Name.HUNTER_SHIP_RANGER);
    public static final Piece HUNTER_CAPTAIN_BRAND = new HunterPerson(Piece.Name.HUNTER_CAPTAIN_BRAND);
    public static final Piece PIRATE_SHIP_ADVENTURE = new PirateShip(Piece.Name.PIRATE_SHIP_ADVENTURE);
    public static final Discoverer DISCOVERER = new Discoverer(Piece.Name.DISCOVERER);

    private Pieces() {
        throw new IllegalStateException("Utility class");
    }

    public static void initialize() {
        HUNTER_SHIP_JANE.setLocation(Locations.JAMES_RIVER);
        HUNTER_SHIP_RANGER.setLocation(Locations.JAMES_RIVER);
        HUNTER_CAPTAIN_BRAND.setLocation(Locations.JAMES_RIVER);
        PIRATE_SHIP_ADVENTURE.setLocation(Locations.OFF_BOARD);
        DISCOVERER.setLocation(Locations.OFF_BOARD);
    }

    public static Piece getPieceByName(Piece.Name name) {
        return switch (name) {
            case HUNTER_SHIP_JANE -> HUNTER_SHIP_JANE;
            case HUNTER_SHIP_RANGER -> HUNTER_SHIP_RANGER;
            case HUNTER_CAPTAIN_BRAND -> HUNTER_CAPTAIN_BRAND;
            case PIRATE_SHIP_ADVENTURE -> PIRATE_SHIP_ADVENTURE;
            case DISCOVERER -> DISCOVERER;
        };
    }

    public static boolean forAnyHunterPiece(Predicate<Piece> action) {
        return action.test(HUNTER_SHIP_JANE) ||
               action.test(HUNTER_SHIP_RANGER) ||
               action.test(HUNTER_CAPTAIN_BRAND);
    }

}
