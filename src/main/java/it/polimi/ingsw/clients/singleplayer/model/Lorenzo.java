package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.FaithTrack;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lorenzo extends Board{

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private boolean ready;

	Lorenzo(int id, FaithTrack faithTrack){
		super(id, null, null, faithTrack, null);
		ready = false;
	}

	@Override
	public void ready(){
		ready = !ready;
		pcs.firePropertyChange("nextAction", false, 1);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}
}
