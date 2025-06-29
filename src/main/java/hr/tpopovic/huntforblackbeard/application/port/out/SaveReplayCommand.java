package hr.tpopovic.huntforblackbeard.application.port.out;

import hr.tpopovic.huntforblackbeard.application.domain.model.ReplayTurn;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record SaveReplayCommand(
        List<ReplayTurn> replayTurns
) {

    public SaveReplayCommand {
        requireNonNull(replayTurns, "Replay turns cannot be null");
    }

}
