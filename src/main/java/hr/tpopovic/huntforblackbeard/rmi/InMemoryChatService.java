package hr.tpopovic.huntforblackbeard.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryChatService implements ChatService {

    private final List<Message> messages;

    public InMemoryChatService() {
        messages = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        messages.add(message);
    }

    @Override
    public List<Message> getMessages() throws RemoteException {
        synchronized (messages) {
            return List.copyOf(messages);
        }
    }

}
