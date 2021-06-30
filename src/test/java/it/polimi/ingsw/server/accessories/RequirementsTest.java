package it.polimi.ingsw.server.accessories;

import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequirementsTest{

    @Test
    void toStringCardTest(){
        var card = CardLeader.CARDLEAD01;
        var cardReq = card.getReq();
        for(var req : cardReq){
            var string = req.toString();
            var thisReq = (Requirements.RequirementsCard)req;
            assertEquals(string, "A level " + thisReq.getCardLvl() + " " + ColoursCards.colored(thisReq.getColour().name()) + " color card");
        }
    }

    @Test
    void toStringPickTest(){
        var card = CardLeader.CARDLEAD05;
        var cardReq = card.getReq();
        for(var req : cardReq){
            var string = req.toString();
            var thisReq = (Requirements.RequirementsResources)req;
            assertEquals(string, thisReq.getQnt() + " " + thisReq.getColour().getType());
        }
    }
}
