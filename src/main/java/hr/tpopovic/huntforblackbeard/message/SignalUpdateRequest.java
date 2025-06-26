package hr.tpopovic.huntforblackbeard.message;

public class SignalUpdateRequest extends Request {

    private final String janeLocation;
    private final String rangerLocation;
    private final String brandLocation;
    private final String adventureLocation;

    public SignalUpdateRequest(String janeLocation, String rangerLocation, String brandLocation, String adventureLocation) {
        this.janeLocation = janeLocation;
        this.rangerLocation = rangerLocation;
        this.brandLocation = brandLocation;
        this.adventureLocation = adventureLocation;
    }

    public String getJaneLocation() {
        return janeLocation;
    }

    public String getRangerLocation() {
        return rangerLocation;
    }

    public String getBrandLocation() {
        return brandLocation;
    }

    public String getAdventureLocation() {
        return adventureLocation;
    }

}
