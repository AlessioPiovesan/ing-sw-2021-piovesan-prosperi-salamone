package it.polimi.ingsw.server.model_server.board;

import it.polimi.ingsw.server.model_server.cards.CardDev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Library implements Serializable {

    /* Creation CardDev slot for each board */
    private final ArrayList<ArrayList<CardDev>> slot;


    public Library() {
        slot = new ArrayList<>(3);
    }

    public boolean checkAddableCard(int n, CardDev card) {
        return getCardOnTop(n) == null || card.getLvl() >= getCardOnTop(n).getLvl();
    }

    public void addCard(int n, CardDev card) {
        slot.get(n).add(card);
    }

    public CardDev getCardOnTop(int n) {
        return slot.get(n).get(slot.get(n).size() - 1);
    }

    public List<CardDev> getCardsOfASlot(int n) {
        return slot.get(n);
    }

    public int getWinPntCardDev() {
        var win = 0;
        for (var i = 0; i < slot.size(); i++)
            for (var j = 0; j < getCardsOfASlot(i).size(); j++) {
                CardDev card = slot.get(i).get(j);
                win += card.getWinPnt();
            }
        return win;
    }
}
