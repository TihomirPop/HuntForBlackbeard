package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.*;
import hr.tpopovic.huntforblackbeard.application.port.in.*;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class MovementService implements ForMovingPieces {

    @Override
    public NumberOfMovesResult fetchNumberOfAvailableMoves(NumberOfMovesQuery query) {
        requireNonNull(query, "NumberOfMovesQuery cannot be null");
        Player.Type playerType = query.playerType();
        Integer numberOfMoves = Players.getPlayerByType(playerType).getNumberOfMoves();
        return new NumberOfMovesResult.Success(numberOfMoves);
    }

    @Override
    public MovementLocationResult fetchAvailableMovementLocations(MovementLocationQuery query) {
        requireNonNull(query, "MovementLocationQuery cannot be null");
        Piece.Name pieceName = query.pieceName();
        Piece piece = Pieces.getPieceByName(pieceName);
        Set<Location.Name> locations = piece.getAvailableDestinations()
                .stream()
                .map(Location::getName)
                .collect(Collectors.toSet());
        return new MovementLocationResult.Success(locations);
    }

    @Override
    public MovementResult move(MovementCommand command) {
        requireNonNull(command, "MovementCommand cannot be null");
        Piece.Name pieceName = command.pieceName();
        Location.Name destinationName = command.destination();
        Piece piece = Pieces.getPieceByName(pieceName);
        Location destination = Locations.getLocationByName(destinationName);

        if (!GameState.canCurrentPlayerMove()) {
            return new MovementResult.Failure("Player has no moves left.");
        }

        if (GameState.isNotCurrentPlayersPiece(piece)) {
            return new MovementResult.Failure("It's not this piece's turn to move.");
        }

        if(piece instanceof HunterPiece hunterPiece && hunterPiece.hasStartedSearching()) {
            return new MovementResult.Failure("Hunter piece has already started searching and cannot move.");
        }

        try {
            piece.move(destination);
            GameState.currentPlayerMoves();
            Integer numberOfMoves = GameState.getCurrentPlayerMoves();
            ReplayManager.makeMove(piece, destination);
            return new MovementResult.Success(numberOfMoves);
        } catch (IllegalArgumentException e) {
            return new MovementResult.Failure("Cannot move to the specified destination: " + e.getMessage());
        }
    }

}
