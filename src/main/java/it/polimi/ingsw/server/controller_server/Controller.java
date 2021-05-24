package it.polimi.ingsw.server.controller_server;

import java.util.LinkedList;
import java.util.List;

public class Controller{

	private static int idGame = 0;
	private static Controller singleInstance;
	private final List<GameController> gameControllerList;

	private Controller(){
		gameControllerList = new LinkedList<>();
	}

	public static Controller getInstance(){
		if(singleInstance == null) singleInstance = new Controller();
		return singleInstance;
	}

	public static int newID(){
		return idGame++;
	}

	public void setGamers(int id, int gamers){
		gameControllerList.get(id).setGamers(gamers);
	}

	public void addGame(GameController gameController){
		gameControllerList.add(gameController);
	}

	public GameController getGameControllerById(int id){
		return gameControllerList.get(id);
	}
}
