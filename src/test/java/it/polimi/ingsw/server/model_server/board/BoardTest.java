package it.polimi.ingsw.server.model_server.board;

import it.polimi.ingsw.server.accessories.Colours;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

    @Test
    void testCheckDiscardedResources() {
        Board b = new Board(0, null, null, null, null);
        LinkedList<Colours> list = new LinkedList<>();
        list.add(Colours.BLUE);
        list.add(Colours.YELLOW);
        list.add(Colours.GREY);
        list.add(Colours.BLUE);
        assertEquals(4, b.checkDiscardedResources(list));

    }
}