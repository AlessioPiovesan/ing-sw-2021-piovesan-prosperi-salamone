package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest{

    @Test
    void giveCardLeadTest(){
        var game = new Game(0, 4, null);
        var cardPlayer = (ArrayList<CardLeader>)game.giveCardLead();
        for(var cardLeader : cardPlayer){
            assertTrue(cardPlayer.contains(cardLeader));
        }
        assertEquals(4, cardPlayer.size());
    }

    @ParameterizedTest
    @ValueSource (ints = {0, 1, 2, 3})
    void checkNextTest(int value){
        var nextPlayer = new int[]{1, 2, 3, 0};
        var game = new Game(0, 4, null);
        game.setPlayingPlayer(value);
        game.checkNext();
        var thisNewPlayer = game.getPlayingPlayer();
        assertEquals(nextPlayer[value], thisNewPlayer);
    }

}