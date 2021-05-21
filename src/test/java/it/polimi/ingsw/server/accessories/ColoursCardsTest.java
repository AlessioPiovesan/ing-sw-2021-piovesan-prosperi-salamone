package it.polimi.ingsw.server.accessories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ColoursCardsTest {

    @Test
    void fromStringTest() {
        for (ColoursCards color : ColoursCards.values())
            assertEquals(color, ColoursCards.fromString(color.name()));
        assertNull(ColoursCards.fromString("ciao"));
    }
}