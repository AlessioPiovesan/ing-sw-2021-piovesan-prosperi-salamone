package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.game.Game;

import java.util.HashMap;

/**
 * class Mod is a class that contains every single game's Model.
 */

public class Mod {

    private static int idGame = 0;
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
     * method newID returns the Game's ID.
     * @return Game ID of type int
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public static int newID(){
        return idGame++;
    }

    /**
     * method addGame serves as a list of all active Games
     * @param id of type int - Game's ID
     * @param game of type Game
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void addGame(int id, Game game){
        gameList.put(id, game);
    }
}
