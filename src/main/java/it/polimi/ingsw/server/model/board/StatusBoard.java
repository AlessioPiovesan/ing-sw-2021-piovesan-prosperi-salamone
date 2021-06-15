package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.List;
import java.util.Map;

/**
 * class StatusBoard is a data structure that records a board's status for client's usage.
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public record StatusBoard(List<CardDev> myDevCards, Colours[][] warehouse, int faithTrack,
        Map<Colours, Integer> strongBox, CardDev[] library, Map<Colours, Integer> resAll, CardLeader[] myLeaderCards){

    public List<CardDev> getMyDevCards(){
        return myDevCards;
    }

    public Colours[][] getWarehouse(){
        return warehouse;
    }

    public int getFaithTrack(){
        return faithTrack;
    }

    public Map<Colours, Integer> getStrongBox(){
        return strongBox;
    }

    public CardDev[] getLibrary(){
        return library;
    }

    public Map<Colours, Integer> getResAll(){
        return resAll;
    }

    public CardLeader[] getMyLeaderCards(){
        return myLeaderCards;
    }
}
