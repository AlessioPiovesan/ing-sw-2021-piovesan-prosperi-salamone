package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.controller_server.BoardController;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testSetCardLead() {
        Board[] b = new Board[4];
        b[0]= new Board(0, null,null,null, null);
        BoardController boardController = new BoardController(null, b);
        boardController.pickCardLead(0);
        assertArrayEquals(b[0].getMyLeaderCards(), new CardLeader[]{CardLeader.card_Lead1, CardLeader.card_Lead2});
    }

    /*@Test
    void testCheckDiscardedResources() {
    }

    @Test
    void testProductionAll() {
    }

    @Test
    void testProduction() {
    }

    @Test
    void testGetResAll() {
    }

    @Test
    void testGetWarehouse() {
    }

    @Test
    void testGetFaithTrack() {
    }

    @Test
    void testUpdateResAll() {
    }

    @Test
    void testCountWinPnt() {
    }*/
}