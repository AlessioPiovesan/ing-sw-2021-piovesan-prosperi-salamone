package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest{

    @Test
    void checkAddTest(){
        var w = new Warehouse();
        assertTrue(w.checkAdd(new Pick(Colours.BLUE, 1)));
        w.moveResources(1, new Pick(Colours.BLUE, 1));
        assertTrue(w.checkAdd(new Pick(Colours.YELLOW, 3)));
        assertFalse(w.checkAdd(new Pick(Colours.BLUE, 4)));
        assertFalse(w.checkAdd(new Pick(Colours.BLUE, 2)));
        assertFalse(w.checkAdd(1, new Pick(Colours.BLUE, 2)));
    }

    @Test
    void isThereThatResTest(){
        var w = new Warehouse();
        assertEquals(0, w.isThereThatRes(Colours.WHITE));
        assertEquals(-1, w.isThereThatRes(Colours.BLUE));
        w.moveResources(1, new Pick(Colours.BLUE, 2));
        assertEquals(-1, w.isThereThatRes(Colours.YELLOW));
        assertEquals(1, w.isThereThatRes(Colours.BLUE));
    }

    @Test
    void lvlExchangeTest(){
        var w = new Warehouse();
        w.moveResources(0, new Pick(Colours.BLUE, 1));
        w.moveResources(2, new Pick(Colours.YELLOW, 1));
        w.lvlExchange(0, 2);
        assertEquals(Colours.YELLOW, w.getWareContent().get(0).resource());
        assertEquals(Colours.BLUE, w.getWareContent().get(2).resource());
    }

    @Test
    void checkStatusTest(){
        var w = new Warehouse();
        var list = w.checkStatus();
        assertEquals(3, list.size());
        IntStream.range(0, 3).mapToObj(list::get).forEachOrdered(pick -> {
            assertEquals(Colours.WHITE, pick.resource());
            assertEquals(0, pick.qnt());
        });
    }

    @Test
    void checkLvlStatusTest(){
        var w = new Warehouse();
        IntStream.range(0, 3).mapToObj(w::checkLvlStatus).forEachOrdered(pick -> {
            assertEquals(Colours.WHITE, pick.resource());
            assertEquals(0, pick.qnt());
        });
    }
}