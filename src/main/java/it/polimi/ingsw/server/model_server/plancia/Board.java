package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;

import java.util.*;

public class Board {
    private static final LinkedList<CardDev> myDevCards = new LinkedList<>();
    private final Warehouse warehouse;
    private final FaithTrack faithTrack;
    private final EnumMap<Colours, Integer> resAll;
    private final CardLeader[] myLeaderCards = new CardLeader[2];
    protected StrongBox strongBox;
    protected Library library;
    int idD;
    String nickname;
    Colours[] discount = {Colours.WHITE, Colours.WHITE};
    Colours[] marketAlt = {Colours.WHITE, Colours.WHITE};
    Colours[] productAlt = {Colours.WHITE, Colours.WHITE};

    public Board(int id, Warehouse warehouse, StrongBox strongBox, FaithTrack faithTrack, Library library) {
        this.idD = id;
        /* Warehouse creation for a single Board */
        this.warehouse = warehouse;
        /* Strongbox creation for a single Board */
        this.strongBox = strongBox;
        /* Faith Track creation for a single board */
        this.faithTrack = faithTrack;
        /* ________ creation for a single board */
        this.library = library;
        resAll = new EnumMap<>(Colours.class);
        resAll.put(Colours.BLUE, 0);
        resAll.put(Colours.YELLOW, 0);
        resAll.put(Colours.GREY, 0);
        resAll.put(Colours.PURPLE, 0);
    }

    public CardLeader[] getMyLeaderCards() {
        return myLeaderCards;
    }

    public void setMyLeaderCards(CardLeader[] choice) { /* DA IMPLEMENTARE */
        myLeaderCards[0] = choice[1];
        myLeaderCards[1] = choice[3];
    }

    public int checkDiscardedResources(List<Colours> discarded) {
        return discarded.size();
    }

    public void setDiscount(Colours color) {
        if (discount[0] == Colours.WHITE)
            discount[0] = color;
        else discount[1] = color;
    }

    public void setProductAlt(Colours color) {
        if (productAlt[0] == Colours.WHITE)
            productAlt[0] = color;
        else productAlt[1] = color;
    }

    public void productionAll() {
        for (var i = 0; i < 3; i++) {
            CardDev card = library.getCardOnTop(i);
            Pick[] res = card.getResFrom();
            store(res);
        }
        /*DA IMPLEMENTARE prouzione plancia, quindi quella che chiede quale risorsa*/
        for (Colours colours : productAlt) {
            if (colours != Colours.WHITE) {
                /*prima chiede da dove prendere productAlt*/
                /*poi chiede la risorsa a scelta*/
                faithTrack.moveForward(1);
            }
        }

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void store(Pick[] start) {
        for (Pick pick : start) {
            strongBox.moveResources(pick);
        }
    }

    public Map<Colours, Integer> getResAll() {
        return resAll;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    /*DA METTERE SUL CLIENT magari*/
    private void chooseResourcesW(List<Pick> res, Class<Warehouse> warehouseClass) {
        /*la quantità di res è negativa*/
        for (Pick r : res) {
            int i = warehouse.isThereThatRes(r.getResource());
            warehouse.moveResources(i, r);
        }
    }

    /*DA METTERE SUL CLIENT magari*/
    private void chooseResourcesSB(List<Pick> res, Class<StrongBox> strongBoxClass) {
        /*la quantità di res è negativa*/
        for (Pick r : res) {
            strongBox.moveResources(r);
        }
    }

    public void updateResAll() {
        List<Pick> w = warehouse.checkStatus();
        for (Pick pick : w)
            resAll.replace(pick.getResource(), pick.getQnt() + resAll.get(pick.getResource()));
        for (Colours c : Arrays.asList(Colours.BLUE, Colours.GREY, Colours.PURPLE, Colours.YELLOW))
            resAll.replace(c, resAll.get(c) + strongBox.getComp().get(c));
    }

    private int getWinPntCardLeader() {
        var win = 0;
        for (CardLeader myLeaderCard : myLeaderCards) {
            if (myLeaderCard.getFlipped()) win += myLeaderCard.getWinPnt();
        }
        return win;
    }

    public int countWinPnt() {
        var win = 0;
        win += faithTrack.getWinPnt();
        win += library.getWinPntCardDev();
        win += getWinPntCardLeader();
        return win;
    }

    public void buyCardDev(CardDev card) {
        /*seleziona le risorse sia da warehouse che da strong box*/
        /*aggiungi alle carte che ha il giocatore*/
        /*controlla se c'è lo sconto*/
        myDevCards.add(card);
    }

    public void setMarketAlt(Colours color) {
        if (marketAlt[0] == Colours.WHITE)
            marketAlt[0] = color;
        else marketAlt[1] = color;
    }

    public void marketResHandler(List<Colours> res) {
        for (Colours colours : res)
            if (colours == Colours.WHITE) {
                for (Colours value : marketAlt) {
                    if (value != Colours.WHITE) {
                        /*si chiede al giocatore che fare di questa biglia bianca*/
                        Colours choice = Colours.RED; /* DA IMPLEMENTARE */
                        colours = choice;
                    }
                }
            }
        /*attiva un pezzo di view che chiede quali tenere*/
        /*DA IMPLEMENTARE*/
    }

    public void resHandler(Colours res) {
        /*attiva un pezzo di view che chiede a che lvl di warehouse infilare quelle risorsa*/
    }

}
