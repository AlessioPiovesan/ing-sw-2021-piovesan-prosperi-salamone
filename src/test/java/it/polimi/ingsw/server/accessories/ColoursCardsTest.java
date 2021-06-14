package it.polimi.ingsw.server.accessories;

import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColoursCardsTest{

    @Test
    void coloredTest(){
        for(var color : ColoursCards.values())
            switch(color.name()){
                case "GREEN" -> assertEquals(ConsoleColors.GREEN + "GREEN" + ConsoleColors.RESET, ColoursCards.colored(color.name()));
                case "CYAN" -> assertEquals(ConsoleColors.CYAN + "CYAN" + ConsoleColors.RESET, ColoursCards.colored(color.name()));
                case "VIOLET" -> assertEquals(ConsoleColors.PURPLE + "VIOLET" + ConsoleColors.RESET, ColoursCards.colored(color.name()));
                case "YELLOW" -> assertEquals(ConsoleColors.YELLOW + "YELLOW" + ConsoleColors.RESET, ColoursCards.colored(color.name()));
            }
    }
}