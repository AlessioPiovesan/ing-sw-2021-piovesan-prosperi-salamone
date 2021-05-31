package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.cards.CardDev;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest{

    @Test
    void checkAddableCard(){
        var library = new Library();
        library.addCard(0, CardDev.CARDDEV_1C1);
        library.addCard(1, CardDev.CARDDEV_1C2);
        library.addCard(2, CardDev.CARDDEV_1C3);
        for(CardDev cardDev : CardDev.values())
            for(int i = 0; i < 3; i++){
                if(library.getCardOnTop(i).getLvl() < cardDev.getLvl())
                    assertTrue(library.checkAddableCard(i, cardDev));
                else assertFalse(library.checkAddableCard(i, cardDev));
            }

    }

    @Test
    void addCardTest(){
        var library = new Library();
        library.addCard(0, CardDev.CARDDEV_1C1);
        library.addCard(1, CardDev.CARDDEV_1C2);
        library.addCard(2, CardDev.CARDDEV_1C3);
        assertEquals(CardDev.CARDDEV_1C1, library.getCardOnTop(0));
        assertEquals(CardDev.CARDDEV_1C2, library.getCardOnTop(1));
        assertEquals(CardDev.CARDDEV_1C3, library.getCardOnTop(2));
    }

    @Test
    void getCardOnTopTest(){
        var library = new Library();
        library.addCard(0, CardDev.CARDDEV_1C1);
        library.addCard(1, CardDev.CARDDEV_1C2);
        library.addCard(2, CardDev.CARDDEV_1C3);
        assertEquals(CardDev.CARDDEV_1C1, library.getCardOnTop(0));
        assertEquals(CardDev.CARDDEV_1C2, library.getCardOnTop(1));
        assertEquals(CardDev.CARDDEV_1C3, library.getCardOnTop(2));
    }

    @Test
    void getWinPntCardDevTest(){
        var library = new Library();
        library.addCard(0, CardDev.CARDDEV_1C1);
        library.addCard(1, CardDev.CARDDEV_1C2);
        library.addCard(2, CardDev.CARDDEV_1C3);
        assertEquals(15, library.getWinPntCardDev());
    }
}