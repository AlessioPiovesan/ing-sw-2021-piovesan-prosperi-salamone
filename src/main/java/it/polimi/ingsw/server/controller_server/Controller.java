package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.Mod;
import it.polimi.ingsw.server.model_server.gioco.Game;

public class Controller{

    private final Mod mod;
    private Game game;
    private int gamers;

    public Controller (Mod mod){
        this.mod = mod;
    }

    public int getGamers ( ){
        return gamers;
    }

    public void setGamers (int gamers){
        game.setNGamers(gamers);
    }
}
