package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.port.out.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

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
        System.out.println(success);
    }

    private void failure(GetReplayResult.Failure failure) {
        AlertManager.showInfo("Replay error", failure.getMessage());
    }

}
