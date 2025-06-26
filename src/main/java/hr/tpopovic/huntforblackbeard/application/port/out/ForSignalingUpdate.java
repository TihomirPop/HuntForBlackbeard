package hr.tpopovic.huntforblackbeard.application.port.out;

import java.util.concurrent.CompletableFuture;

public interface ForSignalingUpdate {

    CompletableFuture<SignalUpdateResult> signal(SignalUpdateCommand command);

}
