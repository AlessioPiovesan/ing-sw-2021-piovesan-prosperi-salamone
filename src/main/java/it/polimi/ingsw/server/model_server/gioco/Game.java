package it.polimi.ingsw.server.model_server.gioco;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import it.polimi.ingsw.server.model_server.carte.CardPopeFav;
import it.polimi.ingsw.server.model_server.plancia.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {

    int id;
    private final Board[] b = new Board[4]; /* c'è DA IMPLEMENTARE l'associazione con i socket dei vari player*/
    private Market market;
    private Supply supply;
    private static int nGamers = 4;
    int playingPlayer;
    private final Random random = new Random();
    private final ArrayList<CardDev> cardDevArrayDeck = new ArrayList<>();
    private final ArrayList<CardLeader> cardLeaderDeck = new ArrayList<>();
    private final ArrayList<Integer> playableLeaderCards = new ArrayList<>();
    private final ArrayList<CardPopeFav> cardPopeDeck = new ArrayList<>();

    public Game(Market newMarket, Supply newSupply) {
        /* Board creation for each player */
        for (var i = 0; i < nGamers; i++) {
            var warehouse = new Warehouse();
            var strongBox = new StrongBox();
            var faithTrack = new FaithTrack();
            var library = new Library();
            b[i] = new Board(i, warehouse, strongBox, faithTrack, library);
        }
        /* Market creation for each Game */
        market = newMarket;
        /* Supply creation for each Game */
        supply = newSupply;
        /* Card cataloging for each Game */
        cardDevArrayDeck.addAll(Arrays.asList(CardDev.values()));
        cardLeaderDeck.addAll(Arrays.asList(CardLeader.values()));
        cardPopeDeck.addAll(Arrays.asList(CardPopeFav.values()));
        /*...*/
    }

    public Board getSingleB(int i) {
        return b[i];
    }

    public Board[] getAllB() {
        return b;
    }

    public Market getMarket(){
        return market;
    }

    public int getPlayingPlayer() {
        return playingPlayer;
    }

    public void setPlayingPlayer(int playingPlayer) {
        this.playingPlayer = playingPlayer;
        notify();
    }

    public void myLeaderCardsHandler() {
        for (var i = 0; i < nGamers; i++)
            b[i].setMyLeaderCards(giveCardLead().toArray(CardLeader[]::new));
    }

    public List<CardLeader> giveCardLead() {
        ArrayList<CardLeader> cardPlayer = new ArrayList<>();
        while (cardPlayer.size() < 4) {
            cardPlayer.add(selectNextPlayableLeaderCards());
        }
        return cardPlayer;
    }

    private CardLeader selectNextPlayableLeaderCards() {
        while (true) {
            var i = random.nextInt(cardLeaderDeck.size());
            if (!(playableLeaderCards.contains(i))) {
                playableLeaderCards.add(i);
                return cardLeaderDeck.get(i);
            }
        }
    }

    public List<CardDev> getCardDevArrayDeck() {
        return cardDevArrayDeck;
    }

    public List<CardLeader> getCardLeaderDeck() {
        return cardLeaderDeck;
    }

    public List<CardPopeFav> getCardPopeDeck() {
        return cardPopeDeck;
    }

    public void pickStartingResources() {
        Colours choice = Colours.WHITE;
        int next = checkNext();
        if (nGamers > 1) {
            b[next].getWarehouse().moveResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
            b[next].updateResAll();
            if (nGamers > 2) {
                next = checkNext();
                b[next].getWarehouse().moveResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
                b[next].getFaithTrack().setPosition(1);
                b[next].updateResAll();
                if (nGamers > 3) {
                    next = checkNext();
                    b[next].getWarehouse().moveResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
                    Colours c = choice.selectPref();
                    int lvl = b[next].getWarehouse().isThereThatRes(c);
                    if (lvl < 0) lvl = 1;
                    b[next].getWarehouse().moveResources(lvl, new Pick(c, 1));
                    b[next].getFaithTrack().setPosition(1);
                    b[next].updateResAll();
                }
            }
        }
    }

    public int getnGamers() {
        return nGamers;
    }

    public static void setNGamers(int gamers) {
        nGamers = gamers;
    }

    public int checkNext() {
        if (playingPlayer++ >= nGamers - 1) return 0;
        return playingPlayer;
    }

    public int[] countWinPntPerBoard() {
        var pntWin = new int[nGamers];
        for (var i = 0; i < nGamers; i++) {
            pntWin[i] = b[i].countWinPnt();
        }
        return pntWin;
    }

    public void myCardPopeFavHandler() {
        for (var i = 0; i < nGamers; i++)
            b[i].setMyPFCards(giveCardLead().toArray(CardPopeFav[]::new));
    }

    public void nextAction(){
        /*chiede alla view l'azione da fare
        * GameController riceve qualcosa e fa partire .action(...)*/
    }
}
