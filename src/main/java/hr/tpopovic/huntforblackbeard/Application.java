package hr.tpopovic.huntforblackbeard;

import hr.tpopovic.huntforblackbeard.adapter.out.SignalUpdateClientSocket;
import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.application.domain.service.GameStateUpdateService;
import hr.tpopovic.huntforblackbeard.application.domain.service.MovementService;
import hr.tpopovic.huntforblackbeard.application.domain.service.PlayerPiecesService;
import hr.tpopovic.huntforblackbeard.application.domain.service.TurnFinishingService;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFetchingPlayerPieces;
import hr.tpopovic.huntforblackbeard.application.port.in.ForFinishingTurn;
import hr.tpopovic.huntforblackbeard.application.port.in.ForMovingPieces;
import hr.tpopovic.huntforblackbeard.application.port.in.ForUpdatingGameState;
import hr.tpopovic.huntforblackbeard.application.port.out.ForSignalingUpdate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static Player.Type PLAYER_TYPE;
    public static Integer SERVER_PORT;
    public static Integer CLIENT_PORT;

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
        switch (playerType) {
            case "HUNTER" -> hunter();
            case "PIRATE" -> pirate();
            default -> throw new IllegalArgumentException("Invalid player type: %s. Expected HUNTER or PIRATE.".formatted(playerType));
        };
        Pieces.initialize();
        IocContainer.addClassToManage(SignalUpdateClientSocket.class);
        IocContainer.addClassToManage(GameStateUpdateService.class);
        IocContainer.addClassToManage(MovementService.class);
        IocContainer.addClassToManage(PlayerPiecesService.class);
        IocContainer.addClassToManage(TurnFinishingService.class);
        launch();
    }

    private static void hunter() {
        PLAYER_TYPE = Player.Type.HUNTER;
        SERVER_PORT = 8042;
        CLIENT_PORT = 8043;
    }

    private static void pirate() {
        PLAYER_TYPE = Player.Type.PIRATE;
        SERVER_PORT = 8043;
        CLIENT_PORT = 8042;
    }

}