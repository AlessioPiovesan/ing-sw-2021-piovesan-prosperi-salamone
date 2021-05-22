package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model_server.board.Board;
import it.polimi.ingsw.server.model_server.game.Game;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class GameController {

    private final BoardController[] boardController;
    private final int id;
    private final Game game;
    private final ArrayList<CardDev> cardDevDeck = new ArrayList<>();
    private final ArrayList<CardLeader> cardLeaderDeck = new ArrayList<>();
    private final ArrayList<Integer> playableLeaderCards = new ArrayList<>();
    private final PrintWriter out;
    private final int[] ready;
    private final int[] win;


    public GameController(int newID, int gamers, Game game) {
        out = new PrintWriter(System.out);
        id = newID;
        this.game = game;
        ready = new int[gamers];
        win = new int[game.getNGamers()];
        IntStream.range(0, gamers).forEachOrdered(i -> ready[i] = 0);
        boardController = new BoardController[gamers];
        IntStream.range(0, gamers).forEachOrdered(i -> boardController[i] = new BoardController(game.getSingleB(i)));
    }

    public void addFaithPnt(List<Colours> waste) {
        int a = waste.size();
        for (var j = 0; j < game.getNGamers(); j++) {
            if (j != game.getPlayingPlayer()) {
                game.getSingleB(j).getFaithTrack().moveForward(a);
            }
        }
    }

    public void cardDevAction(CardDev cardToBeBought) {
        Board b = game.getSingleB(game.getPlayingPlayer());
        game.getCardDevDeck().remove(cardToBeBought);
        /*compra la carta*/
        b.buyCardDev(cardToBeBought);
        update();
    }

    public void cardLeadAction(CardLeader cardToBeActivated) {
        /*attiva quella carta leader*/
        switch (cardToBeActivated) {
            case CARDLEAD01, CARDLEAD02, CARDLEAD03, CARDLEAD04: {
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setDiscount(cardToBeActivated.getSpecial());
                break;
            }
            case CARDLEAD05, CARDLEAD06, CARDLEAD07, CARDLEAD08: {
                var w = game.getSingleB(game.getPlayingPlayer()).getWarehouse();
                w.addLeaderSpace(cardToBeActivated.getSpecial());
                break;
            }
            case CARDLEAD09, CARDLEAD10, CARDLEAD11, CARDLEAD12: {
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setMarketAlt(cardToBeActivated.getSpecial());
                break;
            }
            case CARDLEAD13, CARDLEAD14, CARDLEAD15, CARDLEAD16: {
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setProductAlt(cardToBeActivated.getSpecial());
                break;
            }
            default:
        }
    }

    public void startGame() {
        out.println("Operazioni iniziali"); /*DA CANCELLARE*/
        game.checkNext();
        leaderCardsHandler();
    }

    public void endGame() {
        var i = 0;
        while (i < game.getNGamers()) {
            for (Board b : game.getAllB()) {
                win[i] += b.countWinPnt();
                i++;
            }
        }
    }

    public void initialResourcesHandler() {
        game.pickStartingResources();
    }

    public void leaderCardsHandler() {
        game.myLeaderCardsHandler();
    }

    public void marketAction(int moveMarket) {
        var b = game.getSingleB(game.getPlayingPlayer());
        var m = game.getMarket();
        /*sposta le biglie nel mercato*/
        LinkedList<Colours> res = (LinkedList<Colours>) m.moveMarbles(moveMarket);
        /*mando nella funzione che manda a far scegliere al giocatore che fare di queste risorse*/
        b.marketResHandler(res);
    }

    public void prodAction() {
        var b = game.getSingleB(game.getPlayingPlayer());
        /*parte la produzione di tutto quello che c'è sulla plancia*/
        b.productionAll();
        update();
    }

    public void prodAction(Colours colour) {
        Board b = game.getSingleB(game.getPlayingPlayer());
        /*parte la produzione di tutto quello che c'è sulla plancia*/
        b.productionExtra(colour);
    }

    public void resolveLeaderConflictInMarket(int i) {
        var b = game.getSingleB(game.getPlayingPlayer());
        b.resolveLeaderConflict(i);
    }

    public void update() {
        /*Riceve le scelte del giocatore e manda avanti la partita*/
        game.setPlayingPlayer(game.checkNext());
    }

    public BoardController getActiveBoardController() {
        return boardController[game.getNGamers() - 1];
    }

    public Game getGame() {
        return game;
    }

    public int getId() {
        return id;
    }

    public int[] getWin() {
        return win;
    }

    public void setGamers(int gamers) {
        Game.setNGamers(gamers);
    }
}
