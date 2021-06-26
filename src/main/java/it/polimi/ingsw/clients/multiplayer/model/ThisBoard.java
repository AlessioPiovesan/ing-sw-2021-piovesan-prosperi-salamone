package it.polimi.ingsw.clients.multiplayer.model;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.*;

/**
 * class ThisBoard is the model class for the current playing player's board in a multiplayer game.
 * The class prepares the player's board with a series of getters and setters.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */

public class ThisBoard{

    private final LinkedList<CardDev> myDevCards;
    private String nickname;
    private List<Pick> warehouse;
    private int faithTrack;
    private EnumMap<Colours, Integer> strongBox;
    private List<ArrayList<CardDev>> library;
    private EnumMap<Colours, Integer> resAll;
    private boolean[] activeLeader;
    private Colours choosingRes;
    private CardLeader[] myLeaderCards = new CardLeader[2];

    protected ThisBoard(){myDevCards = new LinkedList<>();}

    public List<CardDev> getMyDevCards(){
        return myDevCards;
    }

    public List<Pick> getWarehouse(){
        return warehouse;
    }

    public void setWarehouse(List<Pick> warehouse){
        this.warehouse = warehouse;
    }

    public List<ArrayList<CardDev>> getLibrary(){
        return library;
    }

    public void setLibrary(List<ArrayList<CardDev>> library){
        this.library = library;
    }

    public int getFaithTrack(){
        return faithTrack;
    }

    public void setFaithTrack(int faithTrack){
        this.faithTrack = faithTrack;
    }

    public Map<Colours, Integer> getStrongBox(){
        return strongBox;
    }

    public void setStrongBox(Map<Colours, Integer> strongBox){
        this.strongBox = (EnumMap<Colours, Integer>)strongBox;
    }

    public Map<Colours, Integer> getResAll(){
        return resAll;
    }

    public void setResAll(Map<Colours, Integer> resAll){
        this.resAll = (EnumMap<Colours, Integer>)resAll;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public boolean[] getActiveLeader(){
        return activeLeader;
    }

    public void setActiveLeader(boolean[] activeLeader){
        this.activeLeader = activeLeader;
    }

    public Colours getChoosingRes(){
        return choosingRes;
    }

    public void setChoosingRes(Colours choosingRes){
        this.choosingRes = choosingRes;
    }

    public CardLeader[] getMyLeaderCards(){
        return myLeaderCards;
    }

    public void setMyLeaderCards(CardLeader[] myLeaderCards){
        this.myLeaderCards = myLeaderCards;
    }
}
