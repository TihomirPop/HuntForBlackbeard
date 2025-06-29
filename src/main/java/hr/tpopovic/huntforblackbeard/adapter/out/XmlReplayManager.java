package hr.tpopovic.huntforblackbeard.adapter.out;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.application.domain.model.ReplayMove;
import hr.tpopovic.huntforblackbeard.application.domain.model.ReplayTurn;
import hr.tpopovic.huntforblackbeard.application.port.out.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XmlReplayManager implements ForReplaying {

    @Override
    public SaveReplayResult save(SaveReplayCommand command) {
        Document xmlReplay;
        try {
            xmlReplay = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
        } catch (ParserConfigurationException e) {
            return new SaveReplayResult.Failure("Failed to create XML document: " + e.getMessage());
        }

        Element turnsElement = xmlReplay.createElement("turns");
        xmlReplay.appendChild(turnsElement);

        for(ReplayTurn turn : command.replayTurns()) {
            Element turnElement = xmlReplay.createElement("turn");
            Element playerElement = xmlReplay.createElement("player");
            playerElement.setTextContent(turn.playerType().name());
            Element movesElement = xmlReplay.createElement("moves");
            for(ReplayMove move : turn.moves()) {
                Element moveElement = xmlReplay.createElement("move");
                Element pieceElement = xmlReplay.createElement("piece");
                pieceElement.setTextContent(move.pieceName().getDisplayName());
                Element destinationElement = xmlReplay.createElement("destination");
                destinationElement.setTextContent(move.destinationName().id);
                moveElement.appendChild(pieceElement);
                moveElement.appendChild(destinationElement);
                movesElement.appendChild(moveElement);
            }
            turnElement.appendChild(playerElement);
            turnElement.appendChild(movesElement);
            turnsElement.appendChild(turnElement);
        }

        DOMSource domSource = new DOMSource(xmlReplay);
        StreamResult streamResult = new StreamResult(getFile());
        try {
            TransformerFactory.newDefaultInstance()
                    .newTransformer()
                    .transform(domSource, streamResult);
        } catch (TransformerException e) {
            return new SaveReplayResult.Failure("Failed to write XML file: " + e.getMessage());
        }

        return new SaveReplayResult.Success();
    }

    private File getFile() {
        String playerType = AppProperties.getPlayerType()
                .name()
                .toLowerCase();
        String dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = "./replays/replay_%s_%s.xml".formatted(playerType, dateTime);
        return new File(fileName);
    }

    @Override
    public GetReplayResult get(GetReplayQuery query) {
        return null;
    }

}
