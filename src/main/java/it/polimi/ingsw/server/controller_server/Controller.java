package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.Mod;

public class Controller{

    private final Mod mod;
    private int gamers;

    public Controller (Mod mod){
        this.mod = mod;
    }

    public int getGamers ( ){
        return gamers;
    }

    public void setGamers (int gamers){
        this.gamers = gamers;
    }
}
