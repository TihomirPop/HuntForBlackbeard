package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;

import static java.util.Objects.requireNonNull;

public record MovementLocationQuery(
        Piece.Name pieceName
) {

    public MovementLocationQuery {
        requireNonNull(pieceName, "Piece name cannot be null");
    }

    public MovementLocationQuery(String name) {
        this(Piece.Name.findByName(name));
    }

}
