package hr.tpopovic.huntforblackbeard.application.port.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;

import static java.util.Objects.requireNonNull;

public record UpdateGameStateCommand(
        Location.Name janeLocation,
        Location.Name rangerLocation,
        Location.Name brandLocation,
        Location.Name adventureLocation
) {

    public UpdateGameStateCommand {
        requireNonNull(janeLocation, "Jane's location cannot be null");
        requireNonNull(rangerLocation, "Ranger's location cannot be null");
        requireNonNull(brandLocation, "Brand's location cannot be null");
        requireNonNull(adventureLocation, "Adventure's location cannot be null");
    }

}
