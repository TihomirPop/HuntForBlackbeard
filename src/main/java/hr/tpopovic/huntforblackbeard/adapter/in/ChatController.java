package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.model.ChatMessage;
import hr.tpopovic.huntforblackbeard.application.port.out.ForChatting;
import hr.tpopovic.huntforblackbeard.application.port.out.GetMessagesResult;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SendMessageResult;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.stream.Collectors;


public class ChatController {

    private final ForChatting forChatting = IocContainer.getInstance(ForChatting.class);

    @FXML
    TextField chatField;
    @FXML
    TextArea chatArea;

    @FXML
    void initialize() {
        KeyFrame frame = new KeyFrame(
                Duration.seconds(1),
                _ -> getChatMessages()
        );
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    void sendMessage() {
        String content = chatField.getText();
        if (content.isBlank()) {
            AlertManager.showInfo("Message Sending Failed", "Message cannot be blank.");
            return;
        }
        ChatMessage chatMessage = new ChatMessage(
                AppProperties.getPlayerType(),
                content
        );
        SendMessageCommand command = new SendMessageCommand(chatMessage);

        SendMessageResult result = forChatting.sendMessage(command);

        switch (result) {
            case SendMessageResult.Success _ -> sendMessageSuccess();
            case SendMessageResult.Failure failure -> sendMessageFailure(failure);
        }
    }

    private void getChatMessages() {
        GetMessagesResult result = forChatting.getMessages();
        switch (result) {
            case GetMessagesResult.Success success -> getMessagesSuccess(success);
            case GetMessagesResult.Failure failure -> getMessagesFailure(failure);
        }
    }

    private void sendMessageSuccess() {
        chatField.setText("");
    }

    private void sendMessageFailure(SendMessageResult.Failure failure) {
        AlertManager.showInfo("Message Sending Failed", failure.getMessage());
    }

    private void getMessagesSuccess(GetMessagesResult.Success success) {
        String messages = success.getMessages()
                .stream()
                .map(message -> "%s: %s".formatted(message.author().name(), message.content()))
                .collect(Collectors.joining("\n"));

        chatArea.setText(messages);
    }

    private void getMessagesFailure(GetMessagesResult.Failure failure) {
        AlertManager.showInfo("Chat Messages Retrieval Failed", failure.getMessage());
    }

}
