package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.model_server.carte.CardDev;

import java.util.ArrayList;

public class Library{

    /* Creation CardDev slot for each board */
    private final ArrayList[] slot;


    public Library ( ){

        slot = new ArrayList[3];
    }

    public boolean checkAddableCard (int n, CardDev card){
        return card.getLvl() >= getCardOnTop(n).getLvl();
    }

    public void addCard (int n, CardDev card){
        slot[n].add(card);
    }

    public CardDev getCardOnTop (int n){
        return (CardDev)slot[n].get(slot.length);
    }

    public ArrayList getCardOfASlot (int n){
        return slot[n];
    }
}
