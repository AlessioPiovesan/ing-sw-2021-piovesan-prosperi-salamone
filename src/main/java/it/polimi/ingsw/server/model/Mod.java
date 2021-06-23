package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.game.Game;

import java.util.HashMap;

/**
 * Class Mod is a class that contains every single game's Model.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Mod{

    private static int idGame = 1000;
    private static Mod singleInstance;
    private final HashMap<Integer, Game> gameList;

    private Mod(){
        gameList = new HashMap<>();
    }

    public static Mod getInstance(){
        if(singleInstance == null) singleInstance = new Mod();
        return singleInstance;
    }

    /**
     * Method newID returns the Game's ID.
     *
     * @return Game ID of type int
     */
    public static int newID(){
        return ++idGame;
    }

    /**
     * Method addGame serves as a list of all active Games
     *
     * @param id   of type int - Game's ID
     * @param game of type Game
     */
    public void addGame(int id, Game game){
        gameList.put(id, game);
    }

    public Game getGameById(int id){
        return gameList.get(id);
    }
}
