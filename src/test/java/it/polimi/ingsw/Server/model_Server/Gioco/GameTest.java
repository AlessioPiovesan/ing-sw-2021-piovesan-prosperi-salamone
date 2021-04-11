package it.polimi.ingsw.Server.model_Server.Gioco;

import it.polimi.ingsw.Server.model_Server.Carte.CardLeader;
import it.polimi.ingsw.Server.model_Server.Gioco.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends Game{

    GameTest (int gamers) throws NoSuchAlgorithmException{
        super(4);
    }

    /*@Test
    void giveCardLeadTest ( ){
        Game game = null;
        try {
            game = new Game(4);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            List<CardLeader> cardPlayer = game.giveCardLead();
            assertTrue(Arrays.stream(CardLeader.values()).anyMatch((Predicate<? super CardLeader>)cardPlayer));
        }
    }*/

    @Test
    void pickCardLeadTest ( ){
    }

    @Test
    void nextPlayerIdTest ( ){
    }

    @Test
    void pickStartingResourcesTest ( ){
    }
}