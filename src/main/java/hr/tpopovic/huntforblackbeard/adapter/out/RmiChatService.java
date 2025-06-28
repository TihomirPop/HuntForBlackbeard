package hr.tpopovic.huntforblackbeard.adapter.out;

import hr.tpopovic.huntforblackbeard.application.domain.model.ChatMessage;
import hr.tpopovic.huntforblackbeard.application.port.out.ForChatting;
import hr.tpopovic.huntforblackbeard.application.port.out.GetMessagesResult;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageResult;
import hr.tpopovic.huntforblackbeard.rmi.ChatService;
import hr.tpopovic.huntforblackbeard.rmi.Message;

import java.rmi.RemoteException;

import static java.util.Objects.requireNonNull;

public class RmiChatService implements ForChatting {

    private final ChatService chatService;

    public RmiChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public SendMessageResult sendMessage(SendMessageCommand command) {
        requireNonNull(command, "SendMessageCommand cannot be null");
        ChatMessage chatMessage = command.chatMessage();
        Message message = new Message(
                chatMessage.author().name(),
                chatMessage.content()
        );

        try {
            chatService.sendMessage(message);
            return new SendMessageResult.Success();
        } catch (RemoteException e) {
            return new SendMessageResult.Failure("Failed to send message: " + e.getMessage());
        }
    }

    @Override
    public GetMessagesResult getMessages() {
        return null;
    }

}
