package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.Mod;

public class Controller {

    private final Mod mod;
    private GameController gameController;


    public Controller(Mod mod) {
        this.mod = mod;
    }

    public void setGamers(int gamers) {
        gameController.setGamers(gamers);
    }
}
