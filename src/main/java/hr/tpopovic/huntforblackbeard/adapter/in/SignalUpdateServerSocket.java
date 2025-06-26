package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.message.Response;
import hr.tpopovic.huntforblackbeard.message.SignalUpdateResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignalUpdateServerSocket {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    void start() {
        executor.submit(this::handle);
        System.out.println("SignalUpdateServerSocket started");
    }

    private void handle() {
        try (ServerSocket serverSocket = new ServerSocket(Application.SERVER_PORT)){
            System.out.println("Server started on port: " + Application.SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                try(ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());) {
                    Object read = inputStream.readObject();
                    System.out.println(read.getClass());
                    outputStream.writeObject(new SignalUpdateResponse(Response.Result.SUCCESS));
                } catch (ClassNotFoundException e) {
                    System.out.println("Error reading object from client: " + e.getMessage());
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
}
