package hr.tpopovic.huntforblackbeard.application.port.in;

public interface ForMovingPieces {

    MovementLocationResult fetchAvailableMovementLocations(MovementLocationQuery query);

    MovementResult move(MovementCommand command);

}
