package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.FaithTrack;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * class Lorenzo is the model for Lorenzo who is a player's opponent whenever he decides to play local single player.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Lorenzo extends Board{

    private final PropertyChangeSupport propertyChangeSupport;
    private UI ui;

    Lorenzo(int id, FaithTrack faithTrack){
        super(id, null, null, faithTrack, null);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setUi(UI ui){
        this.ui = ui;
    }

    public void print(String action){
        ui.printMessage(action);
    }

    public void nextAction(){
        propertyChangeSupport.firePropertyChange("nextAction", "", 1);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
