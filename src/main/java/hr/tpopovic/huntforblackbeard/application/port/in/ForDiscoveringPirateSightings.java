package hr.tpopovic.huntforblackbeard.application.port.in;

public interface ForDiscoveringPirateSightings {

    PirateSightingResult discover(PirateSightingCommand query);

}
