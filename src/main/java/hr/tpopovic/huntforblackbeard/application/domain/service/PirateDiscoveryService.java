package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.*;
import hr.tpopovic.huntforblackbeard.application.port.in.ForDiscoveringPirateSightings;
import hr.tpopovic.huntforblackbeard.application.port.in.PirateSightingCommand;
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

        if(piece instanceof HunterPiece hunterPiece) {
            hunterPiece.startSearching();
        }

        Location location = piece.getLocation();

        return getPirateSightingResult(location);
    }

    @Override
    public PirateSightingResult discover(PirateSightingCommand command) {
        requireNonNull(command, "PirateSightingCommand cannot be null");
        Location destination = Locations.getLocationByName(command.destinationName());
        Piece piece = Pieces.DISCOVERER;

        if(!GameState.isCurrentPlayerHunter()) {
            return new PirateSightingResult.Failure("Only hunters can discover pirate sightings.");
        }

        if(!piece.getAvailableDestinations().contains(destination)) {
            return new PirateSightingResult.Failure("Cannot discover pirates at the specified location: " + destination.getName());
        }

        return getPirateSightingResult(destination);
    }

    private static PirateSightingResult getPirateSightingResult(Location location) {
        if(location.getPieces().contains(Pieces.PIRATE_SHIP_ADVENTURE)) {
            return new PirateSightingResult.Found();
        }

        if(location.isPirateSighted()) {
            return new PirateSightingResult.Sighted(location.getName());
        }

        return new PirateSightingResult.NotSighted();
    }

}
