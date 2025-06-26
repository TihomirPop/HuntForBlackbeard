package hr.tpopovic.huntforblackbeard.application.domain.model;

public abstract sealed class Ship extends Piece permits HunterShip, PirateShip {

    protected Ship(Name name) {
        super(name);
    }

}