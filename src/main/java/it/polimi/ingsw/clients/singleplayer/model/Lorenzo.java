package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.FaithTrack;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lorenzo extends Board{

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    Lorenzo(int id, FaithTrack faithTrack){
        super(id, null, null, faithTrack, null);
    }
}
