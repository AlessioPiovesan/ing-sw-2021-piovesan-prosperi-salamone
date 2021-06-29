package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.game.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest{

    @Test
    void startGameTest(){
        var gameController = new GameController(0, 4, new Game(0, 4, null));
        gameController.startGame();
        assertEquals(4, gameController.getGame().getNGamers());
        assertEquals(0, gameController.getGame().getPlayingPlayer());
    }

    @Test
    void endGameTest(){
        var gameController = new GameController(0, 4, new Game(0, 4, null));
        gameController.endGame();
        var win = gameController.getWin();
        for(var i : win)
            assertEquals(0, win[i]);
    }

    @Test
    void getterTester(){
        var gameController = new GameController(0, 4, new Game(0, 4, null));
        assertEquals(0, gameController.getId());
    }

    @Test
    void addFaithPntTest(){
        var gameController = new GameController(0, 4, new Game(0, 4, null));
        gameController.getGame().checkNext();
        var game = gameController.getGame();
        var list = new ArrayList<Colours>();
        list.add(Colours.BLUE);
        list.add(Colours.BLUE);
        gameController.addFaithPnt(list);
        for(var board : game.getAllB())
            if(!(board == game.getSingleB(game.getPlayingPlayer()))) assertEquals(2, board.getPositionFT());
            else assertEquals(0, board.getPositionFT());
    }

    @Test
    void cardDevAction(){
        var gameController = new GameController(0, 4, new Game(0, 4, null));
        var card = CardDev.CARDDEV_1C1;
        gameController.cardDevAction(card, 1);
        var id = gameController.getGame().getPlayingPlayer();
        var board = gameController.getGame().getSingleB(id);
        var lib = board.checkLibraryStatus().get(1);
        assertFalse(gameController.getGame().getCardDevDeck().contains(card));
        assertTrue(lib.contains(card));
    }

    @Test
    void cardLeadAction(){
        var gameController = new GameController(0, 4, new Game(0, 4, null));
        var card1a = CardLeader.CARDLEAD01;
        var card1b = CardLeader.CARDLEAD02;
        var card2 = CardLeader.CARDLEAD05;
        var card3 = CardLeader.CARDLEAD09;
        var card4 = CardLeader.CARDLEAD13;
        var id = gameController.getGame().getPlayingPlayer();
        var board = gameController.getGame().getSingleB(id);
        gameController.cardLeadAction(card1a);
        assertSame(card1a.getSpecial(), board.getDiscount(0));
        gameController.cardLeadAction(card1a);
        assertSame(card1b.getSpecial(), board.getDiscount(1));
        gameController.cardLeadAction(card2);
        assertTrue(board.getLeaderSpace().containsKey(card2.getSpecial()));
        gameController.cardLeadAction(card3);
        assertSame(card3.getSpecial(), board.getMarketAlt());
        gameController.cardLeadAction(card4);
        assertSame(card4.getSpecial(), board.getProductAlt());
    }

    @Test
    void marketAction(){

    }
}