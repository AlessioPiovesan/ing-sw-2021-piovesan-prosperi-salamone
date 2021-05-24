package it.polimi.ingsw.clients.singleplayer.web;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.UI;

public class Local{

	private final UI ui;

	public Local(int i){
		if(i == 0) ui = new CLI();
		else ui = new GUI();
	}

	public void run(){
		var gameController = LocalGameController.getInstance();
		var game = gameController.getGame();
		var lorenzo = game.getBoard(true);
		lorenzo.addPropertyChangeListener(ui);
		var player = game.getBoard(false);
		player.addPropertyChangeListener(ui);
		gameController.startGame();
	}

}

