package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.Mod;
import it.polimi.ingsw.server.viewer_server.Observer;

public class GameController implements Observer<Game>{

    private TurnController turnController;

    public GameController (Mod mod){    }

    private void nextTurn ( ){
        /* DA IMPLEMENTARE */
    }

    @Override
    public void update (Game game){
        /*Riceve le scelte del giocatore e manda avanti la partita*/
        nextTurn();
    }
}
