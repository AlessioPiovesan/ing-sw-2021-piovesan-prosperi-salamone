package it.polimi.ingsw.server.model_server.gioco;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import it.polimi.ingsw.server.model_server.plancia.Board;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest{

    @Test
    void giveCardLeadTest ( ){
        Game game = new Game(null, null);
        ArrayList<CardLeader> cardPlayer = (ArrayList<CardLeader>)game.giveCardLead();
        for (CardLeader cardLeader: cardPlayer){assertTrue(cardPlayer.contains(cardLeader));}
        assertEquals(4, cardPlayer.size());
    }

   /* @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void PickStartingResourcesTest (int value){
        int[][] results = {{0, 1, 1, 2}, {2, 0, 1, 1}, {1, 2, 0, 1}, {1, 1, 2, 0}};
        Game game = new Game(null, null);
        game.pickStartingResources(value);
        int res = 0;
        for (int i = 0;i < game.getAllB().length;i++){
            for (Colours c: Arrays.asList(Colours.BLUE, Colours.GREY, Colours.PURPLE, Colours.YELLOW))
                res += game.getSingleB(i).getResAll().get(c);
            assertEquals ( results[value][i], res);
        }
    }*/

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void checkNextTest (int value){
        Game game = new Game(null, null);
        int next = game.checkNext(value);
        switch (value){
            case 0 -> assertEquals(1, next);
            case 1 -> assertEquals(2, next);
            case 2 -> assertEquals(3, next);
            case 3 -> assertEquals(0, next);
        }
    }
}