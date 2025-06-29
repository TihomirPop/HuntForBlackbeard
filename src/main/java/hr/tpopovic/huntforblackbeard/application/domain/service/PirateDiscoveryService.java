package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.*;
import hr.tpopovic.huntforblackbeard.application.port.in.ForDiscoveringPirateSightings;
import hr.tpopovic.huntforblackbeard.application.port.in.PirateSightingCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.PirateSightingResult;
import hr.tpopovic.huntforblackbeard.application.port.in.PirateSightingStartCommand;

import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class PirateDiscoveryService implements ForDiscoveringPirateSightings {

    @Override
    public PirateSightingResult startDiscovery(PirateSightingStartCommand command) {
        requireNonNull(command, "PirateSightingCommand cannot be null");
        HunterPiece piece = (HunterPiece) Pieces.getPieceByName(command.pieceName());

        if (GameState.isNotCurrentPlayerHunter()) {
            return new PirateSightingResult.Failure("Only hunters can discover pirate sightings.");
        }

        if (GameState.isNotCurrentPlayersPiece(piece)) {
            return new PirateSightingResult.Failure("It's not this piece's turn to discover pirates.");
        }

        if(piece.hasFinishedSearching()) {
            return new PirateSightingResult.Failure("Hunter piece has already finished searching and cannot start again.");
        }

        piece.startSearching();
        Location location = piece.getLocation();
        Pieces.DISCOVERER.changeLocation(location);

        return getPirateSightingResult(location, piece);
    }

    @Override
    public PirateSightingResult discover(PirateSightingCommand command) {
        requireNonNull(command, "PirateSightingCommand cannot be null");
        Location destination = Locations.getLocationByName(command.destinationName());
        HunterPiece piece = (HunterPiece) Pieces.getPieceByName(command.pieceName());

        if (GameState.isNotCurrentPlayerHunter()) {
            return new PirateSightingResult.Failure("Only hunters can discover pirate sightings.");
        }

        if(piece.hasFinishedSearching()) {
            return new PirateSightingResult.Failure("Hunter piece has already finished searching and cannot discover again.");
        }

        Pieces.DISCOVERER.changeLocation(destination);

        return getPirateSightingResult(destination, piece);
    }

    private PirateSightingResult getPirateSightingResult(Location destination, HunterPiece piece) {
        if (destination.getPieces().contains(Pieces.PIRATE_SHIP_ADVENTURE)) {
            Pieces.DISCOVERER.addPirateSighting(destination);
            piece.finishSearching();
            return new PirateSightingResult.Found();
        }

        if (destination.isPirateSighted()) {
            Pieces.DISCOVERER.addPirateSighting(destination);
            return new PirateSightingResult.Sighted(
                    destination.getName(),
                    Pieces.DISCOVERER.getAvailableDestinations()
                            .stream()
                            .map(Location::getName)
                            .collect(Collectors.toSet())
            );
        }

        piece.finishSearching();
        return new PirateSightingResult.NotSighted();
    }

}
