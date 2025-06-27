package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;

import static java.util.Objects.requireNonNull;

public record PirateSightingCommand(
        Piece.Name pieceName,
        Location.Name locationName
) {

    public PirateSightingCommand {
        requireNonNull(pieceName, "Piece name cannot be null");
    }

    public PirateSightingCommand(Piece.Name pieceName) {
        this(pieceName, null);
    }
}
