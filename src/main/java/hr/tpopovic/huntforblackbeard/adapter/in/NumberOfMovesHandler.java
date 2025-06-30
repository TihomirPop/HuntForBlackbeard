package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.application.port.in.ForMovingPieces;
import hr.tpopovic.huntforblackbeard.application.port.in.NumberOfMovesQuery;
import hr.tpopovic.huntforblackbeard.application.port.in.NumberOfMovesResult;
import javafx.scene.text.Text;

import static java.util.Objects.requireNonNull;

public class NumberOfMovesHandler {

    private static final String REMAINING_MOVES_FORMAT = "Remaining moves: %s";

    private final ForMovingPieces forMovingPieces;
    private final Text numberOfMovesText;

    public NumberOfMovesHandler(ForMovingPieces forMovingPieces, Text numberOfMovesText) {
        this.forMovingPieces = forMovingPieces;
        this.numberOfMovesText = numberOfMovesText;
    }

    public void updateNumberOfMoves() {
        NumberOfMovesQuery query = new NumberOfMovesQuery(AppProperties.getPlayerType());
        NumberOfMovesResult result = forMovingPieces.fetchNumberOfAvailableMoves(query);

        switch (result) {
            case NumberOfMovesResult.Success success -> success(success);
            case NumberOfMovesResult.Failure failure -> failure(failure);
        }
    }

    public void setNumberOfMoves(Integer numberOfMoves) {
        requireNonNull(numberOfMoves, "Number of moves cannot be null");
        numberOfMovesText.setText(REMAINING_MOVES_FORMAT.formatted(numberOfMoves));
    }

    private void success(NumberOfMovesResult.Success success) {
        numberOfMovesText.setText(REMAINING_MOVES_FORMAT.formatted(success.getNumberOfMoves()));
    }

    private void failure(NumberOfMovesResult.Failure failure) {
        AlertManager.showInfo("Failed to fetch number of moves", failure.getMessage());
    }

}
