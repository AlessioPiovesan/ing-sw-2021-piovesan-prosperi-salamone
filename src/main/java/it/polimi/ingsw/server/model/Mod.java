package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.game.Game;

import java.util.HashMap;

public class Mod{

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

    public static int newID(){
        return idGame++;
    }

    public void addGame(int id, Game game){
        gameList.put(id, game);
    }
}
