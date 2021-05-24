package it.polimi.ingsw.clients.model_client.single;

import it.polimi.ingsw.server.model_server.board.Board;
import it.polimi.ingsw.server.model_server.board.FaithTrack;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lorenzo extends Board{

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	Lorenzo(int id, FaithTrack faithTrack){
		super(id, null, null, faithTrack, null);
	}

	public void ready(){
		pcs.firePropertyChange("nextAction", 0, 1);
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
