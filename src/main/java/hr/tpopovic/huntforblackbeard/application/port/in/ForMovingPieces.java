package hr.tpopovic.huntforblackbeard.application.port.in;

public interface ForMovingPieces {

    NumberOfMovesResult fetchNumberOfAvailableMoves(NumberOfMovesQuery query);

    MovementLocationResult fetchAvailableMovementLocations(MovementLocationQuery query);

    MovementResult move(MovementCommand command);

}
