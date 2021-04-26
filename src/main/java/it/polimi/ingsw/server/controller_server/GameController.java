package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.client.accessori.Constants;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.plancia.Board;
import it.polimi.ingsw.server.viewer_server.Observer;

public class GameController implements Observer<Game> {

    Game game;

    public GameController(Game game) {
        this.game = game;
    }

    private void nextTurn() {
    }

    @Override
    public void update(Game game) {
        /*Riceve le scelte del giocatore e manda avanti la partita*/
        nextTurn();
    }

    public void endGame() {
        int[] win = new int[game.getnGamers()];
        int i = 0;
        while (i < game.getnGamers()) {
            for (Board b : game.getAllB()) {
                if (b.getFaithTrack().getPosition() == Constants.MAXTRACK.getVal()) win[i] += b.countWinPnt();
                i++;
            }
        }

    }

    public void action(int moveMarket) {
        /*sposta il mercato*/
    }

    public void action(CardDev cardToBeBought) {
        /*compra la carta*/
    }

    public void action() {
        /*parte la produzione di tutto quello che c'è sulla plancia*/
    }

    public void action(CardLeader cardToBeActivated) {
        /*controlli che possa attivarla*/
        /*attiva quella carta leader*/
    }

}
