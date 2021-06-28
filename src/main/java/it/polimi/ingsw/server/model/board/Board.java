package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Json;
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
    private final Warehouse warehouse;
    private final FaithTrack faithTrack;
    private final StrongBox strongBox;
    private final Library library;
    private final EnumMap<Colours, Integer> resAll;
    private final PropertyChangeSupport pcs;
    private final PropertyChangeSupport listener;
    private final int idD;
    private final Colours[] discount;
    private final Colours[] marketAlt;
    private final Colours[] productAlt;
    private final Map<Colours, Integer> leaderSpace;
    private Integer gameID;
    private boolean endGame;
    private int activeLeader = 0;
    private String nickname;
    private CardLeader[] myLeaderCards = new CardLeader[2];
    private boolean ready;

    public Board(int id, Warehouse warehouse, StrongBox strongBox, FaithTrack faithTrack, Library library){
        endGame = false;
        this.idD = id;
        /* Warehouse creation for a model Board */
        this.warehouse = warehouse;
        /* Strongbox creation for a model Board */
        this.strongBox = strongBox;
        /* Faith Track creation for a model board */
        this.faithTrack = faithTrack;
        /* ________ creation for a model board */
        this.library = library;
        resAll = new EnumMap<>(Colours.class);
        resAll.put(Colours.BLUE, 0);
        resAll.put(Colours.YELLOW, 0);
        resAll.put(Colours.GREY, 0);
        resAll.put(Colours.PURPLE, 0);
        discount = new Colours[]{Colours.WHITE, Colours.WHITE};
        marketAlt = new Colours[]{Colours.WHITE, Colours.WHITE};
        productAlt = new Colours[]{Colours.WHITE, Colours.WHITE};
        leaderSpace = new EnumMap<>(Colours.class);
        ready = false;
        pcs = new PropertyChangeSupport(this);
        listener = new PropertyChangeSupport(this);
    }

    public void somethingIsWrong(String s){
        pcs.firePropertyChange("wrong", "", s);
    }

    public void ready(){
        ready = !ready;
        pcs.firePropertyChange("nextAction", "", ready);
    }

    public int getIdD(){
        return idD;
    }

    public Integer getGameID(){
        return gameID;
    }

    public void setGameID(Integer gameID){
        this.gameID = gameID;
    }

    public Colours[] getDiscount(){
        return discount;
    }

    /**
     * Method setDiscount is a consequence of one of the Leader Card's powers.
     * Discounts a chosen resource.
     *
     * @param color of type Colours - resource to be discounted
     */
    public void setDiscount(Colours color){
        if(discount[0] == Colours.WHITE) discount[0] = color;
        else discount[1] = color;
        listener.firePropertyChange("discount0", "", discount[0]);
        listener.firePropertyChange("discount1", "", discount[1]);
    }

    public Colours[] getMarketAlt(){
        return marketAlt;
    }

    /**
     * Method setMarketAlt is a consequence of one of the Leader Card's powers.
     * Transforms a white marble into a coloured one.
     *
     * @param color of type Colours - resource
     */
    public void setMarketAlt(Colours color){
        if(marketAlt[0] == Colours.WHITE) marketAlt[0] = color;
        else marketAlt[1] = color;
        listener.firePropertyChange("marketAlt0", "", marketAlt[0]);
        listener.firePropertyChange("marketAlt1", "", marketAlt[1]);

    }

    public Colours[] getProductAlt(){
        return productAlt;
    }

    /**
     * Method setProductAlt is a consequence of one of the Leader Card's powers.
     * Adds a production activity by turning a resource into a preferred one and a Faith Point.
     *
     * @param color of type Colours - resource
     */
    public void setProductAlt(Colours color){
        if(productAlt[0] == Colours.WHITE) productAlt[0] = color;
        else productAlt[1] = color;
        listener.firePropertyChange("productAlt0", "", productAlt[0]);
        listener.firePropertyChange("productAlt1", "", productAlt[1]);
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
        return resAll;
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
        return win;
    }

    /**
     * Method getWinPntCardLeader counts win points from Leader Cards
     *
     * @return win of type int - win points
     */
    private int getWinPntCardLeader(){
        var win = 0;
        for(CardLeader myLeaderCard : myLeaderCards){
            if(myLeaderCard.getFlipped()) win += myLeaderCard.getWinPnt();
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
        Collections.addAll(costCardDev, card.getCost());
        myDevCards.add(card);
        checkIfEndGame();
        library.addCard(slot, card);
        pcs.firePropertyChange("costCardDev", "", costCardDev.toArray());
        listener.firePropertyChange("library", "", Json.toJson(card) + "::" + slot);


    }

    /**
     * Method checkIfEndGame is the method that checks if a game has come to an end.
     * a game can end by either a player reaching the end of the Faith Track
     * or if a player buys their seventh Development Card.
     */
    private void checkIfEndGame(){
        if(myDevCards.size() == 7) endGame = true;
        if(faithTrack.getPosition() == 24) endGame = true;
        if(endGame) pcs.firePropertyChange("endGame", false, true);
    }

    /**
     * Method marketResHandler transforms the marbles into resources whenever it has to.
     *
     * @param res of type Colours - resources
     * @see #setBufferMarket(List)
     */
    public void marketResHandler(List<Colours> res){
        var newRes = new LinkedList<Colours>();
        for(Colours colour : res)
            if(colour == Colours.WHITE && marketAlt[activeLeader] != Colours.WHITE)
                newRes.add(marketAlt[activeLeader]);
            else newRes.add(colour);

        setBufferMarket(newRes);
    }

    /**
     * Method setBufferMarket are the resources ready to be placed in the Warehouse
     *
     * @param res of type Colours - resources
     */
    public void setBufferMarket(List<Colours> res){
        var bufferMarket = (LinkedList<Colours>)res;
        pcs.firePropertyChange("marketBuffer", "", bufferMarket.toArray());
    }

    public void setPreference(){
        Colours preference = Colours.WHITE;
        pcs.firePropertyChange("preference", "", preference);
    }

    public void resolveLeaderConflict(boolean i){
        if(i) activeLeader = 0;
        else activeLeader = 1;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public void addListener(PropertyChangeListener listener){
        this.listener.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){pcs.removePropertyChangeListener(listener);}

    /**
     * @param special resource type that you can add in the extra space
     */
    public void addLeaderSpace(Colours special){
        leaderSpace.put(special, 0);
        listener.firePropertyChange("leaderSpace", "", special);
    }

    /**
     * @return position
     * @see FaithTrack#getPosition
     */
    public int getPositionFT(){
        return faithTrack.getPosition();
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
        warehouse.lvlExchange(first, second);
        listener.firePropertyChange("warehouse", "", warehouse.checkStatus().toArray());
    }

    /**
     * Returns a list that contains the content of the Warehouse
     *
     * @return Pick
     * @see Warehouse#getWareContent()
     */
    public List<Pick> checkWarehouseStatus(){
        return warehouse.getWareContent();
    }

    /**
     * @param pick of type Pick
     * @param lvl  of type int
     * @return boolean
     * @see Warehouse#checkAdd(int, Pick)
     */
    public boolean checkWarehouseAdd(Pick pick, int lvl){
        return warehouse.checkAdd(lvl, pick);
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
        return strongBox.getStrongContent();
    }

    /**
     * Method takeFromWarehouse takes resources from warehouse
     *
     * @param colour of type Colours
     * @see #storeW(int, Pick[])
     * @see Warehouse#isThereThatRes(Colours)
     */
    public void takeFromWarehouse(Colours colour){
        var lvl = warehouse.isThereThatRes(colour);
        storeW(lvl, new Pick[]{new Pick(colour, -1)});
    }

    /**
     * Method storeW stores Resources in the Warehouse
     *
     * @param lvl   of type int - Warehouse's shelf
     * @param start of type Pick
     * @see #updateResAll()
     * @see Warehouse#moveResources(int, Pick)
     */
    public void storeW(int lvl, Pick[] start){
        for(var pick : start){
            warehouse.moveResources(lvl, pick);
        }
        updateResAll();
        listener.firePropertyChange("warehouse", "", warehouse.checkStatus().toArray());
    }

    /**
     * Method updateResAll updates all of the resources in the board
     *
     * @see Warehouse#getWareContent()
     * @see StrongBox#getStrongContent()
     */
    public void updateResAll(){
        var w = warehouse.getWareContent();
        var s = strongBox.getStrongContent();
        var map = new EnumMap<Colours, Integer>(Colours.class);
        var leaderKey = leaderSpace.keySet();
        var sKeys = s.keySet();
        var resAllKeys = resAll.keySet();
        map.put(Colours.BLUE, 0);
        map.put(Colours.PURPLE, 0);
        map.put(Colours.GREY, 0);
        map.put(Colours.YELLOW, 0);
        for(var pick : w)
            if(pick.resource() != Colours.WHITE) map.put(pick.resource(), pick.qnt());
        sKeys.forEach(key -> map.put(key, s.get(key) + map.get(key)));
        leaderKey.forEach(key -> map.put(key, leaderSpace.get(key) + map.get(key)));
        resAllKeys.forEach(key -> resAll.put(key, map.get(key)));
        listener.firePropertyChange("resAll", "", resAll);
    }

    public int checkFT(){return faithTrack.getPosition();}

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
            else strongBox.moveResources(pick);
        }
        updateResAll();
        listener.firePropertyChange("strongBox", "", strongBox.getStrongContent());
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
        pcs.firePropertyChange("redCrossMove", "", faithTrack.getPosition());
        listener.firePropertyChange("faithTrack", "", faithTrack.getPosition());
    }

    public void produce(CardDev card){
        storeInStrongBox(card.getResTo());
    }

    public void checkVaticanReport(){
        faithTrack.getWinPntCard();
    }
}
