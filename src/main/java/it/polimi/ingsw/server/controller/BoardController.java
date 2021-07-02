package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.Board;

/**
 * Class BoardController represents the controller of a player's board
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public record BoardController(Board player, String name){

    public void warehouseResHandler(Colours colour, int qnt, int lvl){
        player.storeInWarehouse(lvl, new Pick[]{new Pick(colour, qnt)});
    }

    public void setMyCardLeader(CardLeader[] choice){
        player.setMyLeaderCards(choice);
    }

    public void moveFT(int i){
        player.moveFT(i);
    }

    public void checkVaticanReport(){
        player.checkVaticanReport();
    }

    public void addToLeaderSpace(Colours fromString){
        player.addToLeaderSpace(fromString);
    }

    public void takeFromWarehouse(Colours fromString){
        player.takeFromWarehouse(fromString);
    }

    public void takeFromStrongBox(Colours fromString){
        player.takeFromStrongBox(fromString);
    }

    public void produce(CardDev card){
        player.produce(card);
    }

    public void storeInStrongBox(Colours resourceTaken, int qntTaken){
        player.storeInStrongBox(new Pick[]{new Pick(resourceTaken, qntTaken)});
    }
}
