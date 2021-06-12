package it.polimi.ingsw.server.accessories.cards;

import it.polimi.ingsw.clients.viewer.ConsoleColors;
import it.polimi.ingsw.server.accessories.Requirements;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardLeaderTest{

    @Test
    void testToStringTest(){
        for(var card : CardLeader.values()){
            var name = card.name();
            var ability = card.getAbility();
            var winPnt = card.getWinPnt();
            var req = card.getReq();
            var special = card.getSpecial();
            assertEquals(ConsoleColors.PURPLE + name + ConsoleColors.RESET + "\t->\t" + "this card gives you the ability of\n" + ConsoleColors.RED_UNDERLINED + ability + ConsoleColors.RESET + " and " + winPnt + " points,\n\tto activate this card you need:\n" + showReq(req), card.toString());
        }
    }

    String showReq(Requirements[] requirements){
        var stringReq = new StringBuilder();
        for(var requirement : requirements)
            stringReq.append("\t\t").append(requirement.toString()).append("\n");
        return String.valueOf(stringReq);
    }

    @Test
    void getCardTest(){
        for(var card : CardDev.values())
            for(var i = 0; i < 16; i++){
                var code = String.valueOf(i);
                if(i < 10) code = "0" + code;
                if(card.name().contains(code)) assertEquals(card, CardDev.getSpecificCard(code));
                else assertNotEquals(card, CardDev.getSpecificCard(code));
            }
    }

    @Test
    void flippedTest(){
        for(var card : CardLeader.values()){
            assertTrue(card.getFlipped());
            card.flipCard();
            assertFalse(card.getFlipped());
        }
    }

    @Test
    void getSpecificCardTest(){
        for(var card : CardLeader.values())
            for(var i = 1; i < 16; i++){
                String code;
                if(i < 9) code = "0".concat(String.valueOf(i));
                else code = String.valueOf(i);
                if(card.name().contains(code)) assertEquals(card, CardLeader.getSpecificCard(code));
                else assertNotEquals(card, CardLeader.getSpecificCard(code));
            }
    }
}