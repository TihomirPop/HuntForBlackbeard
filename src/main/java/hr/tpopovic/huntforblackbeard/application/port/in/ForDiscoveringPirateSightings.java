package hr.tpopovic.huntforblackbeard.application.port.in;

public interface ForDiscoveringPirateSightings {

    PirateSightingResult startDiscovery(PirateSightingStartCommand command);

    PirateSightingResult discover(PirateSightingCommand command);

}
