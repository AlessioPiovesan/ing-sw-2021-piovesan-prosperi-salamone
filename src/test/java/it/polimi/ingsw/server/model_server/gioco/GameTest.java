package it.polimi.ingsw.server.model_server.gioco;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest{

    @Test
    void giveCardLeadTest ( ){
    }

    @Test
    void pickCardLeadTest ( ){
    }

    @Test
    void nextPlayerIdTest ( ){
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void PickStartingResourcesTest (int value){
        int[][] results = {{0, 1, 1, 2}, {2, 0, 1, 1}, {1, 2, 0, 1}, {1, 1, 2, 0}};
        Game game = new Game(null, null);
        game.pickStartingResources(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void checkNextTest (int value){
        Game game = new Game(null, null);
        int next = game.checkNext(value);
        switch (value){
            case 0:{
                assertEquals(1, next);
                break;
            }
            case 1:{
                assertEquals(2, next);
                break;
            }
            case 2:{
                assertEquals(3, next);
                break;
            }
            case 3:{
                assertEquals(0, next);
                break;
            }
        }
    }
}