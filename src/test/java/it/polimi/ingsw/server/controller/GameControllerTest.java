package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.model.game.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
		var gameController = new GameController(0, 4, new Game(0, 4, null));
		assertEquals(0, gameController.getId());
	}

	@Test
	void addFaithPnt(){
		var gameController = new GameController(0, 4, new Game(0, 4, null));
		gameController.getGame().checkNext();
		var game = gameController.getGame();
		var list = new ArrayList<Colours>();
		list.add(Colours.BLUE);
		list.add(Colours.BLUE);
		gameController.addFaithPnt(list);
		for(var board : game.getAllB())
			if(!(board == game.getSingleB(game.getPlayingPlayer())))
				assertEquals(2, board.getFaithTrack().getPosition());
			else assertEquals(0, board.getFaithTrack().getPosition());
	}

}