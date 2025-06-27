package hr.tpopovic.huntforblackbeard.application.port.in;

import java.util.concurrent.CompletableFuture;

public interface ForFinishingTurn {

    CompletableFuture<TurnFinishResult> finishTurn();

}
