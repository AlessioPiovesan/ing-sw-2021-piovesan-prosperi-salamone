package it.polimi.ingsw.server.accessories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PickTest{

    @Test
    void pickTest(){
        var color = Colours.BLUE;
        var qnt = 3;
        var pick = new Pick(color, qnt);
        assertEquals(color, pick.getResource());
        assertEquals(qnt, pick.getQnt());
    }
}