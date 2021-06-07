package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

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
    private Colours preference = Colours.WHITE;
    private LinkedList<Colours> bufferMarket;
    private LinkedList<Pick> bufferProd;
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

    public void setDiscount(Colours color){
        if(discount[0] == Colours.WHITE) discount[0] = color;
        else discount[1] = color;
    }

    public Colours[] getMarketAlt(){
        return marketAlt;
    }

    public void setMarketAlt(Colours color){
        if(marketAlt[0] == Colours.WHITE) marketAlt[0] = color;
        else{
            marketAlt[1] = color;
            pcs.firePropertyChange("marketAlt", "", marketAlt[1]);
        }
    }

    public boolean isProducible(){
        for(var i = 0; i < 3; i++){
            if(library.getCardsOfASlot(i) != null){
                return true;
            }
        }
        return false;
    }

    public Colours[] getProductAlt(){
        return productAlt;
    }

    public void setProductAlt(Colours color){
        if(productAlt[0] == Colours.WHITE) productAlt[0] = color;
        else productAlt[1] = color;
    }

    public void productionExtra(Colours color){
        productionAll();
        storeSB(new Pick[]{new Pick(color, 1)});
        faithTrack.moveForward(1);
    }

    public void productionAll(){
        bufferProd = new LinkedList<>();
        for(var i = 0; i < 3; i++){
            CardDev card = library.getCardOnTop(i);
            Pick[] res = card.getResFrom();
            Collections.addAll(bufferProd, res);
            pcs.firePropertyChange("productionBuffer", "", bufferProd);
            //remoteView.resourcesHandler(bufferProd);
            storeSB(card.getResTo());
        }
        checkIfEndGame();
    }

    public CardLeader[] getMyLeaderCards(){
        return myLeaderCards;
    }

    public void setMyLeaderCards(CardLeader[] cards){
        myLeaderCards = cards;
        pcs.firePropertyChange("firstOp", "", 0);
    }

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

    /*DA METTERE SUL CLIENT magari*/
    private void chooseResourcesW(List<Pick> res, Class<Warehouse> warehouseClass){
        /*la quantità di res è negativa*/
        for(Pick r : res){
            int i = warehouse.isThereThatRes(r.getResource());
            warehouse.moveResources(i, r);
        }
    }

    /*DA METTERE SUL CLIENT magari*/
    private void chooseResourcesSB(List<Pick> res, Class<StrongBox> strongBoxClass){
        /*la quantità di res è negativa*/
        for(Pick r : res){
            strongBox.moveResources(r);
        }
    }

    public int countWinPnt(){
        var win = 0;
        win += faithTrack.getWinPnt();
        win += library.getWinPntCardDev();
        win += getWinPntCardLeader();
        return win;
    }

    private int getWinPntCardLeader(){
        var win = 0;
        for(CardLeader myLeaderCard : myLeaderCards){
            if(myLeaderCard.getFlipped()) win += myLeaderCard.getWinPnt();
        }
        return win;
    }

    public void buyCardDev(CardDev card, int slot){
        var costCardDev = new LinkedList<Pick>();
        Collections.addAll(costCardDev, card.getCost());
        pcs.firePropertyChange("costCardDev", "", costCardDev);
        myDevCards.add(card);
        library.addCard(slot, card);
        checkIfEndGame();
    }

    private void checkIfEndGame(){
        if(myDevCards.size() == 7) endGame = true;
        if(faithTrack.getPosition() == 25) endGame = true;
        if(endGame) pcs.firePropertyChange("endGame", false, true);
    }

    public void marketResHandler(List<Colours> res){
        for(Colours colour : res)
            if(colour == Colours.WHITE && marketAlt[activeLeader] != Colours.WHITE){
                res.remove(colour);
                res.add(marketAlt[activeLeader]);
            }
        setBufferMarket(res);
    }

    public void setBufferMarket(List<Colours> res){
        bufferMarket = new LinkedList<>();
        bufferMarket = (LinkedList<Colours>)res;
        pcs.firePropertyChange("marketBuffer", "", bufferMarket);
    }

    public void resHandler(Colours res){
        /*attiva un pezzo di viewer che chiede a che lvl di warehouse infilare quelle risorsa*/
    }

    public void setPreference(){
        preference = Colours.WHITE;
        pcs.firePropertyChange("preference", "", preference);
    }

    public void resolveLeaderConflict(boolean i){
        if(i) activeLeader = 0;
        else activeLeader = 1;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }

    public void createStatus(){
        var w = new Colours[3][3];
        var list = warehouse.checkStatus();
        for(var i = 0; i < 3; i++){
            for(var j = 0; j < 3; j++){
                if(j < list.get(i).getQnt()){
                    w[i][j] = list.get(i).getResource();
                }
                w[i][j] = null;
            }
        }
        var status = new StatusBoard(myDevCards, w, faithTrack.getPosition(), strongBox.getStrongContent(), library.visibleCards().toArray(new CardDev[0]), resAll, myLeaderCards);
        pcs.firePropertyChange("boardStatus", "", status);
    }

    public void moveFT(int move){
        faithTrack.moveForward(move);
        pcs.firePropertyChange("redCrossMove", "", faithTrack.getPosition());
        checkIfEndGame();
    }

    public void addLeaderSpace(Colours special){
        warehouse.addLeaderSpace(special);
    }

    public int getPositionFT(){
        return faithTrack.getPosition();
    }

    public void rearrangeWarehouse(int first, int second){
        warehouse.lvlExchange(first, second);
    }

    public List<Pick> checkWarehouseStatus(){
        return warehouse.getWareContent();
    }

    public boolean checkWarehouseAdd(Pick pick, int lvl){
        return warehouse.checkAdd(lvl, pick);
    }

    public List<ArrayList<CardDev>> checkLibraryStatus(){
        return library.getSlot();
    }

    public Map<Colours, Integer> checkStrongBoxStatus(){
        return strongBox.getStrongContent();
    }

    public void takeFromWarehouse(Colours colour){
        var lvl = warehouse.isThereThatRes(colour);
        storeW(lvl, new Pick[]{new Pick(colour, -1)});
    }

    public void storeW(int lvl, Pick[] start){
        for(var pick : start){
            warehouse.moveResources(lvl, pick);
        }
        updateResAll();
    }

    public void updateResAll(){
        var w = warehouse.getWareContent();
        var s = strongBox.getStrongContent();
        var map = new EnumMap<Colours, Integer>(Colours.class);
        map.put(Colours.BLUE, 0);
        map.put(Colours.PURPLE, 0);
        map.put(Colours.GREY, 0);
        map.put(Colours.YELLOW, 0);
        for(var pick : w)
            if(pick.getResource() != Colours.WHITE) map.put(pick.getResource(), pick.getQnt());
        s.keySet().forEach(key -> map.put(key, s.get(key) + map.get(key)));
        resAll.keySet().forEach(key -> resAll.put(key, map.get(key)));
    }

    public void takeFromStrongBox(Colours colour){
        storeSB(new Pick[]{new Pick(colour, -1)});
    }

    public void storeSB(Pick[] start){
        for(Pick pick : start){
            strongBox.moveResources(pick);
        }
        updateResAll();
    }
}
