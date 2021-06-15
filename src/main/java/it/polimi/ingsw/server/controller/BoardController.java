package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.Board;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

/**
 * class BoardController represents the controller of a player's board
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public class BoardController implements PropertyChangeListener{

    private final Board player;

    public BoardController(Board player){
        this.player = player;
    }

    public Board getPlayer(){
        return player;
    }

    public Map<Colours, Integer> checkResAllPlayer(){
        return player.getResAll();
    }

    public void marketResHandler(List<Colours> keep, List<Colours> loose){
        /*manda le keep in warehouse*/
        for(Colours colour : keep)
            player.resHandler(colour);
        /*manda le loose in descarded*/
    }

    public void warehouseResHandler(Colours colour, int qnt, int lvl){
        player.storeW(lvl, new Pick[]{new Pick(colour, qnt)});
    }

    public void strongboxResHandler(Colours colour, int qnt){
        player.storeSB(new Pick[]{new Pick(colour, 1)});
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        player.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        player.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        if(evt.getPropertyName().equals("myLeaderCards")) setMyCardLeader((CardLeader[])evt.getNewValue());
    }

    public void setMyCardLeader(CardLeader[] choice){
        player.setMyLeaderCards(choice);
    }

    public void sendStatus(){
        player.createStatus();
    }
}
