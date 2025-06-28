package hr.tpopovic.huntforblackbeard.adapter.out;

import hr.tpopovic.huntforblackbeard.application.domain.model.ChatMessage;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.port.out.ForChatting;
import hr.tpopovic.huntforblackbeard.application.port.out.GetMessagesResult;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageResult;
import hr.tpopovic.huntforblackbeard.rmi.ChatService;
import hr.tpopovic.huntforblackbeard.rmi.Message;

import java.rmi.RemoteException;
import java.util.List;

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
        try {
            List<Message> messages = chatService.getMessages();
            List<ChatMessage> chatMessages = messages.stream()
                    .map(message -> new ChatMessage(
                            Player.Type.valueOf(message.author()),
                            message.content()
                    ))
                    .toList();

            return new GetMessagesResult.Success(chatMessages);
        } catch (RemoteException e) {
            return new GetMessagesResult.Failure("Failed to retrieve messages: " + e.getMessage());
        }
    }

}
