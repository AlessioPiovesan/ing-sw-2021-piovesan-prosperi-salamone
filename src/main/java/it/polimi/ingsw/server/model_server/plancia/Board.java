package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;

import java.util.*;

public class Board {
    private static final LinkedList<CardDev> myDevCards = new LinkedList<>();
    private CardLeader[] myLeaderCards = new CardLeader[2];
    private final Warehouse warehouse;
    private final FaithTrack faithTrack;
    private final EnumMap<Colours, Integer> resAll;
    protected StrongBox strongBox;
    protected Library library;
    int idD;

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

    public void setMyLeaderCards(CardLeader[] choice) {
        myLeaderCards = choice;
    }

    public CardLeader[] getMyLeaderCards() {
        return myLeaderCards;
    }

    public int checkDiscardedResources(List<Colours> discarded) {
        return discarded.size();
    }

    public void productionAll(Pick[] start) {
        for (var i = 0; i < 3; i++) {
            CardDev card = library.getCardOnTop(i);
            Pick[] res = card.getResFrom();
            production(res);
        }
    }

    public void production(Pick[] start) {
        for (var i = 0; i < start.length; i++) {
            strongBox.moveResources(start[i]);
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

    private LinkedList<Pick> chooseResources() { /* DA IMPLEMENTARE */
        LinkedList<Pick> choice = new LinkedList<>();
        var stop = true;
        /*Mostra tutte le risorse del giocatore*/
        for (Colours colours : Colours.values())
            System.out.println(getResAll().get(colours));
        /* DA IMPLEMENTARE */
        while (stop) {
            /*Il giocatore sceglie tutte le risorse*/
            choice.add(new Pick(Colours.BLUE, 1));
            if (/*condizione che dice che la scelta è stata fatta ed è conclusa*/ true) stop = false;
        }
        return choice;
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
        win += faithTrack.getWinPntPosition();
        win += faithTrack.getWinPntCard();
        win += library.getWinPntCardDev();
        win += getWinPntCardLeader();
        return win;
    }

    public void buyCardDev(CardDev card) {
        /*seleziona le risorse sia da warehouse che da strong box*/
        /*aggiungi alle carte che ha il giocatore*/
    }
}
