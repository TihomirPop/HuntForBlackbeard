package hr.tpopovic.huntforblackbeard.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {

    String REMOTE_OBJECT_NAME = "hr.tpopovic.chat.ChatService";

    void sendMessage(ChatMessage message) throws RemoteException;

    List<ChatMessage> getMessages() throws RemoteException;

}