package hr.tpopovic.huntforblackbeard;

import hr.tpopovic.huntforblackbeard.adapter.in.DocumentationGenerator;
import hr.tpopovic.huntforblackbeard.adapter.out.HtmlDocumentationGenerator;
import hr.tpopovic.huntforblackbeard.adapter.out.RmiChatService;
import hr.tpopovic.huntforblackbeard.adapter.out.SignalUpdateClientSocket;
import hr.tpopovic.huntforblackbeard.application.domain.model.Pieces;
import hr.tpopovic.huntforblackbeard.application.domain.service.*;
import hr.tpopovic.huntforblackbeard.jndi.JndiProperties;
import hr.tpopovic.huntforblackbeard.rmi.ChatService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
        IocContainer.addClassToManage(SignalUpdateClientSocket.class);
        IocContainer.addClassToManage(GameStateUpdateService.class);
        IocContainer.addClassToManage(MovementService.class);
        IocContainer.addClassToManage(PlayerPiecesService.class);
        IocContainer.addClassToManage(TurnFinishingService.class);
        IocContainer.addClassToManage(PirateDiscoveryService.class);
        IocContainer.addClassToManage(RmiChatService.class);
        IocContainer.addClassToManage(HtmlDocumentationGenerator.class);
        IocContainer.addClassToManage(DocumentationGenerator.class);

        try {
            Registry registry = LocateRegistry.getRegistry(
                    JndiProperties.getRmiHostname(),
                    JndiProperties.getRmiServerPort()
            );
            ChatService chatService = (ChatService) registry.lookup(ChatService.REMOTE_OBJECT_NAME);
            IocContainer.addInstanceToManage(ChatService.class, chatService);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        launch();
    }

}