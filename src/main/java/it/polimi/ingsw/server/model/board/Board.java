package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Class Board describes a player's personal Board.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Board{

    private static final LinkedList<CardDev> myDevCards = new LinkedList<>();
    private static final String WAREHOUSE = "warehouse";
    private static final String STRONGBOX = "strongBox";
    private static final String ALL = "resAll";
    private final Warehouse warehouseObj;
    private final FaithTrack faithTrack;
    private final StrongBox strongBoxObj;
    private final Library library;
    private final EnumMap<Colours, Integer> resAllObj;
    private final PropertyChangeSupport pcs;
    private final PropertyChangeSupport listener;
    private final int myId;
    private final Map<Colours, Integer> leaderSpace;
    private final Colours[] discount;
    private boolean ready;
    private Colours marketAlt;
    private Colours productAlt;
    private Integer gameID;
    private boolean endGame;
    private String nickname;
    private CardLeader[] myLeaderCards = new CardLeader[2];

    public Board(int id, Warehouse warehouseObj, StrongBox strongBoxObj, FaithTrack faithTrack, Library library){
        endGame = false;
        this.myId = id;
        /* Warehouse creation for a model Board */
        this.warehouseObj = warehouseObj;
        /* Strongbox creation for a model Board */
        this.strongBoxObj = strongBoxObj;
        /* Faith Track creation for a model board */
        this.faithTrack = faithTrack;
        /* ________ creation for a model board */
        this.library = library;
        ready = false;
        resAllObj = new EnumMap<>(Colours.class);
        resAllObj.put(Colours.BLUE, 0);
        resAllObj.put(Colours.YELLOW, 0);
        resAllObj.put(Colours.GREY, 0);
        resAllObj.put(Colours.PURPLE, 0);
        discount = new Colours[2];
        discount[0] = Colours.WHITE;
        discount[1] = Colours.WHITE;
        marketAlt = Colours.WHITE;
        productAlt = Colours.WHITE;
        leaderSpace = new EnumMap<>(Colours.class);
        pcs = new PropertyChangeSupport(this);
        listener = new PropertyChangeSupport(this);
    }

    public void somethingIsWrong(String s){
        pcs.firePropertyChange("wrong", "", s);
    }

    public void ready(){
        ready = !ready;
        if(ready) pcs.firePropertyChange("nextAction", "", 1);
    }

    public Integer getGameID(){
        return gameID;
    }

    public void setGameID(Integer gameID){
        this.gameID = gameID;
    }

    public Colours getDiscount(int i){
        return discount[i];
    }

    /**
     * Method setDiscount is a consequence of one of the Leader Card's powers.
     * Discounts a chosen resource.
     *
     * @param color of type Colours - resource to be discounted
     */
    public void setDiscount(Colours color){
        if(discount[0] != Colours.WHITE && color != discount[1]) discount[1] = color;
        if(discount[0] == Colours.WHITE) discount[0] = color;
    }

    public Colours getMarketAlt(){
        return marketAlt;
    }

    /**
     * Method setMarketAlt is a consequence of one of the Leader Card's powers.
     * Transforms a white marble into a coloured one.
     *
     * @param color of type Colours - resource
     */
    public void setMarketAlt(Colours color){
        if(color != marketAlt) marketAlt = color;
    }

    public Colours getProductAlt(){
        return productAlt;
    }

    /**
     * Method setProductAlt is a consequence of one of the Leader Card's powers.
     * Adds a production activity by turning a resource into a preferred one and a Faith Point.
     *
     * @param color of type Colours - resource
     */
    public void setProductAlt(Colours color){
        if(color != productAlt) productAlt = color;
    }

    public CardLeader[] getMyLeaderCards(){
        return myLeaderCards;
    }

    /**
     * Method setMyLeaderCards saves the two chosen Leader Cards.
     *
     * @param cards of type CardLeader - Leader Cards
     */
    public void setMyLeaderCards(CardLeader[] cards){
        myLeaderCards = cards;
        pcs.firePropertyChange("firstOp", "", 0);
        listener.firePropertyChange("leaderCards", "", cards[0].name() + "::" + 0 + "::" + myId);
        listener.firePropertyChange("leaderCards", "", cards[1].name() + "::" + 1 + "::" + myId);
    }

    /**
     * Method setTemporaryLeaderCards is the method that chooses tow of the 4 leader cards presented to the user.
     *
     * @param leaders of type CardLeader - Leader cards
     */
    public void setTemporaryLeaderCards(CardLeader[] leaders){
        pcs.firePropertyChange("temporaryLeaderCards", "", leaders);
    }

    public Map<Colours, Integer> getResAll(){
        return resAllObj;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Method countWinPnt counts the Points to win a game.
     *
     * @return win of type int - win points
     */
    public int countWinPnt(){
        var win = 0;
        win += faithTrack.getWinPnt();
        win += library.getWinPntCardDev();
        win += getWinPntCardLeader();
        pcs.firePropertyChange("winPoints", "", win);
        return win;
    }

    /**
     * Method getWinPntCardLeader counts win points from Leader Cards
     *
     * @return win of type int - win points
     */
    private int getWinPntCardLeader(){
        var win = 0;
        for(var myLeaderCard : myLeaderCards){
            if(!myLeaderCard.getFlipped()) win += myLeaderCard.getWinPnt();
        }
        return win;
    }

    /**
     * Method buyCardDev is the method used to buy Development Cards using resources.
     *
     * @param card of type CardDev - Development card that the player wants to buy
     * @param slot of type int - slot where the user wants to place the card
     * @see #checkIfEndGame()
     */
    public void buyCardDev(CardDev card, int slot){
        var costCardDev = new LinkedList<Pick>();
        Pick[] cost = card.getCost();
        for(Pick pick : cost){
            var colour = pick.resource();
            var qnt = pick.qnt();
            if(pick.resource() == discount[0]) qnt--;

            if(pick.resource() == discount[1]) qnt--;
            costCardDev.add(new Pick(colour, qnt));
        }
        myDevCards.add(card);
        checkIfEndGame();
        library.addCard(slot, card);
        pcs.firePropertyChange("costCardDev", "", costCardDev.toArray());
        listener.firePropertyChange("newCard", "", card.name() + "::" + slot);
    }

    /**
     * Method checkIfEndGame is the method that checks if a game has come to an end.
     * a game can end by either a player reaching the end of the Faith Track
     * or if a player buys their seventh Development Card.
     */
    protected void checkIfEndGame(){
        if(myDevCards.size() == 7) endGame = true;
        if(getPositionFT() >= 24) endGame = true;
        if(endGame) pcs.firePropertyChange("endGame", false, true);
    }

    /**
     * Method getPositionFT gives the position on the faith track
     *
     * @return position
     * @see FaithTrack#getPosition
     */
    public int getPositionFT(){
        return faithTrack.getPosition();
    }

    public boolean getEndGame(){return endGame;}

    /**
     * Method marketResHandler transforms the marbles into resources whenever it has to.
     *
     * @param res of type Colours - resources
     * @see #setBufferMarket(List)
     */
    public void marketResHandler(List<Colours> res){
        var newRes = new LinkedList<Colours>();
        for(Colours colour : res)
            if(colour == Colours.WHITE && marketAlt != Colours.WHITE){
                newRes.add(marketAlt);
                pcs.firePropertyChange("change", "", "Your white marble is now a " + marketAlt + " marble");
            }
            else newRes.add(colour);
        setBufferMarket(newRes);
    }

    /**
     * Method setBufferMarket are the resources ready to be placed in the Warehouse
     *
     * @param res of type Colours - resources
     */
    public void setBufferMarket(List<Colours> res){
        pcs.firePropertyChange("marketBuffer", "", res.toArray());
    }

    public void setPreference(){
        pcs.firePropertyChange("preference", "", 0);
    }

    public void setDoublePreference(){
        pcs.firePropertyChange("doublePreference", "", 0);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public void addListener(PropertyChangeListener listener){
        this.listener.addPropertyChangeListener(listener);
    }

    /**
     * Method addLeaderSpace is activate by a leader card
     *
     * @param special resource type that you can add in the extra space
     */
    public void addLeaderSpace(Colours special){
        leaderSpace.put(special, 0);
        listener.firePropertyChange("leaderSpace", "", special + "::" + myId);
    }

    public Map<Colours, Integer> getLeaderSpace(){
        return leaderSpace;
    }

    /**
     * Passes the call to the Warehouse class and allows the exchange between
     * two shelves of that player's warehouse
     *
     * @param first  of type int
     * @param second of type int
     * @see Warehouse#lvlExchange
     */
    public void rearrangeWarehouse(int first, int second){
        warehouseObj.lvlExchange(first, second);
        listener.firePropertyChange(WAREHOUSE, "", first + "::" + warehouseObj.checkLvlStatus(first).resource() + "::" + warehouseObj.checkLvlStatus(first).qnt() + "::" + myId);
        listener.firePropertyChange(WAREHOUSE, "", second + "::" + warehouseObj.checkLvlStatus(second).resource() + "::" + warehouseObj.checkLvlStatus(second).qnt() + "::" + myId);
    }

    /**
     * Returns a list that contains the content of the Warehouse
     *
     * @return Pick
     * @see Warehouse#getWareContent()
     */
    public List<Pick> checkWarehouseStatus(){
        return warehouseObj.getWareContent();
    }

    /**
     * @param pick of type Pick
     * @param lvl  of type int
     * @return boolean
     * @see Warehouse#checkAdd(int, Pick)
     */
    public boolean checkWarehouseAdd(Pick pick, int lvl){
        return warehouseObj.checkAdd(lvl, pick);
    }

    /**
     * Returns a list that contains the cards that are visible on the board of a player
     *
     * @return CardDev
     * @see Library#getSlot()
     */
    public List<ArrayList<CardDev>> checkLibraryStatus(){
        return library.getSlot();
    }

    /**
     * Returns a map of the content of the Strongbox
     *
     * @return Map
     * @see StrongBox#getStrongContent()
     */
    public Map<Colours, Integer> checkStrongBoxStatus(){
        return strongBoxObj.getStrongContent();
    }

    /**
     * Method takeFromWarehouse takes resources from warehouse
     *
     * @param colour of type Colours
     * @see #storeInWarehouse(int, Pick[])
     * @see Warehouse#isThereThatRes(Colours)
     */
    public void takeFromWarehouse(Colours colour){
        var lvl = warehouseObj.isThereThatRes(colour);
        storeInWarehouse(lvl, new Pick[]{new Pick(colour, -1)});
    }

    /**
     * Method storeW stores Resources in the Warehouse
     *
     * @param lvl   of type int - Warehouse's shelf
     * @param start of type Pick
     * @see #updateResAll()
     * @see Warehouse#moveResources(int, Pick)
     */
    public void storeInWarehouse(int lvl, Pick[] start){
        for(var pick : start){
            warehouseObj.moveResources(lvl, pick);
        }
        updateResAll();
        listener.firePropertyChange(WAREHOUSE, "", lvl + "::" + warehouseObj.checkLvlStatus(lvl).resource() + "::" + warehouseObj.checkLvlStatus(lvl).qnt() + "::" + myId);
    }

    /**
     * Method updateResAll updates all of the resources in the board
     *
     * @see Warehouse#getWareContent()
     * @see StrongBox#getStrongContent()
     */
    public void updateResAll(){
        var w = warehouseObj.getWareContent();
        var s = strongBoxObj.getStrongContent();
        var map = new EnumMap<Colours, Integer>(Colours.class);
        var leaderKey = leaderSpace.keySet();
        var sKeys = s.keySet();
        var resAllKeys = resAllObj.keySet();
        map.put(Colours.BLUE, 0);
        map.put(Colours.PURPLE, 0);
        map.put(Colours.GREY, 0);
        map.put(Colours.YELLOW, 0);
        for(var pick : w)
            if(pick.resource() != Colours.WHITE) map.put(pick.resource(), pick.qnt());
        sKeys.forEach(key -> map.put(key, s.get(key) + map.get(key)));
        leaderKey.forEach(key -> map.put(key, leaderSpace.get(key) + map.get(key)));
        resAllKeys.forEach(key -> resAllObj.put(key, map.get(key)));
        listener.firePropertyChange(ALL, "", Colours.BLUE.name() + "::" + resAllObj.get(Colours.BLUE) + "::" + myId);
        listener.firePropertyChange(ALL, "", Colours.GREY.name() + "::" + resAllObj.get(Colours.GREY) + "::" + myId);
        listener.firePropertyChange(ALL, "", Colours.PURPLE.name() + "::" + resAllObj.get(Colours.PURPLE) + "::" + myId);
        listener.firePropertyChange(ALL, "", Colours.YELLOW.name() + "::" + resAllObj.get(Colours.YELLOW) + "::" + myId);
    }

    /**
     * Method takeFromStrongBox takes resources from the strongbox
     *
     * @param colour of type Colours
     */
    public void takeFromStrongBox(Colours colour){
        storeInStrongBox(new Pick[]{new Pick(colour, -1)});
    }

    /**
     * Method storeInStrongBox stores production result into StrongBox
     *
     * @param start of type Pick
     * @see #updateResAll()
     */
    public void storeInStrongBox(Pick[] start){
        for(Pick pick : start){
            if(pick.resource() == Colours.RED) moveFT(pick.qnt());
            else strongBoxObj.moveResources(pick);
        }
        updateResAll();
        listener.firePropertyChange(STRONGBOX, "", Colours.BLUE.name() + "::" + strongBoxObj.getStrongContent().get(Colours.BLUE) + "::" + myId);
        listener.firePropertyChange(STRONGBOX, "", Colours.GREY.name() + "::" + strongBoxObj.getStrongContent().get(Colours.GREY) + "::" + myId);
        listener.firePropertyChange(STRONGBOX, "", Colours.PURPLE.name() + "::" + strongBoxObj.getStrongContent().get(Colours.PURPLE) + "::" + myId);
        listener.firePropertyChange(STRONGBOX, "", Colours.YELLOW.name() + "::" + strongBoxObj.getStrongContent().get(Colours.YELLOW) + "::" + myId);
    }

    /**
     * Method moveFT moves the pawn on the Faith Track
     *
     * @param move of type int
     * @see FaithTrack #moveForward
     * @see #checkIfEndGame()
     */
    public void moveFT(int move){
        faithTrack.moveForward(move);
        checkIfEndGame();
        pcs.firePropertyChange("redCrossMove", "", getPositionFT());
        listener.firePropertyChange("faithTrack", "", getPositionFT() + "::" + myId);
    }

    public void produce(CardDev card){
        storeInStrongBox(card.getResTo());
    }

    public void checkVaticanReport(){
        faithTrack.getWinPntCard();
    }

    public void addToLeaderSpace(Colours colour){
        leaderSpace.put(colour, leaderSpace.get(colour) + 1);
    }

    public void takeFromLeaderSpace(Colours colour){
        leaderSpace.put(colour, leaderSpace.get(colour) - 1);
    }

    public void setWinner(String winner){
        pcs.firePropertyChange("winner", "", winner);
    }
}
