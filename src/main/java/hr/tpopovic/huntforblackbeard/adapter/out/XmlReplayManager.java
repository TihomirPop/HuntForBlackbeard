package hr.tpopovic.huntforblackbeard.adapter.out;

import hr.tpopovic.huntforblackbeard.AppProperties;
import hr.tpopovic.huntforblackbeard.application.domain.model.*;
import hr.tpopovic.huntforblackbeard.application.port.out.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XmlReplayManager implements ForReplaying {

    public static final String TURNS_TAG_NAME = "turns";
    public static final String TURN_TAG_NAME = "turn";
    public static final String PLAYER_TAG_NAME = "player";
    public static final String MOVES_TAG_NAME = "moves";
    public static final String MOVE_TAG_NAME = "move";
    public static final String PIECE_TAG_NAME = "piece";
    public static final String DESTINATION_TAG_NAME = "destination";

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

        Element turnsElement = xmlReplay.createElement(TURNS_TAG_NAME);
        xmlReplay.appendChild(turnsElement);

        for(ReplayTurn turn : command.replayTurns()) {
            Element turnElement = xmlReplay.createElement(TURN_TAG_NAME);
            Element playerElement = xmlReplay.createElement(PLAYER_TAG_NAME);
            playerElement.setTextContent(turn.playerType().name());
            Element movesElement = xmlReplay.createElement(MOVES_TAG_NAME);
            for(ReplayMove move : turn.moves()) {
                Element moveElement = xmlReplay.createElement(MOVE_TAG_NAME);
                Element pieceElement = xmlReplay.createElement(PIECE_TAG_NAME);
                pieceElement.setTextContent(move.pieceName().getDisplayName());
                Element destinationElement = xmlReplay.createElement(DESTINATION_TAG_NAME);
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

    @Override
    public GetReplayResult get(GetReplayQuery query) {
        File inputFile = query.inputFile();
        Document xmlReplay;

        try {
            xmlReplay = DocumentBuilderFactory.newDefaultInstance()
                    .newDocumentBuilder()
                    .parse(inputFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            return new GetReplayResult.Failure("Failed to read XML file: " + e.getMessage());
        }

        xmlReplay.getDocumentElement().normalize();

        List<ReplayTurn> replayTurns = new ArrayList<>();
        NodeList turnNodes = xmlReplay.getElementsByTagName(TURN_TAG_NAME);

        for (int i = 0; i < turnNodes.getLength(); i++) {
            Element turnElement = (Element) turnNodes.item(i);

            String playerTypeStr = turnElement.getElementsByTagName(PLAYER_TAG_NAME)
                    .item(0)
                    .getTextContent();
            Player.Type playerType = Player.Type.valueOf(playerTypeStr);

            List<ReplayMove> moves = new ArrayList<>();
            NodeList moveNodes = ((Element) turnElement.getElementsByTagName(MOVES_TAG_NAME)
                    .item(0))
                    .getElementsByTagName(MOVE_TAG_NAME);

            for (int j = 0; j < moveNodes.getLength(); j++) {
                Element moveElement = (Element) moveNodes.item(j);

                String pieceNameStr = moveElement.getElementsByTagName(PIECE_TAG_NAME)
                        .item(0)
                        .getTextContent();
                String destinationId = moveElement.getElementsByTagName(DESTINATION_TAG_NAME)
                        .item(0)
                        .getTextContent();

                Piece.Name pieceName = Piece.Name.findByName(pieceNameStr);
                Location.Name destinationName = Location.Name.findById(destinationId);

                moves.add(new ReplayMove(pieceName, destinationName));
            }

            replayTurns.add(new ReplayTurn(playerType, moves));
        }

        return new GetReplayResult.Success(replayTurns);
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

}
