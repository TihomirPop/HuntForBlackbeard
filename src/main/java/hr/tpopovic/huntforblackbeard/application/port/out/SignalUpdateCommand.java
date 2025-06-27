package hr.tpopovic.huntforblackbeard.application.port.out;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public record SignalUpdateCommand(
        Location.Name janeLocation,
        Location.Name rangerLocation,
        Location.Name brandLocation,
        Location.Name adventureLocation,
        Set<Location.Name> pirateSightings
) {

    public SignalUpdateCommand {
        requireNonNull(janeLocation, "Jane's location cannot be null");
        requireNonNull(rangerLocation, "Ranger's location cannot be null");
        requireNonNull(brandLocation, "Brand's location cannot be null");
        requireNonNull(adventureLocation, "Adventure's location cannot be null");
        requireNonNull(pirateSightings, "Pirate sightings cannot be null");
    }

}
