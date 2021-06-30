package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModTest{

    @Test
    void newID(){
        var id = Mod.newID();
        assertEquals(id+1, Mod.newID());
    }
}