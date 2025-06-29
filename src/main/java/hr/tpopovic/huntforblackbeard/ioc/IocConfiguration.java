package hr.tpopovic.huntforblackbeard.ioc;

import hr.tpopovic.huntforblackbeard.adapter.in.DocumentationGenerator;
import hr.tpopovic.huntforblackbeard.adapter.out.HtmlDocumentationGenerator;
import hr.tpopovic.huntforblackbeard.adapter.out.RmiChatService;
import hr.tpopovic.huntforblackbeard.adapter.out.SignalUpdateClientSocket;
import hr.tpopovic.huntforblackbeard.adapter.out.XmlReplayManager;
import hr.tpopovic.huntforblackbeard.application.domain.service.*;
import hr.tpopovic.huntforblackbeard.jndi.JndiProperties;
import hr.tpopovic.huntforblackbeard.rmi.ChatService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class IocConfiguration {

    private IocConfiguration() {
    }

    public static void init() {
        IocContainer.addClassToManage(SignalUpdateClientSocket.class);
        IocContainer.addClassToManage(GameStateUpdateService.class);
        IocContainer.addClassToManage(MovementService.class);
        IocContainer.addClassToManage(PlayerPiecesService.class);
        IocContainer.addClassToManage(TurnFinishingService.class);
        IocContainer.addClassToManage(PirateDiscoveryService.class);
        IocContainer.addClassToManage(RmiChatService.class);
        IocContainer.addClassToManage(HtmlDocumentationGenerator.class);
        IocContainer.addClassToManage(DocumentationGenerator.class);
        IocContainer.addClassToManage(XmlReplayManager.class);
        addRmiChatServiceInstanceToManage();
    }

    private static void addRmiChatServiceInstanceToManage() {
        try {
            Registry registry = LocateRegistry.getRegistry(
                    JndiProperties.getRmiHostname(),
                    JndiProperties.getRmiServerPort()
            );
            ChatService chatService = (ChatService) registry.lookup(ChatService.REMOTE_OBJECT_NAME);
            IocContainer.addInstanceToManage(ChatService.class, chatService);
        } catch (RemoteException | NotBoundException e) {
            throw new IocException("Failed to initialize RMI chat service", e);
        }
    }

}
