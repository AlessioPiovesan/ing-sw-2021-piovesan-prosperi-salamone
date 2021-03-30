package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.M;
import it.polimi.ingsw.viewer.V;
import it.polimi.ingsw.viewer.Observer;

public class C implements Observer<M.Game> {
    private M m;
    private V v;

    public C ( M m, V v ){

        this.m = m;
        this.v = v;

    }

    private void nextTurn(){    }


    @Override
    public void update ( M.Game test ) {
        /*Riceve le scelte del giocatore e manfa avanti la partita*/
        nextTurn();
    }
}
