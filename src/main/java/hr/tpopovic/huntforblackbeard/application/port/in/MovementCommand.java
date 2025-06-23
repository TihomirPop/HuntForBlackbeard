package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;

import static java.util.Objects.requireNonNull;

public record MovementCommand(
        Piece.Name pieceName,
        Location destination
) {

    public MovementCommand {
        requireNonNull(pieceName, "Piece name cannot be null");
        requireNonNull(destination, "Destination cannot be null");
    }

}
