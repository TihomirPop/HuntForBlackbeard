package hr.tpopovic.huntforblackbeard.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryChatService implements ChatService {

    private final List<ChatMessage> chatMessages;

    public InMemoryChatService() {
        chatMessages = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void sendMessage(ChatMessage message) throws RemoteException {
        chatMessages.add(message);
    }

    @Override
    public List<ChatMessage> getMessages() throws RemoteException {
        synchronized (chatMessages) {
            return List.copyOf(chatMessages);
        }
    }

}
