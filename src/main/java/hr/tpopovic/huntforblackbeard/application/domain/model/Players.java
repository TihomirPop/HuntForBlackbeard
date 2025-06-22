package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.Set;

public class Players {

    private static final int HUNTER_STARTING_NUMBER_OF_MOVES = 7;
    private static final int PIRATE_STARTING_NUMBER_OF_MOVES = 5;
    public static final Player HUNTER = new Player(
            Player.Type.HUNTER,
            Set.of(
                    Pieces.HUNTER_SHIP_JANE,
                    Pieces.HUNTER_SHIP_RANGER,
                    Pieces.HUNTER_CAPTAIN_BRAND
            ),
            HUNTER_STARTING_NUMBER_OF_MOVES
    );
    public static final Player PIRATE = new Player(
            Player.Type.PIRATE,
            Set.of(Pieces.PIRATE_SHIP_ADVENTURE),
            PIRATE_STARTING_NUMBER_OF_MOVES
    );

    private Players() {
    }

}
