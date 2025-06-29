package hr.tpopovic.huntforblackbeard;

import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.ioc.IocConfiguration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BlackbeardApplication extends javafx.application.Application {

    private static final Logger log = LoggerFactory.getLogger(BlackbeardApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BlackbeardApplication.class.getResource("/hr/tpopovic/huntforblackbeard/adapter/in/game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Hunt for Blackbeard");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            log.error("Please provide player type as an argument HUNTER or PIRATE.");
            return;
        }
        var playerType = args[0].toUpperCase();
        AppProperties.init(playerType);
        Pieces.initialize();
        IocConfiguration.init();

        launch();
    }

}