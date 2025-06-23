package hr.tpopovic.huntforblackbeard.application.domain.service;

import hr.tpopovic.huntforblackbeard.application.domain.model.GameState;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFetchingPlayerPieces;
import hr.tpopovic.huntforblackbeard.application.port.in.PlayerPiecesResult;

import java.util.Set;

public class PlayerPiecesService implements ForFetchingPlayerPieces {

    @Override
    public PlayerPiecesResult fetch() {
        Set<Piece> playerPieces = GameState.getCurrentPlayerPieces();
        return playerPieces.isEmpty()
                ? new PlayerPiecesResult.Failure("No pieces found for the current player.")
                : new PlayerPiecesResult.Success(playerPieces);
    }

}
