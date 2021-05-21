package it.polimi.ingsw.clients.controller_client;

import it.polimi.ingsw.clients.accessories.SoloAction;
import it.polimi.ingsw.clients.model_client.single.GameOnClient;
import it.polimi.ingsw.clients.model_client.single.Lorenzo;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model_server.game.Market;

import java.util.LinkedList;
import java.util.List;

public class GameControllerOnCLient {

    private static GameControllerOnCLient singleInstance;
    private final GameOnClient game;
    private final BoardControllerOnClient boardController;
    private final LorenzoController lorenzoController;
    private boolean playingPlayer;
    private LinkedList<SoloAction> listSoloAction;

    private GameControllerOnCLient() {
        this.game = new GameOnClient(new Market());
        boardController = new BoardControllerOnClient(game.getBoard(false));
        lorenzoController = new LorenzoController((Lorenzo) game.getBoard(true));
        listSoloAction = (LinkedList<SoloAction>) SoloAction.shuffle();
    }

    public static GameControllerOnCLient getInstance() {
        if (singleInstance == null)
            singleInstance = new GameControllerOnCLient();
        return singleInstance;
    }

    public GameOnClient getGame() {
        return game;
    }

    public BoardControllerOnClient getBoardController() {
        return boardController;
    }

    public LorenzoController getLorenzoController() {
        return lorenzoController;
    }

    public void startGame() {
        playingPlayer = false;
        game.myLeaderCardsHandler();
    }

    public void nextPlayer() {
        playingPlayer = !playingPlayer;
    }

    public boolean isLorenzoPlaying() {
        return playingPlayer;
    }

    public void getReady() {
        nextPlayer();
        if (playingPlayer) lorenzoController.active();
        else boardController.active();
    }

    public void moveLorenzo() {
        SoloAction.action(listSoloAction.get(0));
        listSoloAction = (LinkedList<SoloAction>) SoloAction.pickSoloAction(listSoloAction);
    }

    public void marketAction(int moveMarket) {
        var b = boardController.getPlayer();
        var m = game.getMarket();
        /*sposta le biglie nel mercato*/
        LinkedList<Colours> res = (LinkedList<Colours>) m.moveMarbles(moveMarket);
        /*mando nella funzione che manda a far scegliere al giocatore che fare di queste risorse*/
        b.marketResHandler(res);
    }

    public void addFaithPnt(List<Colours> waste) {
        int a = waste.size();
        lorenzoController.getLorenzo().getFaithTrack().moveForward(a);
    }

    public void prodAction() {
        var b = game.getBoard(playingPlayer);
        /*parte la produzione di tutto quello che c'è sulla plancia*/
        b.productionAll();
    }

    public void cardLeadAction(CardLeader cardToBeActivated) {
        /*attiva quella carta leader*/
        switch (cardToBeActivated) {
            case CARDLEAD01, CARDLEAD02, CARDLEAD03, CARDLEAD04 -> game.getBoard(false).setDiscount(cardToBeActivated.getSpecial());
            case CARDLEAD05, CARDLEAD06, CARDLEAD07, CARDLEAD08 -> game.getBoard(false).getWarehouse().addLeaderSpace(cardToBeActivated.getSpecial());
            case CARDLEAD09, CARDLEAD10, CARDLEAD11, CARDLEAD12 -> game.getBoard(false).setMarketAlt(cardToBeActivated.getSpecial());
            case CARDLEAD13, CARDLEAD14, CARDLEAD15, CARDLEAD16 -> game.getBoard(false).setProductAlt(cardToBeActivated.getSpecial());
            default -> throw new IllegalStateException("Unexpected value: " + cardToBeActivated);
        }
    }

    public void cardDevAction(CardDev cardToBeBought) {
        var b = boardController.getPlayer();
        game.getCardDevDeck().remove(cardToBeBought);
        /*compra la carta*/
        b.buyCardDev(cardToBeBought);
    }

    public void endGame(boolean winner) {
        if (winner) {
            var win = boardController.getPlayer().countWinPnt();
            System.out.println("YOU WON!\nYour record is " + win + " points!");
        } else System.out.println("YOU LOSE");
    }

    public void tossCardDev(ColoursCards colour) {
        var deck = game.getCardDevDeck();
        CardDev found = null;
        for (CardDev cardDev : deck)
            for (var i = 0; i < 3; i++)
                if (cardDev.getLvl() == 1 && cardDev.getColour() == colour)
                    found = cardDev;
        if (found == null) endGame(false);
    }
}
