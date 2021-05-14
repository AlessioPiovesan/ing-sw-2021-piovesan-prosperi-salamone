package it.polimi.ingsw.server.model_server.board;

import it.polimi.ingsw.server.accessories.cards.CardDev;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void checkAddableCard() {
        Library library = new Library();
        library.addCard(0, CardDev.CARD_DEV1B1);
        library.addCard(1, CardDev.CARD_DEV1B2);
        library.addCard(2, CardDev.CARD_DEV1B3);
        for (CardDev cardDev : CardDev.values())
            for (int i = 0; i < 3; i++) {
                if (library.getCardOnTop(i).getLvl() < cardDev.getLvl())
                    assertTrue(library.checkAddableCard(i, cardDev));
                else assertFalse(library.checkAddableCard(i, cardDev));
            }

    }

    @Test
    void addCardTest() {
        Library library = new Library();
        library.addCard(0, CardDev.CARD_DEV1B1);
        library.addCard(1, CardDev.CARD_DEV1B2);
        library.addCard(2, CardDev.CARD_DEV1B3);
        assertEquals(CardDev.CARD_DEV1B1, library.getCardOnTop(0));
        assertEquals(CardDev.CARD_DEV1B2, library.getCardOnTop(1));
        assertEquals(CardDev.CARD_DEV1B3, library.getCardOnTop(2));
    }

    @Test
    void getCardOnTopTest() {
        Library library = new Library();
        library.addCard(0, CardDev.CARD_DEV1B1);
        library.addCard(1, CardDev.CARD_DEV1B2);
        library.addCard(2, CardDev.CARD_DEV1B3);
        assertEquals(CardDev.CARD_DEV1B1, library.getCardOnTop(0));
        assertEquals(CardDev.CARD_DEV1B2, library.getCardOnTop(1));
        assertEquals(CardDev.CARD_DEV1B3, library.getCardOnTop(2));
    }

    @Test
    void getWinPntCardDevTest() {
        Library library = new Library();
        library.addCard(0, CardDev.CARD_DEV1B1);
        library.addCard(1, CardDev.CARD_DEV1B2);
        library.addCard(2, CardDev.CARD_DEV1B3);
        assertEquals(15, library.getWinPntCardDev());
    }
}