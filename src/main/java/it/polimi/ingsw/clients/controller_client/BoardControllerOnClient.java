package it.polimi.ingsw.clients.controller_client;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.model_server.board.Board;

public class BoardControllerOnClient{

	private final Board player;

	public BoardControllerOnClient(Board player){
		this.player = player;
	}

	public Board getPlayer(){
		return player;
	}

	public void warehouseResHandler(Colours colour, int qnt, int lvl){
		player.storeW(lvl, new Pick[]{new Pick(colour, qnt)});
	}
}
