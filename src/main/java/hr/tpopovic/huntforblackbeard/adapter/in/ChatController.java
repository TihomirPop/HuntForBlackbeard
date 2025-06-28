package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.Application;
import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.model.ChatMessage;
import hr.tpopovic.huntforblackbeard.application.port.out.ForChatting;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageResult;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ChatController {

    private final ForChatting forChatting = IocContainer.getInstance(ForChatting.class);

    @FXML
    TextField chatField;

    @FXML
    void sendMessage() {
        String content = chatField.getText();
        if (content.isBlank()) {
            AlertManager.showInfo("Message Sending Failed", "Message cannot be blank.");
            return;
        }
        ChatMessage chatMessage = new ChatMessage(
                Application.PLAYER_TYPE,
                content
        );
        SendMessageCommand command = new SendMessageCommand(chatMessage);

        SendMessageResult result = forChatting.sendMessage(command);

        switch (result) {
            case SendMessageResult.Success _ -> sendMessageSuccess();
            case SendMessageResult.Failure failure -> sendMessageFailure(failure);
        }
    }

    private void sendMessageSuccess() {
        chatField.setText("");
    }

    private void sendMessageFailure(SendMessageResult.Failure failure) {
        AlertManager.showInfo("Message Sending Failed", failure.getMessage());
    }

}
