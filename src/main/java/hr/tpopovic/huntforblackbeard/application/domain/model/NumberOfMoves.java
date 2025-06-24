package hr.tpopovic.huntforblackbeard.application.domain.model;

import static java.util.Objects.requireNonNull;

public record NumberOfMoves(
        Integer number  //todo: either fix or delete
) {

    public NumberOfMoves {
        requireNonNull(number, "Number of moves cannot be null");
        if(number < 0) {
            throw new IllegalArgumentException("Number of moves cannot be negative");
        }
    }
}
