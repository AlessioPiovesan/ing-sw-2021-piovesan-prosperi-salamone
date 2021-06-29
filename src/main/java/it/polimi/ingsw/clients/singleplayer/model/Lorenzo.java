package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.FaithTrack;

import java.beans.PropertyChangeSupport;

/**
 * class Lorenzo is the model for Lorenzo who is a pleyer's opponent whenever he decides to play local single player.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Lorenzo extends Board{

    private final FaithTrack faithTrack;
    private final PropertyChangeSupport pcs;
    private final int id;
    private UI ui;
    private boolean endGame;

    Lorenzo(int id, FaithTrack faithTrack){
        super(id, null, null, faithTrack, null);
        this.id = id;
        this.faithTrack = faithTrack;
        pcs = new PropertyChangeSupport(this);
        endGame = false;
    }

    public void setUi(UI ui){
        this.ui = ui;
    }

    public void print(String action){
        ui.printMessage(action);
    }

    @Override
    public void moveFT(int move){
        faithTrack.moveForward(move);
        checkIfEndGame();
    }

    @Override
    protected void checkIfEndGame(){
        if(faithTrack.getPosition() == 24) endGame = true;
        if(endGame) pcs.firePropertyChange("endGame", false, true);
    }
}
