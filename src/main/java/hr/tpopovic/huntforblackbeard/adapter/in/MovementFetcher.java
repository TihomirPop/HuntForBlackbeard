package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.application.port.in.ForMovingPieces;
import hr.tpopovic.huntforblackbeard.application.port.in.MovementLocationQuery;
import hr.tpopovic.huntforblackbeard.application.port.in.MovementLocationResult;
import hr.tpopovic.huntforblackbeard.ioc.IocContainer;

import java.util.Map;
import java.util.Set;

public class MovementFetcher {

    private final ForMovingPieces forMovingPieces = IocContainer.getInstance(ForMovingPieces.class);
    private final FXLocations locations;

    public MovementFetcher(FXLocations locations) {
        this.locations = locations;
    }

    void updateMapWithAvailablePositionsForCurrentlySelectedPiece(FXPiece currentlySelectedPiece) {
        MovementLocationQuery query = new MovementLocationQuery(currentlySelectedPiece.name());
        MovementLocationResult result = forMovingPieces.fetchAvailableMovementLocations(query);

        switch (result) {
            case MovementLocationResult.Success success -> success(success);
            case MovementLocationResult.Failure failure -> failure(failure);
        }
    }

    private void success(MovementLocationResult.Success success) {
        Map<Boolean, Set<FXLocation>> movementAvailableToLocations = locations.partitionByContains(success.getLocations());
        movementAvailableToLocations.getOrDefault(true, Set.of())
                .forEach(location -> location.button().setVisible(true));
        movementAvailableToLocations.getOrDefault(false, Set.of())
                .forEach(location -> location.button().setVisible(false));
    }

    private void failure(MovementLocationResult.Failure failure) {
        AlertManager.showInfo("Failed to fetch movement locations", failure.getMessage());
    }

}