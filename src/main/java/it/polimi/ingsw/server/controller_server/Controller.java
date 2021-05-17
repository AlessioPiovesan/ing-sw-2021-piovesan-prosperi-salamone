package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.Mod;
import it.polimi.ingsw.server.model_server.game.Game;
import it.polimi.ingsw.server.model_server.game.Market;

import java.util.LinkedList;
import java.util.List;

public class Controller {

    private final Mod mod;
    private List<GameController> gameController;

    public Controller(Mod mod) {
        this.mod = mod;
        gameController = new LinkedList<>();
    }

    public void setGamers(int id, int gamers) {
        gameController.get(id).setGamers(gamers);
    }

    public GameController getGameControllerById(int id) {
        return gameController.get(id);
    }

    public void createMatch(int gamers) {
        var newGameController = new GameController(gamers, new Game(new Market()));
        newGameController.startGame();
    }
}
