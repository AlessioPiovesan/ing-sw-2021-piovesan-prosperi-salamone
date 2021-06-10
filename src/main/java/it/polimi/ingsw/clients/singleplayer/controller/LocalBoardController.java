package it.polimi.ingsw.clients.singleplayer.controller;

import it.polimi.ingsw.clients.singleplayer.model.Lorenzo;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.model.board.Board;

public record LocalBoardController(Board player, Lorenzo lorenzo){

    public Lorenzo getLorenzo(){
        return lorenzo;
    }

    public Board getPlayer(){
        return player;
    }

    public void warehouseResHandler(Colours colour, int qnt, int lvl){
        player.storeW(lvl, new Pick[]{new Pick(colour, qnt)});
    }

    public void moveFT(int move){
        player.moveFT(move);
    }

    public void active(){
        player.ready();
    }

    public void rearrangeWarehouse(int first, int second){
        player.rearrangeWarehouse(first, second);
    }

    public void takeFromWarehouse(Colours colour){
        player.takeFromWarehouse(colour);
    }

    public void takeFromStrongbox(Colours color){
        player.takeFromStrongBox(color);
    }

    public synchronized void checkVaticanReport(){
        player.checkVaticanReport();
        lorenzo.checkVaticanReport();
    }
}
