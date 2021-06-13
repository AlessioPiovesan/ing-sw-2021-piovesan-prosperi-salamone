package it.polimi.ingsw.server.accessories.cards;

import it.polimi.ingsw.clients.utils.ConsoleColors;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardDevTest{

    @Test
    void testToString(){
        for(var card : CardDev.values()){
            var name = card.name();
            var lvl = card.getLvl();
            var colour = card.getColour();
            var cost = card.getCost();
            var resTo = card.getResTo();
            var resFrom = card.getResFrom();
            var winPnt = card.getWinPnt();
            assertEquals(getColor(colour) + name + ConsoleColors.RESET + "\n" + "This is a " + " level " + lvl + " " + ColoursCards.colored(colour.name()) + " card that gives " + winPnt + " points.\nIt costs" + "\t->\t" + showPick(cost) + "\nThis card allows you to produce\n" + showPick(resTo) + "\nStarting from\n" + showPick(resFrom), card.toString());
        }
    }

    @NotNull String getColor(ColoursCards colour){
        return switch(colour){
            case YELLOW -> ConsoleColors.YELLOW_BACKGROUND;
            case VIOLET -> ConsoleColors.PURPLE_BACKGROUND;
            case GREEN -> ConsoleColors.GREEN_BACKGROUND;
            case CYAN -> ConsoleColors.CYAN_BACKGROUND;
        };
    }

    String showPick(Pick[] picks){
        var string = new StringBuilder();
        for(var pick : picks)
            string.append(pick.getQnt()).append(" - ").append(pick.getResource().getType()).append("\t");
        return String.valueOf(string);
    }

    @Test
    void getCardTest(){
        for(var card : CardDev.values())
            for(var i : new String[]{"1", "2", "3", "4"})
                for(var c : new String[]{"B", "G", "P", "Y"})
                    for(var j : new String[]{"1", "2", "3"}){
                        var code = i + c + j;
                        if(card.name().contains(code)) assertEquals(card, CardDev.getSpecificCard(code));
                        else assertNotEquals(card, CardDev.getSpecificCard(code));
                    }
    }

    @Test
    void flippedTest(){
        for(var card : CardDev.values()){
            assertTrue(card.getFlipped());
            card.flipCard();
            assertFalse(card.getFlipped());
        }
    }
}