package it.polimi.ingsw.server.model_server.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.viewer_server.RemoteView;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class Board {
    private static final LinkedList<CardDev> myDevCards = new LinkedList<>();
    private final Warehouse warehouse;
    private final FaithTrack faithTrack;
    private final StrongBox strongBox;
    private final Library library;
    private CardLeader[] myLeaderCards = new CardLeader[2];
    private CardLeader[] temporaryLeaderCards = new CardLeader[4];
    private final EnumMap<Colours, Integer> resAll;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    int idD;
    int activeLeader = 0;
    String nickname;
    Colours preference = Colours.WHITE;
    LinkedList<Colours> buffer;
    Colours[] discount = {Colours.WHITE, Colours.WHITE};
    Colours[] marketAlt = {Colours.WHITE, Colours.WHITE};
    Colours[] productAlt = {Colours.WHITE, Colours.WHITE};

    RemoteView remoteView;

    public Board(int id, Warehouse warehouse, StrongBox strongBox, FaithTrack faithTrack, Library library, RemoteView remoteView) {
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
        this.remoteView = remoteView;
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

    public void setMarketAlt(Colours color) {
        if (marketAlt[0] == Colours.WHITE)
            marketAlt[0] = color;
        else {
            marketAlt[1] = color;
            pcs.firePropertyChange("marketAlt", "", marketAlt[1]);
        }
    }

    public void setBuffer(List<Colours> res) {
        buffer = (LinkedList<Colours>) res;
        pcs.firePropertyChange("buffer", "", buffer);
    }

    public void productionExtra(Colours color) {
        productionAll();
        storeSB(new Pick[]{new Pick(color, 1)});
        faithTrack.moveForward(1);
    }

    public void productionAll() {
        for (var i = 0; i < 3; i++) {
            CardDev card = library.getCardOnTop(i);
            Pick[] res = card.getResFrom();
            storeSB(res);
        }
    }

    public CardLeader[] getMyLeaderCards() {
        return myLeaderCards;
    }

    public void setTemporaryLeaderCards(CardLeader[] leaders) {
        temporaryLeaderCards = leaders;
        pcs.firePropertyChange("temporaryLeaderCards", "", temporaryLeaderCards);
        remoteView.scegliLeCarteLeaderAllInizioDomanda(temporaryLeaderCards);
    }

    public void setMyLeaderCards(CardLeader[] cards){
        myLeaderCards = cards;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void storeSB(Pick[] start) {
        for (Pick pick : start) {
            strongBox.moveResources(pick);
        }
        updateResAll();
    }

    public void storeW(int lvl, Pick[] start) {
        for (Pick pick : start) {
            warehouse.moveResources(lvl, pick);
        }
        updateResAll();
    }

    public void updateResAll() { /* DA IMPLEMENTARE magari che un ActionListener fa aggiornare questa variabile*/
        List<Pick> w = warehouse.checkStatus();
        for (Pick pick : w)
            resAll.replace(pick.getResource(), pick.getQnt() + resAll.get(pick.getResource()));
        for (Colours c : Arrays.asList(Colours.BLUE, Colours.GREY, Colours.PURPLE, Colours.YELLOW))
            resAll.replace(c, resAll.get(c) + strongBox.getStrongContent().get(c));
        pcs.firePropertyChange("resAll", "", resAll);
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
        if (myDevCards.size() == 7) System.out.println("Fine partita?");
    }

    public void marketResHandler(List<Colours> res) {
        for (Colours colours : res)
            if (colours == Colours.WHITE && marketAlt[activeLeader] != Colours.WHITE) {
                colours = marketAlt[activeLeader];
            }
        setBuffer(res);
    }

    public void resHandler(Colours res) {
        /*attiva un pezzo di view che chiede a che lvl di warehouse infilare quelle risorsa*/
    }

    public int checkDiscardedResources(List<Colours> discarded) {
        return discarded.size();
    }

    public void setPreference() {
        preference = Colours.WHITE;
        pcs.firePropertyChange("preference", "", preference);
        remoteView.getRisorsaPreferita();
    }

    public void resolveLeaderConflict(int i) {
        activeLeader = i;
    }


}
