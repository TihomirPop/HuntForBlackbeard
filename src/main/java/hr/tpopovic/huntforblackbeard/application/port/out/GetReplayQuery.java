package hr.tpopovic.huntforblackbeard.application.port.out;

import java.io.File;

import static java.util.Objects.requireNonNull;

public record GetReplayQuery(
        File inputFile
) {

    public GetReplayQuery {
        requireNonNull(inputFile, "Input file must not be null");
    }

}
