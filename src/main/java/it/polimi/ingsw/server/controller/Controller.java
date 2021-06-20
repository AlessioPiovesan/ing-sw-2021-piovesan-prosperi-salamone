package it.polimi.ingsw.server.controller;

import java.util.LinkedList;
import java.util.List;

/**
 * Class Controller is a class that contains every single game's controller.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
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

    /**
     * Method newID returns the Game's controller ID.
     *
     * @return Game ID of type int
     * @author Alessio Piovesan
     * @author Pier Giorgio Prosperi
     * @author Matteo Salamone
     */
    public int newID(){
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

    public GameController getGameById(int id){
        return gameControllerList.get(id);
    }
}
