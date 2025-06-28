package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.application.port.in.TurnFinishResult;

import java.util.concurrent.CompletableFuture;

public class FinishTurnHandler {

    private FinishTurnHandler() {
    }

    public static void finish(CompletableFuture<TurnFinishResult> futureResult) {
        futureResult.thenAccept(result -> {
            switch (result) {
                case TurnFinishResult.GameOngoing _ -> gameOngoing();
                case TurnFinishResult.HunterWins _ -> hunterWins();
                case TurnFinishResult.PirateWins _ -> pirateWins();
                case TurnFinishResult.Failure failure -> failure(failure);
            }
        });
    }

    private static void gameOngoing() {
        // No action needed, game continues
    }

    private static void hunterWins() {
        AlertManager.showInfo("Game Over", "The Hunter has won the game!");
    }

    private static void pirateWins() {
        AlertManager.showInfo("Game Over", "The Pirate has won the game!");
    }

    private static void failure(TurnFinishResult.Failure failure) {
        AlertManager.showInfo("Turn Finish Error", failure.getMessage());
    }
}
