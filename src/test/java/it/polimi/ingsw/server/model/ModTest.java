package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModTest{

    @Test
    void newID(){
        assertEquals(1001, Mod.newID());
    }
}