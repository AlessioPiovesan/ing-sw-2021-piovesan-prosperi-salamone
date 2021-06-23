package it.polimi.ingsw.server.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Controller is a class that contains every single game's controller.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Controller{

    private static int idGame = 1000;
    private static Controller singleInstance;
    private final Map<Integer, GameController> gameControllerMap;

    private Controller(){
        gameControllerMap = new HashMap<>();
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
    public static int newID(){
        return ++idGame;
    }

    public void addGame(int id, GameController gameController){
        gameControllerMap.put(id, gameController);
    }

    public GameController getGameControllerById(int id){
        return gameControllerMap.get(id);
    }
}
