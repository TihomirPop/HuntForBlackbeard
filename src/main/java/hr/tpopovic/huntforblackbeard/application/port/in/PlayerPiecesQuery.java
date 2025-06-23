package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Player;

import static java.util.Objects.requireNonNull;

public record PlayerPiecesQuery(
        Player.Type playerType
) {

    public PlayerPiecesQuery {
        requireNonNull(playerType, "Player type must not be null");
    }
}
