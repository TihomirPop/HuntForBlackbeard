package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.port.in.ExploredLocationsResult;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFetchingExploredLocations;

public class ExploredLocationService implements ForFetchingExploredLocations {

    @Override
    public ExploredLocationsResult fetch() {
        return new ExploredLocationsResult.Success(
                GameState.getCountOfLocationsDiscoveredByPirate(),
                GameState.getLocationsNeededToWin()
        );
    }

}
