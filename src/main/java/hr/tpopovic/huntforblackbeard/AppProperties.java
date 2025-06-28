package hr.tpopovic.huntforblackbeard;

import hr.tpopovic.huntforblackbeard.application.domain.model.Player;
import hr.tpopovic.huntforblackbeard.jndi.JndiProperties;

public class AppProperties {

    private static Player.Type playerType;
    private static String serverHost;
    private static Integer serverPort;
    private static String clientHost;
    private static Integer clientPort;

    private AppProperties() {
    }

    public static Player.Type getPlayerType() {
        return playerType;
    }

    public static String getServerHost() {
        return serverHost;
    }

    public static Integer getServerPort() {
        return serverPort;
    }

    public static String getClientHost() {
        return clientHost;
    }

    public static Integer getClientPort() {
        return clientPort;
    }

    public static void init(String playerType) {
        switch (playerType) {
            case "HUNTER" -> hunter();
            case "PIRATE" -> pirate();
            default -> throw new IllegalArgumentException("Invalid player type: %s. Expected HUNTER or PIRATE.".formatted(playerType));
        }
    }

    private static void hunter() {
        playerType = Player.Type.HUNTER;
        serverHost = JndiProperties.getHunterHostname();
        serverPort = JndiProperties.getHunterServerPort();
        clientHost = JndiProperties.getPirateHostname();
        clientPort = JndiProperties.getPirateServerPort();
    }

    private static void pirate() {
        playerType = Player.Type.PIRATE;
        serverHost = JndiProperties.getPirateHostname();
        serverPort = JndiProperties.getPirateServerPort();
        clientHost = JndiProperties.getHunterHostname();
        clientPort = JndiProperties.getHunterServerPort();
    }

}
