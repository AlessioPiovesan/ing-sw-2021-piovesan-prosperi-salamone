package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest{

    @Test
    void checkAddTest(){
        Warehouse w = new Warehouse();
        assertTrue(w.checkAdd(new Pick(Colours.BLUE, 1)));
        w.moveResources(1, new Pick(Colours.BLUE, 1));
        assertTrue(w.checkAdd(new Pick(Colours.YELLOW, 3)));
        assertFalse(w.checkAdd(new Pick(Colours.BLUE, 4)));
        assertFalse(w.checkAdd(new Pick(Colours.BLUE, 2)));
        assertFalse(w.checkAdd(1, new Pick(Colours.BLUE, 2)));
    }

    @Test
    void isThereThatResTest(){
        Warehouse w = new Warehouse();
        assertEquals(0, w.isThereThatRes(Colours.WHITE));
        assertEquals(-1, w.isThereThatRes(Colours.BLUE));
    }

    @Test
    void lvlExchangeTest(){
        Warehouse w = new Warehouse();
        w.moveResources(0, new Pick(Colours.BLUE, 1));
        w.moveResources(2, new Pick(Colours.YELLOW, 1));
        w.lvlExchange(0, 2);
        assertEquals(Colours.YELLOW, w.getWareContent().get(0).getResource());
        assertEquals(Colours.BLUE, w.getWareContent().get(2).getResource());
    }

    @Test
    void checkStatusTest(){
        Warehouse w = new Warehouse();
        List<Pick> list = w.checkStatus();
        assertEquals(3, list.size());
        IntStream.range(0, 3).mapToObj(list::get).forEachOrdered(pick -> {
            assertEquals(Colours.WHITE, pick.getResource());
            assertEquals(0, pick.getQnt());
        });
    }

    @Test
    void checkLvlStatusTest(){
        Warehouse w = new Warehouse();
        IntStream.range(0, 3).mapToObj(w::checkLvlStatus).forEachOrdered(pick -> {
            assertEquals(Colours.WHITE, pick.getResource());
            assertEquals(0, pick.getQnt());
        });
    }

    @Test
    void addLeaderSpaceTest(){
        Warehouse w = new Warehouse();
        int i = 3;
        for(CardLeader c : Arrays.asList(CardLeader.CARDLEAD05, CardLeader.CARDLEAD06, CardLeader.CARDLEAD07, CardLeader.CARDLEAD08)){
            w.addLeaderSpace(c.getSpecial());
            assertEquals(++i, w.getWareContent().size());
        }
    }
}