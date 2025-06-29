package hr.tpopovic.huntforblackbeard.jndi;

public class JndiProperties {

    private JndiProperties() {
    }

    public static String getHunterHostname() {
        return PropertyReader.getStringValue(PropertyKey.HUNTER_HOSTNAME);
    }

    public static Integer getHunterServerPort() {
        return PropertyReader.getIntegerValue(PropertyKey.HUNTER_SERVER_PORT);
    }

    public static String getPirateHostname() {
        return PropertyReader.getStringValue(PropertyKey.PIRATE_HOSTNAME);
    }

    public static Integer getPirateServerPort() {
        return PropertyReader.getIntegerValue(PropertyKey.PIRATE_SERVER_PORT);
    }

    public static String getRmiHostname() {
        return PropertyReader.getStringValue(PropertyKey.RMI_HOSTNAME);
    }

    public static Integer getRmiServerPort() {
        return PropertyReader.getIntegerValue(PropertyKey.RMI_SERVER_PORT);
    }

    public static String getSightingImagePath() {
        return PropertyReader.getStringValue(PropertyKey.SIGHTING_IMAGE_PATH);
    }

}
