package it.polimi.ingsw.clients.view_client;

import it.polimi.ingsw.server.accessories.cards.Card;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GUI implements Runnable, UI{

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private boolean single;

	@Override
	public void run(){

	}

	@Override
	public void showCards(Card list){

	}

	@Override
	public void setIfSingle(boolean b){
		single = b;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt){
		System.out.println(evt);
	}
}
