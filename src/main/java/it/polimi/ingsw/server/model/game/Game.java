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
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public class Game{

    private static int nGamers = 4;
    private final Board[] boards = new Board[4];
    private final Random random = new Random();
    private final ArrayList<CardDev> cardDevDeck;
    private final ArrayList<CardLeader> cardLeaderDeck;
    private final ArrayList<Integer> playableLeaderCards;
    private final Market market;
    private final int id;
    private int playingPlayer;

    public Game(int id, int players, Market market){
        this.id = id;
        setNGamers(players);
        /* Board creation for each player */
        for(var i = 0; i < nGamers; i++){
            var warehouse = new Warehouse();
            var strongBox = new StrongBox();
            var faithTrack = new FaithTrack();
            var library = new Library();
            boards[i] = new Board(i, warehouse, strongBox, faithTrack, library);
            playingPlayer = 0;
        }
        /* Market creation for each Game */
        this.market = market;
        /* Card cataloging for each Game */
        cardDevDeck = new ArrayList<>();
        cardDevDeck.addAll(Arrays.asList(CardDev.values()));
        cardLeaderDeck = new ArrayList<>();
        cardLeaderDeck.addAll(Arrays.asList(CardLeader.values()));
        playableLeaderCards = new ArrayList<>();
    }

    public int getNGamers(){
        return nGamers;
    }

    public static void setNGamers(int gamers){
        nGamers = gamers;
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

    public void setPlayingPlayer(int playingPlayer){
        this.playingPlayer = playingPlayer;
    }

    /**
     * Method myLeaderCardsHandler is the method used to deal the 4 initial Leader Cards which 2 of them can be later chosen by the Player.
     *
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void myLeaderCardsHandler(){
        for(var i = 0; i < nGamers; i++)
            boards[i].setTemporaryLeaderCards(giveCardLead().toArray(CardLeader[]::new));
    }

    /**
     * Method giveCardLead randomly draws 4 Leader Cards from the deck of all the Leader Cards.
     *
     * @return List of CardLeader
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
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
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
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

    public List<CardLeader> getCardLeaderDeck(){
        return cardLeaderDeck;
    }

    /**
     * Method pickStartingResources is the method for the choices of initial resources from selected players.
     *
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
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
                    boards[next].setPreference();
                    boards[next].setPreference();
                    boards[next].moveFT(1);
                }
            }
        }
    }

    /**
     * Method checkNext is only used to check if the next Board usable is the first one in the array.
     *
     * @return PlayingPlayer of type int - Next player in queue
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public int checkNext(){
        if(playingPlayer++ >= nGamers - 1) return 0;
        return playingPlayer;
    }

    /**
     * Method countWinPntPerBoard is used to calculate the Points needed to determine the winner of the Game.
     *
     * @return pntWin of type int - points needed to win the game.
     */
    public int[] countWinPntPerBoard(){
        var pntWin = new int[nGamers];
        for(var i = 0; i < nGamers; i++){
            pntWin[i] = boards[i].countWinPnt();
        }
        return pntWin;
    }
}
