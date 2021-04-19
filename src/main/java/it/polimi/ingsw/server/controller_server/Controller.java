package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.Mod;
import it.polimi.ingsw.server.viewer_server.Observer;

public class Controller implements Observer<Game>{

    private final Mod mod;
    private int gamers;
    private TurnController turnController;

    public Controller (Mod mod){
        this.mod = mod;
    }

    private void nextTurn ( ){
        /* DA IMPLEMENTARE */
    }

    @Override
    public void update (Game test){
        /*Riceve le scelte del giocatore e manda avanti la partita*/
        nextTurn();
    }

    public Mod getMod ( ){
        return mod;
    }

    public int getGamers ( ){
        return gamers;
    }

    public void setGamers (int gamers){
        this.gamers = gamers;
    }
}
