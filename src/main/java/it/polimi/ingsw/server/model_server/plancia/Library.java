package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.model_server.carte.CardDev;

import java.util.ArrayList;
import java.util.List;

public class Library{

    /* Creation CardDev slot for each board */
    private final ArrayList[] slot;


    public Library ( ){
        slot = new ArrayList[3];
        for (var i = 0;i < 3;i++) slot[i] = new ArrayList();
    }

    public boolean checkAddableCard (int n, CardDev card){
        return getCardOnTop(n) == null || card.getLvl() >= getCardOnTop(n).getLvl();
    }

    public void addCard (int n, CardDev card){
        slot[n].add(card);
    }

    public CardDev getCardOnTop (int n){
        return (CardDev)slot[n].get(slot[n].size() - 1);
    }

    public List getCardsOfASlot (int n){
        return slot[n];
    }

    public int getWinPntCardDev ( ){
        var win = 0;
        for (var i = 0;i < slot.length;i++)
            for (var j = 0;j < getCardsOfASlot(i).size();j++){
                CardDev card = (CardDev)slot[i].get(j);
                win += card.getWinPnt();
            }
        return win;
    }
}
