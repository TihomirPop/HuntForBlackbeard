package hr.tpopovic.huntforblackbeard.rmi;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public record Message(
        String author,
        String content
) implements Serializable {

    public Message {
        requireNonNull(author, "Author cannot be null");
        requireNonNull(content, "Content cannot be null");
    }

}
