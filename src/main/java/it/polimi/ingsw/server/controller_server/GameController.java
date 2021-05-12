package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model_server.board.Board;
import it.polimi.ingsw.server.model_server.game.Game;

import java.util.LinkedList;
import java.util.List;

public class GameController {

    Game game;

    public GameController(Game game) {
        this.game = game;
    }


    public void update(Game game) {

        /*Riceve le scelte del giocatore e manda avanti la partita*/
        game.setPlayingPlayer(game.checkNext());
        /*funzione che non abbiamo di Game che chiede alla view qual è l'azione successiva*/
        game.ready();
        update(game);
    }

    public void setGamers(int gamers) {
        Game.setNGamers(gamers);
    }

    public void startGame() {
        game.myLeaderCardsHandler();
        game.pickStartingResources();
        update(game);
    }

    public void endGame() {
        var win = new int[game.getNGamers()];
        var i = 0;
        while (i < game.getNGamers()) {
            for (Board b : game.getAllB()) {
                win[i] += b.countWinPnt();
                i++;
            }
        }

    }

    public void prodAction() {
        Board b = game.getSingleB(game.getPlayingPlayer());
        /*parte la produzione di tutto quello che c'è sulla plancia*/
        b.productionAll();
    }

    public void marketAction(int moveMarket) {
        var b = game.getSingleB(game.getPlayingPlayer());
        var m = game.getMarket();
        /*sposta le biglie nel mercato*/
        LinkedList<Colours> res = (LinkedList<Colours>) m.moveMarbles(moveMarket);
        /*mando nella funzione che manda a far scegliere al giocatore che fare di queste risorse*/
        b.marketResHandler(res);
    }

    public void storeInWarehose(List<Pick> goods, List<Colours> waste) {
        var b = game.getSingleB(game.getPlayingPlayer());
        for (Pick res : goods) {
            b.storeW(res.getQnt(), new Pick[]{new Pick(res.getResource(), 1)});
        }
        addFaithPnt(waste);
    }

    public void resolveLeaderConflictInMarket(int i) {
        var b = game.getSingleB(game.getPlayingPlayer());
        b.resolveLeaderConflict(i);
    }

    public void cardDevAction(CardDev cardToBeBought) {
        Board b = game.getSingleB(game.getPlayingPlayer());
        /*compra la carta*/
        b.buyCardDev(cardToBeBought);
    }

    public void cardLeadAction(CardLeader cardToBeActivated) {
        /*controlli che possa attivarla*/
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

    private void addFaithPnt(List<Colours> waste) {
        int a = game.getSingleB(game.getPlayingPlayer()).checkDiscardedResources(waste);
        for (var j = 0; j < game.getNGamers(); j++) {
            if (j != game.getPlayingPlayer()) {
                game.getSingleB(j).getFaithTrack().moveForward(a);
            }
        }
    }

}
