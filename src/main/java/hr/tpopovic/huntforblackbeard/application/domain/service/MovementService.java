package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.GameState;
import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.port.in.ForMovingPieces;
import hr.tpopovic.huntforblackbeard.application.port.in.MovementCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.MovementResult;

import static java.util.Objects.requireNonNull;

public class MovementService implements ForMovingPieces {

    @Override
    public MovementResult move(MovementCommand command) {
        requireNonNull(command, "MovementCommand cannot be null");
        Piece piece = command.piece();
        Location destination = command.destination();

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
