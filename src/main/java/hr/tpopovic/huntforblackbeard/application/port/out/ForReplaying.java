package hr.tpopovic.huntforblackbeard.application.port.out;

public interface ForReplaying {

    SaveReplayResult save(SaveReplayCommand command);

    GetReplayResult get(GetReplayQuery query);

}
