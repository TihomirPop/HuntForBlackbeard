package hr.tpopovic.huntforblackbeard.jndi;

public enum PropertyKey {

    HUNTER_HOSTNAME("player.hunter.hostname"),
    HUNTER_SERVER_PORT("player.hunter.server.port"),
    PIRATE_HOSTNAME("player.pirate.hostname"),
    PIRATE_SERVER_PORT("player.pirate.server.port"),
    RMI_HOSTNAME("rmi.hostname"),
    RMI_SERVER_PORT("rmi.port"),
    SIGHTING_IMAGE_PATH("sighting.image.path");

    private final String key;

    PropertyKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
