package hr.tpopovic.huntforblackbeard.application.domain.model;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record ReplayTurn(
        Player.Type playerType,
        List<ReplayMove> moves
) {

    public ReplayTurn {
        requireNonNull(playerType, "Player type cannot be null");
        requireNonNull(moves, "Moves cannot be null");
    }

}
