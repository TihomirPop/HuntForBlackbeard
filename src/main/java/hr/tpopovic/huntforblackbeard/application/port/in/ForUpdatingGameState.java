package hr.tpopovic.huntforblackbeard.application.port.in;

public interface ForUpdatingGameState {

    UpdateGameStateResult update(UpdateGameStateCommand command);

}
