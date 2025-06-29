package hr.tpopovic.huntforblackbeard.adapter.out;

import hr.tpopovic.huntforblackbeard.application.port.out.ForReplaying;
import hr.tpopovic.huntforblackbeard.application.port.out.SaveReplayCommand;
import hr.tpopovic.huntforblackbeard.application.port.out.SaveReplayResult;

public class XmlReplayManager implements ForReplaying {

    @Override
    public SaveReplayResult save(SaveReplayCommand command) {
        System.out.println(command);
        return new SaveReplayResult.Success();
    }

}
