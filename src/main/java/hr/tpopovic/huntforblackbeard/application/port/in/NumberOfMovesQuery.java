package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Player;

import static java.util.Objects.requireNonNull;

public record NumberOfMovesQuery(
        Player.Type playerType
) {

    public NumberOfMovesQuery {
        requireNonNull(playerType, "Player type must not be null");
    }

}
