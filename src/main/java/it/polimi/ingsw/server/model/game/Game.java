package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class Game provides a Model for the Game.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Game{

    private static int nGamers;
    private final Board[] boards;
    private final Random random;
    private final ArrayList<CardDev> cardDevDeck;
    private final ArrayList<CardLeader> cardLeaderDeck;
    private final ArrayList<Integer> playableLeaderCards;
    private final Market market;
    private final int id;
    private int playingPlayer;

    public Game(int id, int players, Market market){
        this.id = id;
        boards = new Board[players];
        random = new Random();
        setNGamers(players);
        /* Board creation for each player */
        for(var i = 0; i < players; i++){
            var warehouse = new Warehouse();
            var strongBox = new StrongBox();
            var faithTrack = new FaithTrack();
            var library = new Library();
            boards[i] = new Board(i, warehouse, strongBox, faithTrack, library);
        }
        playingPlayer = 0;
        /* Market creation for each Game */
        this.market = market;
        /* Card cataloging for each Game */
        cardDevDeck = new ArrayList<>();
        cardDevDeck.addAll(Arrays.asList(CardDev.values()));
        cardLeaderDeck = new ArrayList<>();
        cardLeaderDeck.addAll(Arrays.asList(CardLeader.values()));
        playableLeaderCards = new ArrayList<>();
    }

    public int getNGamers(){return nGamers;}

    public static void setNGamers(int players){
        nGamers = players;
    }

    public Board[] getAllB(){
        return boards;
    }

    public int getId(){
        return id;
    }

    public Board getSingleB(int i){
        return boards[i];
    }

    public Market getMarket(){
        return market;
    }

    public int getPlayingPlayer(){
        return playingPlayer;
    }

    public void setPlayingPlayer(int newPlayingPlayer){
        playingPlayer = newPlayingPlayer;
    }

    public void nextReady(){
        boards[playingPlayer].ready();
    }

    /**
     * Method myLeaderCardsHandler is the method used to deal the 4 initial Leader Cards which 2 of them can be later chosen by the Player.
     */
    public void myLeaderCardsHandler(){
        for(var i = 0; i < nGamers; i++)
            boards[i].setTemporaryLeaderCards(giveCardLead().toArray(CardLeader[]::new));
    }

    /**
     * Method giveCardLead randomly draws 4 Leader Cards from the deck of all the Leader Cards.
     *
     * @return List of CardLeader
     */
    public List<CardLeader> giveCardLead(){
        var cardPlayer = new ArrayList<CardLeader>();
        while(cardPlayer.size() < 4){
            cardPlayer.add(selectNextPlayableLeaderCards());
        }
        return cardPlayer;
    }

    /**
     * Method selectNextPlayableLeaderCards is the method used for the randomization of the Leader Cards
     *
     * @return CardLeader
     */
    private CardLeader selectNextPlayableLeaderCards(){
        while(true){
            var i = random.nextInt(cardLeaderDeck.size());
            if(!(playableLeaderCards.contains(i))){
                playableLeaderCards.add(i);
                return cardLeaderDeck.get(i);
            }
        }
    }

    public List<CardDev> getCardDevDeck(){
        return cardDevDeck;
    }

    /**
     * Method pickStartingResources is the method for the choices of initial resources from selected players.
     */
    public void pickStartingResources(){
        int next = playingPlayer;
        if(next++ >= nGamers - 1) next = 0;
        if(nGamers > 1){
            boards[next].setPreference();
            if(nGamers > 2){
                if(next++ >= nGamers - 1) next = 0;
                boards[next].setPreference();
                boards[next].moveFT(1);
                if(nGamers > 3){
                    if(next++ >= nGamers - 1) next = 0;
                    boards[next].setDoublePreference();
                    boards[next].moveFT(1);
                }
            }
        }
        startRealPlay();
    }

    private void startRealPlay(){
        boards[playingPlayer].ready();
    }

    /**
     * Method checkNext is only used to check if the next Board usable is the first one in the array.
     *
     * @return PlayingPlayer of type int - Next player in queue
     */
    public int checkNext(){
        playingPlayer++;
        if(playingPlayer > (nGamers - 1)) playingPlayer = 0;
        return playingPlayer;
    }
}
