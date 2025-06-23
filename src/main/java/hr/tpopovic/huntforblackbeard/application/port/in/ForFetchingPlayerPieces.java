package hr.tpopovic.huntforblackbeard.application.port.in;

public interface ForFetchingPlayerPieces {

    PlayerPiecesResult fetch(PlayerPiecesQuery query);

}
