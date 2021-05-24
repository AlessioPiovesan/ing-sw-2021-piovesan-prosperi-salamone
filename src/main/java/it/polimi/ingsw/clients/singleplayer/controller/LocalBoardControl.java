package it.polimi.ingsw.clients.singleplayer.controller;

import it.polimi.ingsw.clients.singleplayer.model.Lorenzo;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.model.board.Board;

public class LocalBoardControl{

	private final Board player;
	private final Lorenzo lorenzo;

	public LocalBoardControl(Board player, Lorenzo lorenzo){
		this.player = player;
		this.lorenzo = lorenzo;
	}

	public Lorenzo getLorenzo(){
		return lorenzo;
	}

	public Board getPlayer(){
		return player;
	}

	public void warehouseResHandler(Colours colour, int qnt, int lvl){
		player.storeW(lvl, new Pick[]{new Pick(colour, qnt)});
	}

	public void active(){
		player.ready();

	}
}
