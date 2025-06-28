package hr.tpopovic.huntforblackbeard.application.port.out;

public interface ForChatting {

    SendMessageResult sendMessage(SendMessageCommand command);

    GetMessagesResult getMessages();

}
