package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.application.port.in.ExploredLocationsResult;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFetchingExploredLocations;
import javafx.scene.text.Text;

public class PirateScoreUpdater {

    private static final String LOCATIONS_NEEDED_TO_WIN_FORMAT = "Locations needed to win: %d/%d";
    private final ForFetchingExploredLocations forFetchingExploredLocations;

    public PirateScoreUpdater(ForFetchingExploredLocations forFetchingExploredLocations) {
        this.forFetchingExploredLocations = forFetchingExploredLocations;
    }

    void updateText(Text locationsNeededToWinText) {
        ExploredLocationsResult result = forFetchingExploredLocations.fetch();
        switch (result) {
            case ExploredLocationsResult.Success success -> success(success, locationsNeededToWinText);
            case ExploredLocationsResult.Failure failure -> failure(failure);
        }
    }

    private void success(ExploredLocationsResult.Success success, Text locationsNeededToWinText) {
        Integer exploredLocationsCount = success.getExploredLocationsCount();
        Integer neededLocationsCount = success.getNeededLocationsCount();
        String text = LOCATIONS_NEEDED_TO_WIN_FORMAT.formatted(exploredLocationsCount, neededLocationsCount);
        locationsNeededToWinText.setText(text);
    }

    private void failure(ExploredLocationsResult.Failure failure) {
        AlertManager.showInfo("Failed to fetch explored locations", failure.getMessage());
    }

}
