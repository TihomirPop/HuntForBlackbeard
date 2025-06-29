package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.*;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class GameState {

    private static Integer LOCATIONS_NEEDED_TO_WIN = 5;

    private static Player currentPlayer = Players.PIRATE;
    private static Integer turnCount = 0;
    private static final Set<Location> locationsDiscoveredByPirate = new HashSet<>();

    private GameState() {
    }

    public static void endTurn() {
        turnCount++;
        currentPlayer.resetMoves();
        if (currentPlayer == Players.HUNTER) {
            Locations.clearPirateSightings();
            Pieces.DISCOVERER.clearPirateSightings();
            getCurrentPlayerPieces().stream()
                    .filter(HunterPiece.class::isInstance)
                    .map(HunterPiece.class::cast)
                    .forEach(HunterPiece::clearSearching);

            currentPlayer = Players.PIRATE;
        } else {
            currentPlayer = Players.HUNTER;
        }
    }

    public static boolean canCurrentPlayerMove() {
        return currentPlayer.canUseMove();
    }

    public static boolean isNotCurrentPlayersPiece(Piece piece) {
        return !currentPlayer.getPieces().contains(piece);
    }

    public static void currentPlayerMoves() {
        if (canCurrentPlayerMove()) {
            currentPlayer.useMove();
        } else {
            throw new IllegalStateException("Current player cannot move, no moves left.");
        }
    }

    public static Set<Piece> getCurrentPlayerPieces() {
        return currentPlayer.getPieces();
    }

    public static Integer getCurrentPlayerMoves() {
        return currentPlayer.getNumberOfMoves();
    }

    public static boolean isFirstTurn() {
        return turnCount == 0;
    }

    public static boolean isFirstMove() {
        return currentPlayer.isFirstMove();
    }

    public static boolean isNotCurrentPlayerHunter() {
        return currentPlayer != Players.HUNTER;
    }

    public static Winner getWinner() {
        if (hasPirateWon()) {
            return Winner.PIRATE;
        } else if (hasHunterWon()) {
            return Winner.HUNTER;
        } else {
            return Winner.ONGOING;
        }
    }

    private static boolean hasPirateWon() {
        return locationsDiscoveredByPirate.size() >= LOCATIONS_NEEDED_TO_WIN;
    }

    private static boolean hasHunterWon() {
        Location adventureLocation = Pieces.PIRATE_SHIP_ADVENTURE.getLocation();
        boolean adventureHasBeenDiscovered = Pieces.DISCOVERER.getPirateSightings().contains(adventureLocation);
        boolean hunterIsOnSameLocationAsAdventure = Pieces.forAnyHunterPiece(piece -> piece.getLocation().equals(adventureLocation));
        return adventureHasBeenDiscovered && hunterIsOnSameLocationAsAdventure;
    }

    public static void addLocationDiscoveredByPirate(Location destination) {
        requireNonNull(destination, "Location cannot be null");
        locationsDiscoveredByPirate.add(destination);
    }

    static Player.Type getCurrentPlayerType() {
        return currentPlayer.getType();
    }

    public enum Winner {
        HUNTER("Hunter"),
        PIRATE("Pirate"),
        ONGOING("Ongoing");

        private final String name;

        Winner(String name) {
            this.name = name;
        }

        public static Winner findByName(String winner) {
            return switch (winner) {
                case "Hunter" -> HUNTER;
                case "Pirate" -> PIRATE;
                case "Ongoing" -> ONGOING;
                default -> throw new IllegalArgumentException("Unknown winner: " + winner);
            };
        }

        public String getName() {
            return name;
        }
    }

}
