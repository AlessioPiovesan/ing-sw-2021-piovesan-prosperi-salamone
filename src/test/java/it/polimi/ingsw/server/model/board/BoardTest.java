package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest{

    @Test
    void setDiscountTest(){
        var b = new Board(0, null, null, null, null);
        b.setDiscount(CardLeader.CARDLEAD01.getSpecial());
        assertEquals(CardLeader.CARDLEAD01.getSpecial(), b.getDiscount(0));
        b.setDiscount(CardLeader.CARDLEAD02.getSpecial());
        assertEquals(CardLeader.CARDLEAD02.getSpecial(), b.getDiscount(1));
    }

    @Test
    void setProductAltTest(){
        var b = new Board(0, null, null, null, null);
        b.setProductAlt(CardLeader.CARDLEAD13.getSpecial());
        assertEquals(CardLeader.CARDLEAD13.getSpecial(), b.getProductAlt());
    }

    @Test
    void setMarketAltTest(){
        var b = new Board(0, null, null, null, null);
        b.setMarketAlt(CardLeader.CARDLEAD09.getSpecial());
        assertEquals(CardLeader.CARDLEAD09.getSpecial(), b.getMarketAlt());
    }

    @Test
    void countWinPntTest(){
        var b = new Board(0, null, null, new FaithTrack(), new Library());
        b.setMyLeaderCards(new CardLeader[]{CardLeader.CARDLEAD01, CardLeader.CARDLEAD02});
        assertEquals(0, b.countWinPnt());
    }

    @Test
    void buyCardDevTest(){
        var lib = new Library();
        var b = new Board(0, null, null, new FaithTrack(), lib);
        b.buyCardDev(CardDev.CARDDEV_1C1, 0);
        assertEquals(CardDev.CARDDEV_1C1, lib.getCardOnTop(0));
    }

    @Test
    void checkIfEndGameTest1(){
        var b = new Board(0, null, null, new FaithTrack(), new Library());
        b.buyCardDev(CardDev.CARDDEV_1C1, 0);
        b.buyCardDev(CardDev.CARDDEV_2C1, 1);
        b.buyCardDev(CardDev.CARDDEV_3C1, 2);
        b.buyCardDev(CardDev.CARDDEV_1C2, 0);
        b.buyCardDev(CardDev.CARDDEV_2C2, 1);
        b.buyCardDev(CardDev.CARDDEV_3C2, 2);
        b.buyCardDev(CardDev.CARDDEV_3C3, 2);
        b.checkIfEndGame();
        assertTrue(b.getEndGame());
    }

    @Test
    void checkIfEndGameTest(){
        var b = new Board(0, null, null, new FaithTrack(), null);
        b.moveFT(25);
        b.checkIfEndGame();
        assertTrue(b.getEndGame());
    }

    @Test
    void checkIfEndGameTest3(){
        var b = new Board(0, null, null, new FaithTrack(), null);
        b.moveFT(24);
        b.checkIfEndGame();
        assertTrue(b.getEndGame());
    }

    @Test
    void addLeaderSpaceTest(){
        var b = new Board(0, null, null, null, null);
        b.addLeaderSpace(Colours.BLUE);
        assertNotNull(b.getLeaderSpace().get(Colours.BLUE));
    }

    @Test
    void rearrangeWarehouseTest(){
        var w = new Warehouse();
        var b = new Board(0, w, new StrongBox(), null, null);
        b.storeInWarehouse(1, new Pick[]{new Pick(Colours.BLUE, 1)});
        b.rearrangeWarehouse(1, 2);
        assertEquals(0, w.getWareContent().get(1).qnt());
    }

    @Test
    void takeFromWarehouseTest(){
        var w = new Warehouse();
        var b = new Board(0, w, new StrongBox(), null, null);
        b.storeInWarehouse(1, new Pick[]{new Pick(Colours.BLUE, 1)});
        b.takeFromWarehouse(Colours.BLUE);
        assertEquals(0, w.getWareContent().get(1).qnt());
    }

    @Test
    void storeInWarehouseTest(){
        var w = new Warehouse();
        var b = new Board(0, w, new StrongBox(), null, null);
        b.storeInWarehouse(1, new Pick[]{new Pick(Colours.BLUE, 1)});
        assertEquals(1, w.getWareContent().get(1).qnt());
    }

    @Test
    void updateResAllTest(){
        var b = new Board(0, new Warehouse(), new StrongBox(), null, null);
        b.storeInWarehouse(1, new Pick[]{new Pick(Colours.BLUE, 1)});
        b.storeInStrongBox(new Pick[]{new Pick(Colours.BLUE, 1)});
        b.updateResAll();
        assertEquals(2, b.getResAll().get(Colours.BLUE));
        assertEquals(0, b.getResAll().get(Colours.YELLOW));
        assertEquals(0, b.getResAll().get(Colours.GREY));
        assertEquals(0, b.getResAll().get(Colours.PURPLE));
    }

    @Test
    void takeFromStrongBoxTest(){
        var sB = new StrongBox();
        var b = new Board(0, new Warehouse(), sB, null, null);
        b.storeInStrongBox(new Pick[]{new Pick(Colours.BLUE, 1)});
        b.takeFromStrongBox(Colours.BLUE);
        assertEquals(0, sB.getStrongContent().get(Colours.BLUE));
    }

    @Test
    void storeInStrongBoxTest(){
        var sB = new StrongBox();
        var b = new Board(0, new Warehouse(), sB, null, null);
        b.storeInStrongBox(new Pick[]{new Pick(Colours.BLUE, 1)});
        assertEquals(1, sB.getStrongContent().get(Colours.BLUE));
    }

    @Test
    void moveFTTest(){
        var b = new Board(0, null, null, new FaithTrack(), null);
        b.moveFT(1);
        assertEquals(1, b.getPositionFT());
    }

    @Test
    void produceTest(){
        var sB = new StrongBox();
        var card = CardDev.CARDDEV_1C1;
        var resTo = card.getResTo();
        var b = new Board(0, new Warehouse(), sB, new FaithTrack(), null);
        b.produce(card);
        for(var pick : resTo)
            if(pick.resource() == Colours.RED) assertEquals(pick.qnt(), b.getPositionFT());
            else assertEquals(pick.qnt(), sB.getStrongContent().get(pick.resource()));
    }
}