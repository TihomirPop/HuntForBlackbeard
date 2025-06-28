package hr.tpopovic.huntforblackbeard.application.port.out;

import hr.tpopovic.huntforblackbeard.application.domain.model.ChatMessage;

import java.util.List;

public abstract sealed class GetMessagesResult {

    private GetMessagesResult() {
    }

    public static final class Success extends GetMessagesResult {
        private final List<ChatMessage> messages;

        public Success(List<ChatMessage> messages) {
            this.messages = messages;
        }

        public List<ChatMessage> getMessages() {
            return messages;
        }

    }

    public static final class Failure extends GetMessagesResult {

        private final String message;

        public Failure(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}
