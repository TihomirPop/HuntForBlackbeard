package hr.tpopovic.huntforblackbeard.application.domain.model;

import static java.util.Objects.requireNonNull;

public record ChatMessage(
        Player.Type author,
        String content
) {

    public ChatMessage {
        requireNonNull(author, "Author cannot be null");
        requireNonNull(content, "Content cannot be null");
    }

}
