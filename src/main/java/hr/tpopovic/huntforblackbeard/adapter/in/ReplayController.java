package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.domain.model.ReplayMove;
import hr.tpopovic.huntforblackbeard.application.domain.model.ReplayTurn;
import hr.tpopovic.huntforblackbeard.application.port.out.ForReplaying;
import hr.tpopovic.huntforblackbeard.application.port.out.GetReplayQuery;
import hr.tpopovic.huntforblackbeard.application.port.out.GetReplayResult;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.List;


public class ReplayController {

    private final ForReplaying forReplaying = IocContainer.getInstance(ForReplaying.class);

    @FXML
    AnchorPane gamePane;
    @FXML
    ImageView jane;
    @FXML
    ImageView ranger;
    @FXML
    ImageView brand;
    @FXML
    ImageView adventure;
    @FXML
    Text currentPlayerText;

    private FXLocations locations;
    private FXPieces pieces;

    @FXML
    void initialize() {
        FXLocations.Builder locationsBuilder = FXLocations.builder();
        List<Node> gamePaneChildren = List.copyOf(gamePane.getChildren());
        for (Node node : gamePaneChildren) {
            if (node instanceof Button button) {
                FXLocationInitializer.initialize(button, gamePane, locationsBuilder);
            }
        }

        locations = locationsBuilder.build();
        pieces = FXPieces.builder()
                .setJane(jane)
                .setRanger(ranger)
                .setBrand(brand)
                .setAdventure(adventure)
                .build();

        Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select file to replay from");
            fileChooser.setInitialDirectory(new File("./replays"));
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Replay files", "*.xml")
            );
            File file = fileChooser.showSaveDialog(gamePane.getScene().getWindow());
            GetReplayQuery query = new GetReplayQuery(file);
            GetReplayResult result = forReplaying.get(query);
            switch (result) {
                case GetReplayResult.Success success -> success(success);
                case GetReplayResult.Failure failure -> failure(failure);
            }
        });
    }

    private void success(GetReplayResult.Success success) {
        Timeline timeline = new Timeline();
        ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();

        double time = 0;

        for (ReplayTurn turn : success.getReplayTurns()) {
            KeyFrame turnKeyFrame = new KeyFrame(
                    Duration.seconds(time),
                    _ -> onNewTurn(turn)
            );
            keyFrames.add(turnKeyFrame);
            time += 3;

            for (ReplayMove move : turn.moves()) {
                KeyFrame moveKeyFrame = new KeyFrame(
                        Duration.seconds(time),
                        _ -> onNewMove(move)
                );
                keyFrames.add(moveKeyFrame);
                time += 2;
            }
        }

        KeyFrame turnKeyFrame = new KeyFrame(
                Duration.seconds(time),
                _ -> onFinishedReplay(success.getReplayTurns())
        );
        keyFrames.add(turnKeyFrame);

        timeline.play();
    }

    private void onNewTurn(ReplayTurn turn) {
        Player.Type type = turn.playerType();
        currentPlayerText.setText(type.name());
    }

    private void onNewMove(ReplayMove move) {
        FXPiece piece = pieces.findByName(move.pieceName());
        FXLocation location = locations.findById(move.destinationName().id);
        piece.changeLocation(location);
    }

    private void onFinishedReplay(List<ReplayTurn> replayTurns) {
        Player.Type type = replayTurns.getLast().playerType();
        AlertManager.showInfo("Replay finished", "%s won the game!".formatted(type.name()));
    }


    private void failure(GetReplayResult.Failure failure) {
        AlertManager.showInfo("Replay error", failure.getMessage());
    }

}
