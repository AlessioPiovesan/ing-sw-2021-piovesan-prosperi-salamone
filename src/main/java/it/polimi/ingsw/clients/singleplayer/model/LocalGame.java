package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.*;
import it.polimi.ingsw.server.model.game.Market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class LocalGame is the model for the local single player game without establishing any connections.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class LocalGame{

    private final Market market;
    private final Random random = new Random();
    private final Board[] boards;
    private final ArrayList<CardDev> cardDevDeck;
    private final ArrayList<CardLeader> cardLeaderDeck;
    private final ArrayList<Integer> playableLeaderCards;

    public LocalGame(Market market){
        this.market = market;
        boards = new Board[2];
        boards[0] = new Board(0, new Warehouse(), new StrongBox(), new FaithTrack(), new Library());
        boards[1] = new Lorenzo(1, new FaithTrack());
        cardDevDeck = new ArrayList<>();
        cardDevDeck.addAll(Arrays.asList(CardDev.values()));
        cardLeaderDeck = new ArrayList<>();
        cardLeaderDeck.addAll(Arrays.asList(CardLeader.values()));
        playableLeaderCards = new ArrayList<>();
    }

    public List<CardDev> getCardDevDeck(){
        return cardDevDeck;
    }

    public Board getBoard(boolean isLorenzo){
        if(isLorenzo) return boards[1];
        else return boards[0];
    }

    public Market getMarket(){
        return market;
    }

    /**
     * Method myLeaderCardsHandler handles the initial four leader cards.
     *
     * @author Alessio Piovesan
     */
    public void myLeaderCardsHandler(){
        boards[0].setTemporaryLeaderCards(giveCardLead().toArray(CardLeader[]::new));
    }

    /**
     * Method giveCardLead deals the four leader cards to the player
     *
     * @return the four leader cards the player receives at the start of the game
     * @author Alessio Piovesan
     */
    private List<CardLeader> giveCardLead(){
        var cardPlayer = new ArrayList<CardLeader>();
        while(cardPlayer.size() < 4){
            cardPlayer.add(selectNextPlayableLeaderCards());
        }
        return cardPlayer;
    }

    /**
     * Method selectNextPlayableLeaderCards is the method that lets the player choose two leader cards out of the four available.
     *
     * @return an available leader card
     * @author Alessio Piovesan
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
}
