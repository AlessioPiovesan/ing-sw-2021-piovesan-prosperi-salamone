package it.polimi.ingsw.Server.controller_Server;

import it.polimi.ingsw.Server.model_Server.Gioco.Game;
import it.polimi.ingsw.Server.model_Server.Mod;
import it.polimi.ingsw.Server.viewer_Server.Observer;
import it.polimi.ingsw.Server.viewer_Server.View;

public class Controller implements Observer<Game> {

    private final Mod mod;

    public Controller (Mod mod){ this.mod = mod; }

    private void nextTurn(){    }

    @Override
    public void update ( Game test ) {
        /*Riceve le scelte del giocatore e manda avanti la partita*/
        nextTurn();
    }
}
