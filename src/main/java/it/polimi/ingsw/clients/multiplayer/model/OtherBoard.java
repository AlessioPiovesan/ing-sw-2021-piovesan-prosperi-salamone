package it.polimi.ingsw.clients.multiplayer.model;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Class OtherBoard is the class describing all the other players' boards.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class OtherBoard{

    private final Colours[] discount;
    private final Colours[] marketAlt;
    private final Colours[] productAlt;
    private final Map<Colours, Integer> leaderSpace;
    private final List<ArrayList<CardDev>> library;
    private String nickname;
    private List<Pick> warehouse;
    private int faithTrackPos;
    private EnumMap<Colours, Integer> strongBox;
    private EnumMap<Colours, Integer> resAll;
    private boolean[] activeLeader;
    private CardLeader[] myLeaderCards = new CardLeader[2];

    protected OtherBoard(){
        warehouse = createWarehouse();
        strongBox = createStrongBox();
        library = createLibrary();
        faithTrackPos = 0;
        activeLeader = new boolean[2];
        resAll = new EnumMap<>(Colours.class);
        discount = new Colours[]{Colours.WHITE, Colours.WHITE};
        marketAlt = new Colours[]{Colours.WHITE, Colours.WHITE};
        productAlt = new Colours[]{Colours.WHITE, Colours.WHITE};
        leaderSpace = new EnumMap<>(Colours.class);
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

    public Colours[] getDiscount(){
        return discount;
    }

    public void setDiscount(int i, Colours discount){
        this.discount[i] = discount;
    }

    public Colours[] getMarketAlt(){
        return marketAlt;
    }

    public void setMarketAlt(int i, Colours marketAlt){
        this.marketAlt[i] = marketAlt;
    }

    public Colours[] getProductAlt(){
        return productAlt;
    }

    public void setProductAlt(int i, Colours productAlt){
        this.productAlt[i] = productAlt;
    }

    public Map<Colours, Integer> getLeaderSpace(){
        return leaderSpace;
    }

    public void addLeaderSpace(Colours special){
        leaderSpace.put(special, 0);
    }
}
