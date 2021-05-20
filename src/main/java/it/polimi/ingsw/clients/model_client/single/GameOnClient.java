package it.polimi.ingsw.clients.model_client.single;

import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model_server.board.*;
import it.polimi.ingsw.server.model_server.game.Market;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameOnClient {

    private final Market market;
    private final Random random = new Random();
    private final Board[] boards;
    private final ArrayList<CardDev> cardDevDeck = new ArrayList<>();
    private final ArrayList<CardLeader> cardLeaderDeck = new ArrayList<>();
    private final ArrayList<Integer> playableLeaderCards = new ArrayList<>();

    public GameOnClient(Market market) {
        this.market = market;
        boards = new Board[2];
        boards[0] = new Board(0, new Warehouse(), new StrongBox(), new FaithTrack(), new Library());
        boards[1] = new Lorenzo(1, new FaithTrack());
        cardDevDeck.addAll(Arrays.asList(CardDev.values()));
        cardLeaderDeck.addAll(Arrays.asList(CardLeader.values()));
    }

    public List<CardDev> getCardDevDeck() {
        return cardDevDeck;
    }

    public Board getBoard(boolean isLorenzo) {
        if (isLorenzo) return boards[1];
        else return boards[0];
    }

    public Market getMarket() {
        return market;
    }

    public void myLeaderCardsHandler() {
        boards[0].setTemporaryLeaderCards(giveCardLead().toArray(CardLeader[]::new));
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

    public Board[] getBoards() {
        return boards;
    }

}
