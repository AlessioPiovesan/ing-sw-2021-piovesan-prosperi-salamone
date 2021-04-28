package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.gioco.Market;
import it.polimi.ingsw.server.model_server.gioco.Supply;
import it.polimi.ingsw.server.model_server.plancia.Board;
import it.polimi.ingsw.server.model_server.plancia.Warehouse;
import it.polimi.ingsw.server.viewer_server.Observer;

import java.util.LinkedList;

public class GameController implements Observer<Game> {

    Game game;

    public GameController(Game game) {
        this.game = game;
    }

    private void nextTurn() {
        game.setPlayingPlayer(game.checkNext());
        /*funzione che non abbiamo di Game che chiede alla view qual è l'azione successiva*/
    }

    @Override
    public void update(Game game) {

        /*Riceve le scelte del giocatore e manda avanti la partita*/

        action();
        nextTurn();
    }

    public void setGamers(int gamers) {
        Game.setNGamers(gamers);
    }

    public void startGame() {
        game = new Game(new Market(), new Supply());
        game.myLeaderCardsHandler();
        game.pickStartingResources();
        nextTurn();
    }

    public void endGame() {
        var win = new int[game.getnGamers()];
        var i = 0;
        while (i < game.getnGamers()) {
            for (Board b : game.getAllB()) {
                win[i] += b.countWinPnt();
                i++;
            }
        }

    }

    public void action() {
        Board b = game.getSingleB(game.getPlayingPlayer());
        /*parte la produzione di tutto quello che c'è sulla plancia*/
        b.productionAll();
    }

    public void action(int moveMarket) {
        Board b = game.getSingleB(game.getPlayingPlayer());
        Market m = game.getMarket();
        /*sposta le biglie nel mercato*/
        LinkedList<Colours> res = (LinkedList<Colours>) m.moveMarbles(moveMarket);
        /*mando nella funzione che manda a far scegliere al giocatore che fare di queste risorse*/
        b.marketResHandler(res);
    }

    public void action(CardDev cardToBeBought) {
        Board b = game.getSingleB(game.getPlayingPlayer());
        /*compra la carta*/
        b.buyCardDev(cardToBeBought);
    }

    public void action(CardLeader cardToBeActivated) {
        /*controlli che possa attivarla*/
        /*attiva quella carta leader*/

        switch (cardToBeActivated) {
            case card_Lead1, card_Lead2, card_Lead3, card_Lead4: {
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setDiscount(cardToBeActivated.getSpecial());
                break;
            }
            case card_Lead5, card_Lead6, card_Lead7, card_Lead8: {
                Warehouse w = game.getSingleB(game.getPlayingPlayer()).getWarehouse();
                w.addLeaderSpace(cardToBeActivated);
                break;
            }
            case card_Lead9, card_Lead10, card_Lead11, card_Lead12: {
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setMarketAlt(cardToBeActivated.getSpecial());
                break;
            }
            case card_Lead13, card_Lead14, card_Lead15, card_Lead16: {
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setProductAlt(cardToBeActivated.getSpecial());
                break;
            }
            default:
        }
    }

}
