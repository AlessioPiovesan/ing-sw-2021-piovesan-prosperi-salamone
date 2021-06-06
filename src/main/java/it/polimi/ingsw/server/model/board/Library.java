package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.cards.CardDev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Library implements Serializable{

    /* Creation CardDev slot for each board */
    private final ArrayList<ArrayList<CardDev>> slot;


    public Library(){
        slot = new ArrayList<>(3);
        slot.add(new ArrayList<>());
        slot.add(new ArrayList<>());
        slot.add(new ArrayList<>());
    }

    protected boolean checkAddableCard(int n, CardDev card){
        return getCardOnTop(n) == null || card.getLvl() > getCardOnTop(n).getLvl();
    }

    protected CardDev getCardOnTop(int n){
        return slot.get(n).get(slot.get(n).size());
    }

    protected ArrayList<ArrayList<CardDev>> getSlot(){
        return slot;
    }

    protected void addCard(int n, CardDev card){
        slot.get(n).add(card);
    }

    protected int getWinPntCardDev(){
        var win = 0;
        for(var i = 0; i < slot.size(); i++)
            for(var j = 0; j < getCardsOfASlot(i).size(); j++){
                CardDev card = slot.get(i).get(j);
                win += card.getWinPnt();
            }
        return win;
    }

    protected List<CardDev> getCardsOfASlot(int n){
        return slot.get(n);
    }

    protected List<CardDev> visibleCards(){
        var list = new ArrayList<CardDev>();
        for(var i = 0; i < 3; i++){
            list.add(getCardOnTop(i));
        }
        return list;
    }
}
