package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest{

    @Test
    void setDiscount(){
        var b = new Board(0, null, null, null, null);
        b.setDiscount(CardLeader.CARDLEAD01.getSpecial());
        assertEquals(CardLeader.CARDLEAD01.getSpecial(), b.getDiscount());
    }

    @Test
    void setProductAlt(){
        var b = new Board(0, null, null, null, null);
        b.setProductAlt(CardLeader.CARDLEAD13.getSpecial());
        assertEquals(CardLeader.CARDLEAD13.getSpecial(), b.getProductAlt());
    }

    @Test
    void setMarketAlt(){
        var b = new Board(0, null, null, null, null);
        b.setMarketAlt(CardLeader.CARDLEAD09.getSpecial());
        assertEquals(CardLeader.CARDLEAD09.getSpecial(), b.getMarketAlt());
    }
}