package hr.tpopovic.huntforblackbeard.adapter.in;

public record InitializationResult(
        FXLocations locations,
        FXPieces pieces,
        FXPiece currentlySelectedPiece,
        NumberOfMovesHandler numberOfMovesHandler,
        MovementFetcher movementFetcher
) {

}
