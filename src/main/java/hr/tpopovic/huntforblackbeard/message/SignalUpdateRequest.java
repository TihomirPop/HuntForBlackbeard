package hr.tpopovic.huntforblackbeard.message;

import java.util.Set;

public class SignalUpdateRequest extends Request {

    private final String janeLocation;
    private final String rangerLocation;
    private final String brandLocation;
    private final String adventureLocation;
    private final Set<String> pirateSightings;

    public SignalUpdateRequest(
            String janeLocation,
            String rangerLocation,
            String brandLocation,
            String adventureLocation, Set<String> pirateSightings
    ) {
        this.janeLocation = janeLocation;
        this.rangerLocation = rangerLocation;
        this.brandLocation = brandLocation;
        this.adventureLocation = adventureLocation;
        this.pirateSightings = pirateSightings;
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

    public Set<String> getPirateSightings() {
        return pirateSightings;
    }

}
