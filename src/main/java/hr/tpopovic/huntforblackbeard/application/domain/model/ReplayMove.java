package hr.tpopovic.huntforblackbeard.application.domain.model;

import static java.util.Objects.requireNonNull;

public record ReplayMove(
        Piece.Name pieceName,
        Location.Name destinationName
) {

    public ReplayMove {
        requireNonNull(pieceName, "Piece name cannot be null");
        requireNonNull(destinationName, "Destination name cannot be null");
    }

}
