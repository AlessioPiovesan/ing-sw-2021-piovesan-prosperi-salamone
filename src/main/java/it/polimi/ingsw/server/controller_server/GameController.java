package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.client.accessori.Constants;
import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.plancia.Board;
import it.polimi.ingsw.server.viewer_server.Observer;

public class GameController implements Observer<Game>{

    Game game;
    private TurnController turnController;

    public GameController (Game game){
        this.game = game;
    }

    private void nextTurn ( ){
        /* DA IMPLEMENTARE */
    }

    @Override
    public void update (Game game){
        /*Riceve le scelte del giocatore e manda avanti la partita*/
        nextTurn();
    }

    public void endGame ( ){
        int[] win = new int[game.getnGamers()];
        int i = 0;
        while (i < game.getnGamers()){
            for (Board b: game.getAllB()){
                if (b.getFaithTrack().getPosition() == Constants.MAXTRACK.getVal()) win[i] += b.countWinPnt();
                i++;
            }
        }

    }
}
