package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.Mod;

import java.util.List;

public class Controller {

    private final Mod mod;
    private List<GameController> gameController;

    public Controller(Mod mod) {
        this.mod = mod;
    }

    public void setGamers(int id, int gamers) {
        gameController.get(id).setGamers(gamers);
    }

    public GameController getGameControllerById(int id){
        return gameController.get(id);
    }
}
