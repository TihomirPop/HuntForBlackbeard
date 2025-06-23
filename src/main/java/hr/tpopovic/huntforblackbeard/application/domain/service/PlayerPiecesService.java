package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.domain.model.Players;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFetchingPlayerPieces;
import hr.tpopovic.huntforblackbeard.application.port.in.PlayerPiecesQuery;
import hr.tpopovic.huntforblackbeard.application.port.in.PlayerPiecesResult;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class PlayerPiecesService implements ForFetchingPlayerPieces {

    @Override
    public PlayerPiecesResult fetch(PlayerPiecesQuery query) {
        requireNonNull(query, "PlayerPiecesQuery must not be null");
        return switch (query.playerType()) {
            case HUNTER -> fetchHunterPieces();
            case PIRATE -> fetchPiratePieces();
        };
    }

    private PlayerPiecesResult fetchHunterPieces() {
        Player player = Players.HUNTER;
        return fetchPlayerPieces(player);
    }

    private PlayerPiecesResult fetchPiratePieces() {
        Player player = Players.PIRATE;
        return fetchPlayerPieces(player);
    }

    private static PlayerPiecesResult fetchPlayerPieces(Player player) {
        Set<Piece.Name> pieceNames = player.getPieces()
                .stream()
                .map(Piece::getName)
                .collect(Collectors.toSet());
        return pieceNames.isEmpty()
                ? new PlayerPiecesResult.Failure("No pieces found for the current player.")
                : new PlayerPiecesResult.Success(pieceNames);
    }

}
