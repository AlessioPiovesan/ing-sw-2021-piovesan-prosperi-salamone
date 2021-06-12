package it.polimi.ingsw.server.accessories;

import it.polimi.ingsw.clients.viewer.ConsoleColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColoursTest{

    @Test
    void validTest(){
        for(Colours colours : Colours.values())
            if(Colours.valid().contains(colours)) assertTrue(Colours.valid().contains(colours));
            else assertFalse(Colours.valid().contains(colours));
    }

    @Test
    void fromStringTest(){
        for(Colours color : Colours.values()){
            assertEquals(color, Colours.fromString(color.name()));
            assertEquals(color, Colours.fromString(deColor(color.getType())));
        }
        assertNull(Colours.fromString("ciao"));
    }

    String deColor(String s){
        return switch(s){
            case (ConsoleColors.BLUE + "SHIELD" + ConsoleColors.RESET) -> "SHIELD";
            case (ConsoleColors.BLACK_BRIGHT + "STONE" + ConsoleColors.RESET) -> "STONE";
            case (ConsoleColors.PURPLE + "SERVANT" + ConsoleColors.RESET) -> "SERVANT";
            case (ConsoleColors.RED + "FAITH" + ConsoleColors.RESET) -> "FAITH";
            case (ConsoleColors.YELLOW + "COIN" + ConsoleColors.RESET) -> "COIN";
            default -> ("");
        };
    }
}