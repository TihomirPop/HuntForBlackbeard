package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.socket.Response;
import hr.tpopovic.huntforblackbeard.socket.SignalUpdateRequest;
import hr.tpopovic.huntforblackbeard.socket.SignalUpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignalUpdateServerSocket {

    private static final Logger log = LoggerFactory.getLogger(SignalUpdateServerSocket.class);

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final SignalUpdateHandler signalUpdateHandler;
    private volatile boolean running = true;

    public SignalUpdateServerSocket(SignalUpdateHandler signalUpdateHandler) {
        this.signalUpdateHandler = signalUpdateHandler;
    }

    public void start() {
        executor.submit(this::startSocket);
        log.info("SignalUpdateServerSocket started");
    }

    public void stop() {
        running = false;
        executor.shutdownNow();
        log.info("SignalUpdateServerSocket stopped");
    }

    private void startSocket() {
        try (ServerSocket serverSocket = new ServerSocket(AppProperties.getServerPort())) {
            log.info("Server started on port: {}", AppProperties.getServerPort());
            while (running) {
                waitForAndHandleRequest(serverSocket);
            }
        } catch (IOException e) {
            log.error("Failed to start server socket", e);
        }
    }

    private void waitForAndHandleRequest(ServerSocket serverSocket) {
        try {
            Socket clientSocket = serverSocket.accept();
            log.info("Client connected from port: {}", clientSocket.getPort());
            executor.submit(() -> handleRequest(clientSocket));
        } catch (IOException e) {
            log.error("Error accepting client connection", e);
        }
    }

    private void handleRequest(Socket clientSocket) {
        try (
                Socket socket = clientSocket;
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            Object request = inputStream.readObject();
            log.info("Received request of type: {}", request.getClass().getName());
            if (request instanceof SignalUpdateRequest signalUpdateRequest) {
                log.info("Processing SignalUpdateRequest: {}", signalUpdateRequest);
                SignalUpdateResponse response = signalUpdateHandler.update(signalUpdateRequest);
                outputStream.writeObject(response);
            } else {
                log.warn("Received unexpected request type: {}", request.getClass().getName());
                outputStream.writeObject(new SignalUpdateResponse(Response.Result.FAILURE));
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error handling request", e);
        }
    }

}
