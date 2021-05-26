package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StrongBoxTest{

    @Test
    void moveResources(){
        StrongBox strongBox = new StrongBox();
        for(Colours colour : Colours.values()){
            switch(colour){
                case RED, WHITE -> assertFalse(strongBox.getStrongContent().containsKey(colour));
                case BLUE, GREY, PURPLE, YELLOW -> assertEquals(0, strongBox.getStrongContent().get(colour));
            }
        }
        strongBox.moveResources(new Pick(Colours.BLUE, 5));
        assertEquals(5, strongBox.getStrongContent().get(Colours.BLUE));
        strongBox.moveResources(new Pick(Colours.BLUE, -3));
        assertEquals(2, strongBox.getStrongContent().get(Colours.BLUE));
    }
}