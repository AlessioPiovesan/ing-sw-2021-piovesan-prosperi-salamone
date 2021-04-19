package it.polimi.ingsw.server.model_server.gioco;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test
    void pickStartingResourcesTest ( ){
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void testPickStartingResources (int value){
        Game game = new Game(value, null, null);
    }
}