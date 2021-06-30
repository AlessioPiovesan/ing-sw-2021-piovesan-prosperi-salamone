package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Mod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ControllerTest{

    @Test
    void getInstance(){
        assertNotNull(Controller.getInstance());
    }

    @Test
    void newID(){
        assertEquals(1001, Mod.newID());
    }

    @Test
    void addGame(){
        var gameController = new GameController(0, 0, null);
        Controller.getInstance().addGame(0, gameController);
        assertNotNull(Controller.getInstance().getGameControllerById(0));
    }
}