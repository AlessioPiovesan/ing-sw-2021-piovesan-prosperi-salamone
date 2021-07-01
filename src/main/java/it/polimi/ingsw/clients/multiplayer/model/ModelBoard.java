package it.polimi.ingsw.clients.multiplayer.model;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.*;

/**
 * Class ModelBoard is the class describing all the other players' boards.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class ModelBoard{

    private final Map<Colours, Integer> leaderSpace;
    private final List<ArrayList<CardDev>> library;
    private final CardLeader[] myLeaderCards = new CardLeader[2];
    private String nickname;
    private List<Pick> warehouse;
    private int faithTrackPos;
    private EnumMap<Colours, Integer> strongBox;
    private EnumMap<Colours, Integer> resAll;

    protected ModelBoard(){
        warehouse = createWarehouse();
        strongBox = createStrongBox();
        library = createLibrary();
        faithTrackPos = 0;
        resAll = new EnumMap<>(Colours.class);
        leaderSpace = new EnumMap<>(Colours.class);
    }

    /**
     * Method createWarehouse creates the warehouse for the selected Model Board
     *
     * @return of type ArrayList of Pick
     * @author Alessio Piovesan
     * @author Pier Giorgio Prosperi
     * @author Matteo Salamone
     */
    private List<Pick> createWarehouse(){
        var wareContent = new ArrayList<Pick>();
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        return wareContent;
    }

    /**
     * Method createStrongbox creates the strongbox for the selected Model Board
     *
     * @return of type EnumMap of Colours
     * @author Alessio Piovesan
     * @author Pier Giorgio Prosperi
     * @author Matteo Salamone
     */
    private EnumMap<Colours, Integer> createStrongBox(){
        var strongContent = new EnumMap<Colours, Integer>(Colours.class);
        strongContent.put(Colours.BLUE, 0);
        strongContent.put(Colours.GREY, 0);
        strongContent.put(Colours.PURPLE, 0);
        strongContent.put(Colours.YELLOW, 0);
        return strongContent;
    }

    /**
     * Method createLibrary creates the containers for Development Cards
     * for the selected Model Board
     *
     * @return of type ArrayList of CardDev
     */
    private List<ArrayList<CardDev>> createLibrary(){
        var slots = new ArrayList<ArrayList<CardDev>>(3);
        slots.add(new ArrayList<>());
        slots.add(new ArrayList<>());
        slots.add(new ArrayList<>());
        return slots;
    }

    public List<Pick> getWarehouse(){
        return warehouse;
    }

    public void setWarehouse(int lvl, Colours colour, int qnt){
        var list = new LinkedList<Pick>();
        for(var i = 0; i < warehouse.size(); i++)
            if(i == lvl) list.add(new Pick(colour, qnt));
            else list.add(warehouse.get(i));
        this.warehouse = list;
    }

    public void setMyLeaderCards(CardLeader myLeaderCards, int i){
        this.myLeaderCards[i] = myLeaderCards;
    }

    public List<ArrayList<CardDev>> getLibrary(){
        return library;
    }

    public void addCardinLibrary(CardDev card, int slot){
        library.get(slot).add(card);
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

    public void setStrongBox(Colours colours, Integer qnt){
        this.resAll.put(colours, qnt);
    }

    public Map<Colours, Integer> getResAll(){
        return resAll;
    }

    public void setResAll(Colours colours, Integer qnt){
        this.resAll.put(colours, qnt);
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public CardLeader[] getMyLeaderCards(){
        return myLeaderCards;
    }

    public Map<Colours, Integer> getLeaderSpace(){
        return leaderSpace;
    }

    public void addLeaderSpace(Colours special){
        leaderSpace.put(special, 0);
    }
}
