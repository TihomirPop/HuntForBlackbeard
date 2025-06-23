package hr.tpopovic.huntforblackbeard;

import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static String PLAYER_TYPE;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/hr/tpopovic/huntforblackbeard/adapter/in/game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Hunt for Blackbeard");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide player type as an argument HUNTER or PIRATE.");
            return;
        }
        var playerType = args[0].toUpperCase();
        if (!playerType.equals("HUNTER") && !playerType.equals("PIRATE")) {
            System.out.println("Invalid player type. Please provide HUNTER or PIRATE.");
            return;
        }
        PLAYER_TYPE = playerType;
        Pieces.initialize();
        launch();
    }

}