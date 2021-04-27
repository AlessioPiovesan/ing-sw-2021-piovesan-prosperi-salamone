package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import it.polimi.ingsw.server.model_server.carte.CardPopeFav;

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
    String nickname;
    Colours discount = Colours.WHITE;
    Colours marketAlt = Colours.WHITE;
    Colours productAlt = Colours.WHITE;

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

    public void setMyLeaderCards(CardLeader[] choice) { /* DA IMPLEMENTARE */
        myLeaderCards[0] = choice[1];
        myLeaderCards[1] = choice[3];
    }

    public CardLeader[] getMyLeaderCards() {
        return myLeaderCards;
    }

    public int checkDiscardedResources(List<Colours> discarded) {
        return discarded.size();
    }

    public void setDiscount(Colours color){
        discount = color;
    }

    public void setProductAlt(Colours color){
        productAlt = color;
    }

    public void productionAll() {
        for (var i = 0; i < 3; i++) {
            CardDev card = library.getCardOnTop(i);
            Pick[] res = card.getResFrom();
            store(res);
        }
        if (productAlt != Colours.WHITE){
            /*prima chiede da dove prendere productAlt*/
            /*poi chiede la risorsa a scelta*/
            faithTrack.setPosition(1);
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void store(Pick[] start) {
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
        win += faithTrack.getWinPnt();
        win += library.getWinPntCardDev();
        win += getWinPntCardLeader();
        return win;
    }

    public void buyCardDev(CardDev card) {
        /*seleziona le risorse sia da warehouse che da strong box*/
        /*aggiungi alle carte che ha il giocatore*/
        /*controlla se c'è lo sconto*/
    }

    public void setMyPFCards(CardPopeFav[] cards) { /* DA IMPLEMENTARE */
        faithTrack.setCard(cards);
    }

    public void setMarketAlt(Colours colour){
        marketAlt = colour;
    }

    public void marketResHandler(LinkedList<Colours> res){
        for (Colours colours:res)
            if(colours == Colours.WHITE) colours = marketAlt;
        /*attiva un pezzo di view che chiede quali tenere*/
    }

    public void resHandler(Colours res){
        /*attiva un pezzo di view che chiede a che lvl di warehouse infilare quelle risorsa*/
    }

}
