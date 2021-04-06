package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Carte.CardDev;

import java.util.LinkedList;

class Library{

    LinkedList<CardDev>[] slot;

    Library ( ){
        /* Creation CardDev slot for each board */
        slot[1] = null;
        slot[2] = null;
        slot[3] = null;
    }

    public void setL (int n, CardDev card){
        if (slot[n].isEmpty())
            slot[n].add(card);
        else System.out.println("Slot occupato"); /* DA IMPLEMENTARE */
    }
}
