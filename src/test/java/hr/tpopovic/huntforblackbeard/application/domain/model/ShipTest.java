package hr.tpopovic.huntforblackbeard.application.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShipTest {

    @Test
    void should_update_location_when_moving_piece() {
        // given
        Pieces.initialize();
        Piece givenShip = Pieces.HUNTER_SHIP_JANE;
        Location oldLocation = givenShip.location;
        Location givenLocation = Locations.CAPE_HENRY;

        // when
        givenShip.move(givenLocation);

        // then
        assertThat(oldLocation.getPieces())
                .isNotNull()
                .isNotEmpty()
                .doesNotContain(givenShip);
        assertThat(givenShip.location)
                .isNotNull()
                .isEqualTo(givenLocation);
        assertThat(givenLocation.getPieces())
                .isNotNull()
                .containsExactly(givenShip);
    }

}