package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;

import static java.util.Objects.requireNonNull;

public record PirateSightingStartCommand(
        Piece.Name pieceName
) {

    public PirateSightingStartCommand {
        requireNonNull(pieceName, "Piece name cannot be null");
    }

}
