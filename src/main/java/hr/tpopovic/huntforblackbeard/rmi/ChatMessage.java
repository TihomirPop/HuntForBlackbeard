package hr.tpopovic.huntforblackbeard.rmi;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public record ChatMessage(
        String author,
        String content
) implements Serializable {

    public ChatMessage {
        requireNonNull(author, "Author cannot be null");
        requireNonNull(content, "Content cannot be null");
    }
}
