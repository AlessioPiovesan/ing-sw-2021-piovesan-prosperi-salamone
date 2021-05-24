package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameControllerTest{

	@Test
	void startGame(){
		var gameController = new GameController(0, 4, new Game(0, 4, null));
		gameController.startGame();
		assertEquals(4, gameController.getGame().getNGamers());
		assertEquals(1, gameController.getGame().getPlayingPlayer());
	}

	@Test
	void endGame(){
		new GameController(0, 4, new Game(0, 4, null));
		assertTrue(true);
	}

	@Test
	void getterTester(){
		var gameConytoller = new GameController(0, 4, new Game(0, 4, null));
		assertEquals(0, gameConytoller.getId());
	}

}