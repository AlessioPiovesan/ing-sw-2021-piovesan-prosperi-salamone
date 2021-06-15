package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * class Board describes a player's personal Board.
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public class Board{

    private static final LinkedList<CardDev> myDevCards = new LinkedList<>();
    private final Warehouse warehouse;
    private final FaithTrack faithTrack;
    private final StrongBox strongBox;
    private final Library library;
    private final EnumMap<Colours, Integer> resAll;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final int idD;
    private final Colours[] discount;
    private final Colours[] marketAlt;
    private final Colours[] productAlt;
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
        ready = false;
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

    public Colours[] getDiscount(){
        return discount;
    }

    /**
     * method setDiscount is a consquence of one of the Leader Card's powers.
     * Discounts a chosen resource.
     * @param color of type Colours - resource to be discounted
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void setDiscount(Colours color){
        if(discount[0] == Colours.WHITE) discount[0] = color;
        else discount[1] = color;
    }

    public Colours[] getMarketAlt(){
        return marketAlt;
    }

    /**
     * method setMarketAlt is a consequence of one of the Leader Card's powers.
     * Transforms a white marble into a coloured one.
     * @param color of type Colours - resource
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void setMarketAlt(Colours color){
        if(marketAlt[0] == Colours.WHITE) marketAlt[0] = color;
        else{
            marketAlt[1] = color;
            pcs.firePropertyChange("marketAlt", "", marketAlt[1]);
        }
    }

    public Colours[] getProductAlt(){
        return productAlt;
    }

    /**
     * method setProductAlt is a consequence of one of the Leader Card's powers.
     * Adds a production activity by turning a resource into a preferred one and a Faith Point.
     * @param color of type Colours - resource
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */

    public void setProductAlt(Colours color){
        if(productAlt[0] == Colours.WHITE) productAlt[0] = color;
        else productAlt[1] = color;
    }

    public CardLeader[] getMyLeaderCards(){
        return myLeaderCards;
    }

    /**
     * method setMyLeaderCards saves the two chosen Leader Cards.
     * @param cards of type CardLeader - Leader Cards
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void setMyLeaderCards(CardLeader[] cards){
        myLeaderCards = cards;
        pcs.firePropertyChange("firstOp", "", 0);
    }
//scelta di due tra quattro

    /**
     * method setTemporaryLeaderCards is the method that chooses tow of the 4 leader cards presented to the user.
     * @param leaders of type CardLeader - Leader cards
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
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
     * method countWinPnt counts the Points to win a game.
     * @return win of type int - win points
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public int countWinPnt(){
        var win = 0;
        win += faithTrack.getWinPnt();
        win += library.getWinPntCardDev();
        win += getWinPntCardLeader();
        return win;
    }

    /**
     * method getWinPntCardLeader counts win points from Leader Cards
     * @return win of type int - win points
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    private int getWinPntCardLeader(){
        var win = 0;
        for(CardLeader myLeaderCard : myLeaderCards){
            if(myLeaderCard.getFlipped()) win += myLeaderCard.getWinPnt();
        }
        return win;
    }

    /**
     * method buyCardDev is the method used to buy Development Cards using resources
     * @see #checkIfEndGame()
     * @param card of type CardDev - Development card that the player wants to buy
     * @param slot of type int - slot where the user wants to place the card
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void buyCardDev(CardDev card, int slot){
        var costCardDev = new LinkedList<Pick>();
        Collections.addAll(costCardDev, card.getCost());
        myDevCards.add(card);
        checkIfEndGame();
        library.addCard(slot, card);
        pcs.firePropertyChange("costCardDev", "", costCardDev);


    }

    /**
     * method checkIfEndGame is the method that checks if a game has come to an end.
     * a game can end by either a player reaching the end of the Faith Track
     * or if a player buys their seventh Development Card.
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    private void checkIfEndGame(){
        if(myDevCards.size() == 7) endGame = true;
        if(faithTrack.getPosition() == 24) endGame = true;
        if(endGame) pcs.firePropertyChange("endGame", false, true);
    }

    /**
     * method marketResHandler transforms the marbles into resources whenever it has to.
     * @see #setBufferMarket(List)
     * @param res of type Colours - resources
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void marketResHandler(List<Colours> res){
        for(Colours colour : res)
            if(colour == Colours.WHITE && marketAlt[activeLeader] != Colours.WHITE){
                res.remove(colour);
                res.add(marketAlt[activeLeader]);
            }
        setBufferMarket(res);
    }

    /**
     * method setBufferMarket are the resources ready to be placed in the Warehouse
     * @param res of type Colours - resources
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void setBufferMarket(List<Colours> res){
        var bufferMarket = (LinkedList<Colours>)res;
        pcs.firePropertyChange("marketBuffer", "", bufferMarket);
    }

    public void resHandler(Colours res){
        /*attiva un pezzo di viewer che chiede a che lvl di warehouse infilare quelle risorsa*/
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

    public void removePropertyChangeListener(PropertyChangeListener listener){pcs.removePropertyChangeListener(listener);}

    /**
     * method createStatus creates the status of the board.
     * @see StatusBoard
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void createStatus(){
        var pseudoWarehouse = new Colours[3][3];
        var list = warehouse.checkStatus();
        for(var i = 0; i < 3; i++){
            for(var j = 0; j < 3; j++){
                if(j < list.get(i).getQnt()){
                    var thisDepotPick = list.get(i);
                    pseudoWarehouse[i][j] = thisDepotPick.getResource();
                }
                pseudoWarehouse[i][j] = null;
            }
        }
        var status = new StatusBoard(pseudoWarehouse, faithTrack.getPosition(), strongBox.getStrongContent(), library.visibleCards(), resAll, myLeaderCards);
        pcs.firePropertyChange("boardStatus", "", status);
    }

    /**
     * method moveFT moves the pawn on the Faith Track
     * @see FaithTrack #moveForward
     * @see #checkIfEndGame()
     * @param move of type int
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void moveFT(int move){
        faithTrack.moveForward(move);
        checkIfEndGame();
        pcs.firePropertyChange("redCrossMove", "", faithTrack.getPosition());
    }

    /**
     * @see Warehouse#addLeaderSpace(Colours)
     * @param special
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void addLeaderSpace(Colours special){
        warehouse.addLeaderSpace(special);
    }

    /**
     * @see FaithTrack#getPosition
     * @return position
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public int getPositionFT(){
        return faithTrack.getPosition();
    }

    /**
     * @see Warehouse#lvlExchange
     * @param first of type int
     * @param second of type int
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void rearrangeWarehouse(int first, int second){
        warehouse.lvlExchange(first, second);
    }

    /**
     * @see Warehouse#getWareContent()
     * @return Pick
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public List<Pick> checkWarehouseStatus(){
        return warehouse.getWareContent();
    }

    /**
     * @see Warehouse#checkAdd(int, Pick)
     * @param pick of type Pick
     * @param lvl of type int
     * @return boolean
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public boolean checkWarehouseAdd(Pick pick, int lvl){
        return warehouse.checkAdd(lvl, pick);
    }

    /**
     * @see Library#getSlot()
     * @return CardDev
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public List<ArrayList<CardDev>> checkLibraryStatus(){
        return library.getSlot();
    }

    /**
     * @see StrongBox#getStrongContent()
     * @return Map
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public Map<Colours, Integer> checkStrongBoxStatus(){
        return strongBox.getStrongContent();
    }

    /**
     * method takeFromWarehouse takes resources from warehouse
     * @see #storeW(int, Pick[])
     * @see Warehouse#isThereThatRes(Colours)
     * @param colour of type Colours
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void takeFromWarehouse(Colours colour){
        var lvl = warehouse.isThereThatRes(colour);
        storeW(lvl, new Pick[]{new Pick(colour, -1)});
    }

    /**
     * method storeW stores Resources in the Warehouse
     * @see #updateResAll()
     * @see Warehouse#moveResources(int, Pick)
     * @param lvl of type int - Warehouse's depot
     * @param start of type Pick
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void storeW(int lvl, Pick[] start){
        for(var pick : start){
            warehouse.moveResources(lvl, pick);
        }
        updateResAll();
    }

    /**
     * method updateResAll updates all of the resources in the board
     * @see Warehouse#getWareContent()
     * @see StrongBox#getStrongContent()
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void updateResAll(){
        var w = warehouse.getWareContent();
        var s = strongBox.getStrongContent();
        var map = new EnumMap<Colours, Integer>(Colours.class);
        var sKeys = s.keySet();
        var resAllKeys = resAll.keySet();
        map.put(Colours.BLUE, 0);
        map.put(Colours.PURPLE, 0);
        map.put(Colours.GREY, 0);
        map.put(Colours.YELLOW, 0);
        for(var pick : w)
            if(pick.getResource() != Colours.WHITE) map.put(pick.getResource(), pick.getQnt());
        sKeys.forEach(key -> map.put(key, s.get(key) + map.get(key)));
        resAllKeys.forEach(key -> resAll.put(key, map.get(key)));
    }

    public int checkFT(){return faithTrack.getPosition();}

    /**
     * method takeFromStrongBox takes resources from the strongbox
     * @param colour of type Colours
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void takeFromStrongBox(Colours colour){
        storeSB(new Pick[]{new Pick(colour, -1)});
    }

    /**
     * method storeSB stores production result into StrongBox
     * @see StrongBox#moveResources(Pick)
     * @see #updateResAll()
     * @param start of type Pick
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void storeSB(Pick[] start){
        for(Pick pick : start){
            strongBox.moveResources(pick);
        }
        updateResAll();
    }

    public void produce(CardDev card){
        storeSB(card.getResTo());
    }

    public void checkVaticanReport(){
        faithTrack.getWinPntCard();
    }
}
