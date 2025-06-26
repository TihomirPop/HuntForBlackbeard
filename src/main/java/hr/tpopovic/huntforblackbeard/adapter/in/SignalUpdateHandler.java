package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.port.in.ForUpdatingGameState;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateCommand;
import hr.tpopovic.huntforblackbeard.application.port.in.UpdateGameStateResult;
import hr.tpopovic.huntforblackbeard.message.Response;
import hr.tpopovic.huntforblackbeard.message.SignalUpdateRequest;
import hr.tpopovic.huntforblackbeard.message.SignalUpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignalUpdateHandler {

    private static final Logger log = LoggerFactory.getLogger(SignalUpdateHandler.class);

    private final ForUpdatingGameState forUpdatingGameState;

    public SignalUpdateHandler(ForUpdatingGameState forUpdatingGameState) {
        this.forUpdatingGameState = forUpdatingGameState;
    }

    public SignalUpdateResponse update(SignalUpdateRequest request) {
        UpdateGameStateCommand command = new UpdateGameStateCommand(
                Location.Name.findById(request.getJaneLocation()),
                Location.Name.findById(request.getRangerLocation()),
                Location.Name.findById(request.getBrandLocation()),
                Location.Name.findById(request.getAdventureLocation())
        );

        UpdateGameStateResult result = forUpdatingGameState.update(command);

        return switch (result) {
            case UpdateGameStateResult.Success _ -> success();
            case UpdateGameStateResult.Failure failure -> failure(failure);
        };
    }

    private SignalUpdateResponse success() {
        return new SignalUpdateResponse(Response.Result.SUCCESS);
    }

    private SignalUpdateResponse failure(UpdateGameStateResult.Failure failure) {
        log.error("Signal update failed: {}", failure.getMessage());
        return new SignalUpdateResponse(Response.Result.FAILURE);
    }

}
