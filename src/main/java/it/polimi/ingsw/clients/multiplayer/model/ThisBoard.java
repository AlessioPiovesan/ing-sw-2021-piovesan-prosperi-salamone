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

    private String nickname;
    private List<Pick> warehouse;
    private int faithTrackPos;
    private EnumMap<Colours, Integer> strongBox;
    private List<ArrayList<CardDev>> library;
    private EnumMap<Colours, Integer> resAll;
    private boolean[] activeLeader;
    private Colours[] discount;
    private Colours[] marketAlt;
    private Colours[] productAlt;
    private Map<Colours, Integer> leaderSpace;
    private CardLeader[] myLeaderCards = new CardLeader[2];

    protected ThisBoard(){
        warehouse = createWarehouse();
        strongBox = createStrongBox();
        faithTrackPos = 0;
        library = createLibrary();
        discount = new Colours[]{Colours.WHITE, Colours.WHITE};
        marketAlt = new Colours[]{Colours.WHITE, Colours.WHITE};
        productAlt = new Colours[]{Colours.WHITE, Colours.WHITE};
        leaderSpace = Collections.unmodifiableMap(new HashMap<>());
    }

    protected List<Pick> createWarehouse(){
        var wareContent = new ArrayList<Pick>();
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        return wareContent;
    }

    protected EnumMap<Colours, Integer> createStrongBox(){
        var strongContent = new EnumMap<Colours, Integer>(Colours.class);
        strongContent.put(Colours.BLUE, 0);
        strongContent.put(Colours.GREY, 0);
        strongContent.put(Colours.PURPLE, 0);
        strongContent.put(Colours.YELLOW, 0);
        return strongContent;
    }

    protected List<ArrayList<CardDev>> createLibrary(){
        var slots = new ArrayList<ArrayList<CardDev>>(3);
        slots.add(new ArrayList<>());
        slots.add(new ArrayList<>());
        slots.add(new ArrayList<>());
        return slots;
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
        return faithTrackPos;
    }

    public void setFaithTrack(int faithTrack){
        this.faithTrackPos = faithTrack;
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

    public CardLeader[] getMyLeaderCards(){
        return myLeaderCards;
    }

    public void setMyLeaderCards(CardLeader[] myLeaderCards){
        this.myLeaderCards = myLeaderCards;
    }
}
