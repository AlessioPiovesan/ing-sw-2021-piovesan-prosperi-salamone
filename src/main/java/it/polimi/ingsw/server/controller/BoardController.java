package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.Board;

/**
 * Class BoardController represents the controller of a player's board
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class BoardController{

    private final Board player;
    private final String name;

    public BoardController(Board player, String name){
        this.player = player;
        this.name = name;
    }

    public Board getPlayer(){
        return player;
    }

    public String getName(){return name;}

    /**
     * @param pick of type Pick
     * @param lvl  of type int
     * @return boolean
     * @see Board#checkWarehouseAdd(Pick, int)
     */
    public boolean checkWarehouseAdd(Pick pick, int lvl){
        return player.checkWarehouseAdd(pick, lvl);
    }

    public void warehouseResHandler(Colours colour, int qnt, int lvl){
        player.storeW(lvl, new Pick[]{new Pick(colour, qnt)});
    }

    public void setMyCardLeader(CardLeader[] choice){
        player.setMyLeaderCards(choice);
    }

    public void moveFT(int i){
        player.moveFT(i);
    }
}
