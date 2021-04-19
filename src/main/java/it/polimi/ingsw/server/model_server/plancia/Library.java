package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.model_server.carte.CardDev;

import java.util.ArrayList;
import java.util.List;

public class Library{

    private final ArrayList<CardDev>[] slot;


    public Library ( ){
        /* Creation CardDev slot for each board */
        slot = new ArrayList[3];

    }

    public boolean checkAddableCard (int n, CardDev card){
        return card.getLvl() >= getCardOnTop(n).getLvl();
    }

    public void addCard (int n, CardDev card){
        slot[n].add(card);
    }

    public CardDev getCardOnTop (int n){
        return slot[n].get(slot[n].size());
    }

    public List<CardDev> getCardOfASlot (int n){
        return slot[n];
    }
}
