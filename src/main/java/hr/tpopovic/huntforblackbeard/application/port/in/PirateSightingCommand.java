package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;

import static java.util.Objects.requireNonNull;

public record PirateSightingCommand(
        Piece.Name pieceName,
        Location.Name destinationName
) {

    public PirateSightingCommand {
        requireNonNull(pieceName, "Piece name cannot be null");
        requireNonNull(destinationName, "Destination name cannot be null");
    }

}
