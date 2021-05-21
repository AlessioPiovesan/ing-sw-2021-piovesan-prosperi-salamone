package it.polimi.ingsw.server.accessories.cards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CardLeaderTest {

    @Test
    void testToStringTest() {
        for (var card : CardLeader.values()) {
            var name = card.name();
            var ability = card.getAbility();
            var winPnt = card.getWinPnt();
            var req = card.getReq();
            var special = card.getSpecial();
            assertEquals(name + "\t->\t" +
                    "this card gives you the ability of" +
                    ability +
                    "and " + winPnt + "points, to activate this card you need:\n"
                    + Arrays.toString(req), card.toString());
        }
    }

    @Test
    void getCardTest() {
        for (var card : CardDev.values())
            for (var i = 0; i < 16; i++) {
                var code = String.valueOf(i);
                if (i < 10) code = "0" + code;
                if (card.name().contains(code))
                    assertEquals(card, CardDev.getSpecificCard(code));
                else assertNotEquals(card, CardDev.getSpecificCard(code));
            }
    }

    @Test
    void flippedTest() {
        for (var card : CardLeader.values()) {
            assertTrue(card.getFlipped());
            card.flipCard();
            assertFalse(card.getFlipped());
        }

    }
}