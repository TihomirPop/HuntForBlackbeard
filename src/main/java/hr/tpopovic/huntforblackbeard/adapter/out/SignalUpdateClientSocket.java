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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class SignalUpdateClientSocket implements ForSignalingUpdate {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public CompletableFuture<SignalUpdateResult> signal(SignalUpdateCommand command) {
        SignalUpdateRequest request = new SignalUpdateRequest(
                command.janeLocation().id,
                command.rangerLocation().id,
                command.brandLocation().id,
                command.adventureLocation().id,
                command.pirateSightings()
                        .stream()
                        .map(locationName -> locationName.id)
                        .collect(Collectors.toSet())
        );

        return CompletableFuture.supplyAsync(
                () -> sendRequest(request, "localhost", Application.CLIENT_PORT),
                executor
        );
    }

    private SignalUpdateResult sendRequest(SignalUpdateRequest request, String serverAddress, int serverPort) {
        try (Socket clientSocket = new Socket(serverAddress, serverPort)) {
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
