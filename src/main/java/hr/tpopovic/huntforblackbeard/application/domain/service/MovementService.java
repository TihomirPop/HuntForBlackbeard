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
        Location destination = command.destination();
        Piece piece = Pieces.getPieceByName(pieceName);

        if (!GameState.canCurrentPlayerMove()) {
            return new MovementResult.Failure("Player cannot move.");
        }

        if (!GameState.isCurrentPlayersPiece(piece)) {
            return new MovementResult.Failure("Piece is not owned by the player.");
        }

        try {
            GameState.currentPlayerMoves();
            piece.move(destination);
            return new MovementResult.Success();
        } catch (IllegalArgumentException e) {
            return new MovementResult.Failure("Cannot move to the specified destination: " + e.getMessage());
        }
    }

}
