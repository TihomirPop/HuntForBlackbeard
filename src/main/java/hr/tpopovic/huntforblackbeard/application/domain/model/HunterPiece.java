package hr.tpopovic.huntforblackbeard.application.domain.model;

public abstract sealed class HunterPiece extends Piece permits HunterShip, HunterPerson {

    private boolean hasStartedSearching = false;

    protected HunterPiece(Name name) {
        super(name);
    }

    public void startSearching() {
        hasStartedSearching = true;
    }

    public boolean hasStartedSearching() {
        return hasStartedSearching;
    }

}
