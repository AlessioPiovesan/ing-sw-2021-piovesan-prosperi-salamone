package it.polimi.ingsw.server.controller_server;


import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.model_server.board.Board;
import it.polimi.ingsw.server.model_server.cards.CardLeader;
import it.polimi.ingsw.server.accessories.Observer;

import java.util.List;
import java.util.Map;

public class BoardController implements Observer<Board> {

    Board player;

    public BoardController(Board player) {
        this.player = player;
    }

    public Board getPlayer() {
        return player;
    }

    public Map<Colours, Integer> checkResAllPlayer() {
        return player.getResAll();
    }

    public void pickCardLeader() {
        var choice = new CardLeader[2];
        System.out.println("Prima scelta");     /*Mostri le quattro carte e ne scegli una*/
        choice[0] = (CardLeader.CARDLEAD01);      /*Inserisci la carta scelta nella lista */
        System.out.println("Seconda scelta");   /*Mostri le tre carte e ne scegli una*/
        choice[1] = CardLeader.CARDLEAD02;      /*Inserisci la carta scelta nella lista */
        player.setMyLeaderCards(choice);
    }

    public void marketResHandler(List<Colours> keep, List<Colours> loose) {
        /*manda le keep in warehouse*/
        for (Colours colour : keep)
            player.resHandler(colour);
        /*manda le loose in descarded*/
    }

    public void warehouseResHandler(Colours colour, int lvl) {
        player.getWarehouse().moveResources(lvl, new Pick(colour, 1));
    }

    @Override
    public void update(Board message) {
    }
}
