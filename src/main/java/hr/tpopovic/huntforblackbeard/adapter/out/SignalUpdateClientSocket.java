package hr.tpopovic.huntforblackbeard.adapter.out;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.application.port.out.ForSignalingUpdate;
import hr.tpopovic.huntforblackbeard.application.port.out.SignalUpdateCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SignalUpdateResult;
import hr.tpopovic.huntforblackbeard.message.SignalUpdateRequest;
import hr.tpopovic.huntforblackbeard.message.SignalUpdateResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SignalUpdateClientSocket implements ForSignalingUpdate {

    @Override
    public SignalUpdateResult signal(SignalUpdateCommand command) {

        SignalUpdateRequest request = new SignalUpdateRequest(
                command.janeLocation().id,
                command.rangerLocation().id,
                command.brandLocation().id,
                command.adventureLocation().id
        );

        try (Socket clientSocket = new Socket("localhost", Application.SERVER_PORT)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream.writeObject(request);
            if (inputStream.readObject() instanceof SignalUpdateResponse response) {
                if (response.isSuccess()) {
                    return new SignalUpdateResult.Success();
                } else {
                    return new SignalUpdateResult.Failure("Signal update failed");
                }
            } else {
                return new SignalUpdateResult.Failure("Invalid response from server");
            }

        } catch (IOException | ClassNotFoundException e) {
            return new SignalUpdateResult.Failure("Error communicating with server: " + e.getMessage());
        }
    }

}
