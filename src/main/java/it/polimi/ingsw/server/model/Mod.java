package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.game.Game;

import java.util.ArrayList;
import java.util.List;

public class Mod{

	private static int idGame = 0;
	private static Mod singleInstance;
	private final List<Game> gameList;

	private Mod(){
		gameList = new ArrayList<>();
	}

	public static int newID(){
		return idGame++;
	}

	public void addGame(Game game){
		gameList.add(game);
	}
}
