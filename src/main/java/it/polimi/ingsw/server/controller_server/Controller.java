package it.polimi.ingsw.server.controller_server;

import java.util.LinkedList;
import java.util.List;

public class Controller {

    private static int idGame = 0;
    private static Controller singleInstance;
    private List<GameController> gameController;

    private Controller() {
        gameController = new LinkedList<>();
    }

    public static Controller getInstance() {
        if (singleInstance == null)
            singleInstance = new Controller();

        return singleInstance;
    }

    public static int newID() {
        return idGame++;
    }

    public void setGamers(int id, int gamers) {
        gameController.get(id).setGamers(gamers);
    }

    /*public void createMatch(int gamers) {
        var newGameController = new GameController(gamers, new Game(new Market()));
        newGameController.startGame();
    }*/

    public GameController getGameControllerById(int id) {
        return gameController.get(id);
    }
}
