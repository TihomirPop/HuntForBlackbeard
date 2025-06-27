package hr.tpopovic.huntforblackbeard.application.port.in;

public interface ForDiscoveringPirateSightings {

    PirateSightingResult startDiscovery(PirateSightingStartCommand command);

}
