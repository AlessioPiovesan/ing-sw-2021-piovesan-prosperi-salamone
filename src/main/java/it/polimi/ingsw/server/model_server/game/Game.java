package it.polimi.ingsw.server.model_server.game;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.model_server.board.*;
import it.polimi.ingsw.server.model_server.cards.CardDev;
import it.polimi.ingsw.server.model_server.cards.CardLeader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game implements Serializable {

    private static int nGamers = 4;
    private final Board[] boards = new Board[4]; /* c'è DA IMPLEMENTARE l'associazione con i socket dei vari player*/
    private final Random random = new Random();
    private final ArrayList<CardDev> cardDevDeck = new ArrayList<>();
    private final ArrayList<CardLeader> cardLeaderDeck = new ArrayList<>();
    private final ArrayList<Integer> playableLeaderCards = new ArrayList<>();
    private final Market market;
    int id;
    int playingPlayer;

    public Game(Market newMarket) {
        /* Board creation for each player */
        for (var i = 0; i < nGamers; i++) {
            var warehouse = new Warehouse();
            var strongBox = new StrongBox();
            var faithTrack = new FaithTrack();
            var library = new Library();
            boards[i] = new Board(i, warehouse, strongBox, faithTrack, library);
        }
        /* Market creation for each Game */
        market = newMarket;
        /* Card cataloging for each Game */
        cardDevDeck.addAll(Arrays.asList(CardDev.values()));
        cardLeaderDeck.addAll(Arrays.asList(CardLeader.values()));
        /*...*/
    }

    public int getNGamers() {
        return nGamers;
    }

    public static void setNGamers(int gamers) {
        nGamers = gamers;
    }

    public Board[] getAllB() {
        return boards;
    }

    public Board getSingleB(int i) {
        return boards[i];
    }

    public Market getMarket() {
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
            boards[i].setMyLeaderCards(giveCardLead().toArray(CardLeader[]::new));
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

    public List<CardDev> getCardDevDeck() {
        return cardDevDeck;
    }

    public List<CardLeader> getCardLeaderDeck() {
        return cardLeaderDeck;
    }

    public void pickStartingResources() {
        Colours choice = Colours.WHITE;
        int next = checkNext();
        if (nGamers > 1) {
            boards[next].getWarehouse().moveResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
            boards[next].updateResAll();
            if (nGamers > 2) {
                next = checkNext();
                boards[next].getWarehouse().moveResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
                boards[next].getFaithTrack().moveForward(1);
                boards[next].updateResAll();
                if (nGamers > 3) {
                    next = checkNext();
                    boards[next].getWarehouse().moveResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
                    Colours c = choice.selectPref();
                    int lvl = boards[next].getWarehouse().isThereThatRes(c);
                    if (lvl < 0) lvl = 1;
                    boards[next].getWarehouse().moveResources(lvl, new Pick(c, 1));
                    boards[next].getFaithTrack().moveForward(1);
                    boards[next].updateResAll();
                }
            }
        }
    }

    public int checkNext() {
        if (playingPlayer++ >= nGamers - 1) return 0;
        return playingPlayer;
    }

    public int[] countWinPntPerBoard() {
        var pntWin = new int[nGamers];
        for (var i = 0; i < nGamers; i++) {
            pntWin[i] = boards[i].countWinPnt();
        }
        return pntWin;
    }

    public void nextAction() {
        /*chiede alla view l'azione da fare
         * GameController riceve qualcosa e fa partire .action(...)*/
    }

    public void ready() { /* DA IMPLEMENTARE */
        notify();
    }
}
