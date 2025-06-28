package hr.tpopovic.huntforblackbeard.application.port.out;

import hr.tpopovic.huntforblackbeard.application.domain.model.ChatMessage;

import static java.util.Objects.requireNonNull;

public record SendMessageCommand(
        ChatMessage chatMessage
) {

    public SendMessageCommand {
        requireNonNull(chatMessage, "Chat message cannot be null");
    }

}
