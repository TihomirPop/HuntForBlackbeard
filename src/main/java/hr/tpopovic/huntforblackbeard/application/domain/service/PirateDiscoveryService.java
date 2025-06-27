package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.GameState;
import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.application.port.in.ForDiscoveringPirateSightings;
import hr.tpopovic.huntforblackbeard.application.port.in.PirateSightingStartCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.PirateSightingResult;

import static java.util.Objects.requireNonNull;

public class PirateDiscoveryService implements ForDiscoveringPirateSightings {

    @Override
    public PirateSightingResult startDiscovery(PirateSightingStartCommand command) {
        requireNonNull(command, "PirateSightingCommand cannot be null");
        Piece piece = Pieces.getPieceByName(command.pieceName());

        if(!GameState.isCurrentPlayerHunter()) {
            return new PirateSightingResult.Failure("Only hunters can discover pirate sightings.");
        }

        if (!GameState.isCurrentPlayersPiece(piece)) {
            return new PirateSightingResult.Failure("It's not this piece's turn to discover pirates.");
        }

        if(!GameState.canCurrentPlayerMove()) {
            return new PirateSightingResult.Failure("Player has no moves left.");
        }

        Location location = piece.getLocation();

        if(location.getPieces().contains(Pieces.PIRATE_SHIP_ADVENTURE)) {
            return new PirateSightingResult.Found();
        }

        if(location.isPirateSighted()) {
            return new PirateSightingResult.Sighted();
        }

        return new PirateSightingResult.NotSighted();
    }

}
