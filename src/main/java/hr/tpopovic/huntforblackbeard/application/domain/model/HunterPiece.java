package hr.tpopovic.huntforblackbeard.application.domain.model;

public abstract sealed class HunterPiece extends Piece permits HunterShip, HunterPerson {

    private boolean hasStartedSearching = false;
    private boolean hasFinishedSearching = false;

    protected HunterPiece(Name name) {
        super(name);
    }

    public void startSearching() {
        hasStartedSearching = true;
    }

    public void finishSearching() {
        hasFinishedSearching = true;
    }

    public boolean hasStartedSearching() {
        return hasStartedSearching;
    }

    public boolean hasFinishedSearching() {
        return hasFinishedSearching;
    }

    public void clearSearching() {
        hasStartedSearching = false;
        hasFinishedSearching = false;
    }

}
