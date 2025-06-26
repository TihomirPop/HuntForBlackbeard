package hr.tpopovic.huntforblackbeard.application.port.out;

public interface ForSignalingUpdate {

    SignalUpdateResult signal(SignalUpdateCommand command);

}
