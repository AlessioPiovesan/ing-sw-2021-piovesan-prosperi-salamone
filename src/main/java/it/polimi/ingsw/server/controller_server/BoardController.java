package it.polimi.ingsw.server.controller_server;


import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model_server.board.Board;

import java.util.List;
import java.util.Map;

public class BoardController {

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

    public void pickCardLeader(CardLeader[] choice) {
        player.setMyLeaderCards(choice);
    }

    public void marketResHandler(List<Colours> keep, List<Colours> loose) {
        /*manda le keep in warehouse*/
        for (Colours colour : keep)
            player.resHandler(colour);
        /*manda le loose in descarded*/
    }

    public void warehouseResHandler(Colours colour, int qnt, int lvl) {
        player.storeW(lvl, new Pick[]{new Pick(colour, qnt)});
    }

    public void strongboxResHandler(Colours colour, int qnt) {
        player.storeSB(new Pick[]{new Pick(colour, 1)});
    }

    public void update(Board message) {
    }
}
