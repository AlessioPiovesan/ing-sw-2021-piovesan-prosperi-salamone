package it.polimi.ingsw.Server.controller_Server;

import it.polimi.ingsw.Server.model_Server.Game;
import it.polimi.ingsw.Server.model_Server.Mod;
import it.polimi.ingsw.Server.viewer_Server.Observer;
import it.polimi.ingsw.Server.viewer_Server.View;

public class Controller implements Observer<Game> {
    private Mod mod;
    private View v;

    public Controller (Mod mod, View v ){

        this.mod = mod;
        this.v = v;

    }

    private void nextTurn(){    }


    @Override
    public void update ( Game test ) {
        /*Riceve le scelte del giocatore e manfa avanti la partita*/
        nextTurn();
    }
}
