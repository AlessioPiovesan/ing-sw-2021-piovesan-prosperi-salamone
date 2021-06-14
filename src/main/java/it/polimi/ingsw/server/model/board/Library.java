package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.cards.CardDev;

import java.util.ArrayList;
import java.util.List;

/**
 * class Library is where a player can store his Development Cards.
 * Level three cards can only be placed on top of level two cards which can only be placed on top of level one cards.
 * There are a total of three slots for Development Cards in the Library
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */

public class Library{

    /* Creation CardDev slot for each board */
    private final ArrayList<ArrayList<CardDev>> slot;


    public Library(){
        slot = new ArrayList<>(3);
        slot.add(new ArrayList<>());
        slot.add(new ArrayList<>());
        slot.add(new ArrayList<>());
    }

    /**
     * method checkAddableCard checks if a card can be stored in the selected slot in the card.
     * @param n of type int - slot for the card
     * @param card of type CardDev - the type of card that the player wants to store
     * @return boolean - true is it can be stored, false otherwise
     */
    protected boolean checkAddableCard(int n, CardDev card){
        return getCardOnTop(n) == null || card.getLvl() > getCardOnTop(n).getLvl();
    }

    /**
     * method getCardOnTop returns visible card from the selected slot
     * @param n of type int - selected slot
     * @return CardDev - returns the CardDev on top
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    protected CardDev getCardOnTop(int n){
        return slot.get(n).get(slot.get(n).size() - 1);
    }

    protected ArrayList<ArrayList<CardDev>> getSlot(){
        return slot;
    }

    /**
     * method addCard adds a card into the slot
     * @param n of type int - selected slot
     * @param card of type CardDev - card that is going to be added
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    protected void addCard(int n, CardDev card){
        slot.get(n).add(card);
    }

    /**
     * method getWinPntCardDev returns sum of all the win points from Development Cards
     * @return win of type int - returns the sum of win points from Development Cards
     */
    public int getWinPntCardDev(){
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

    /**
     * method visibleCards creates a list of all the visible cards on the board
     * @return List - a list of all the visible cards on the board
     */
    protected List<CardDev> visibleCards(){
        var list = new ArrayList<CardDev>();
        for(var i = 0; i < 3; i++){
            list.add(getCardOnTop(i));
        }
        return list;
    }
}
