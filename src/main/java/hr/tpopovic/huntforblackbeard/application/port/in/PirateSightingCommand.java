package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;

import static java.util.Objects.requireNonNull;

public record PirateSightingCommand(
        Location.Name destinationName
) {

    public PirateSightingCommand {
        requireNonNull(destinationName, "Destination name cannot be null");
    }

}
