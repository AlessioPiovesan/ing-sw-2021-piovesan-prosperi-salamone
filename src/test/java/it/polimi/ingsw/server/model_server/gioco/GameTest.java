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
    @ValueSource(ints = {2, 3})
    void testPickStartingResources(int value) {
        int[][] results = {{0, 1, 1, 2}, {2, 0, 1, 1}, {1, 2, 0, 1}, {1, 1, 2, 0}};
        Game game = new Game(4, null, null);
        game.pickStartingResources(4, value);
    }
}