package hr.tpopovic.huntforblackbeard.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ChatServer {

    private static final int RANDOM_PORT_HINT = 0;

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(4242);
        ChatService chatService = new InMemoryChatService();
        ChatService skeleton = (ChatService) UnicastRemoteObject.exportObject(chatService, RANDOM_PORT_HINT);
        registry.rebind(ChatService.REMOTE_OBJECT_NAME, skeleton);
    }

}
