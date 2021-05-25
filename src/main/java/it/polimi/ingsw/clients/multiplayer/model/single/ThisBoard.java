package it.polimi.ingsw.clients.multiplayer.model.single;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ThisBoard{

	private LinkedList<CardDev> myDevCards = new LinkedList<>();
	private String nickname;
	private Colours[][] warehouse;
	private int faithTrack;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private EnumMap<Colours, Integer> strongBox;
	private CardDev[] library;
	private EnumMap<Colours, Integer> resAll;
	private boolean[] activeLeader;
	private Colours choosingRes;
	private CardLeader[] myLeaderCards = new CardLeader[2];

	protected ThisBoard(){}

	public List<CardDev> getMyDevCards(){
		return myDevCards;
	}

	public void setMyDevCards(List<CardDev> myDevCards){
		var old = this.myDevCards;
		this.myDevCards = (LinkedList<CardDev>)myDevCards;
		pcs.firePropertyChange("myDevCards",old, this.myDevCards);
	}

	public Colours[][] getWarehouse(){
		return warehouse;
	}

	public void setWarehouse(Colours[][] warehouse){
		this.warehouse = warehouse;
	}

	public int getFaithTrack(){
		return faithTrack;
	}

	public void setFaithTrack(int faithTrack){
		this.faithTrack = faithTrack;
	}

	public Map<Colours, Integer> getStrongBox(){
		return strongBox;
	}

	public void setStrongBox(Map<Colours, Integer> strongBox){
		this.strongBox = (EnumMap<Colours, Integer>)strongBox;
	}

	public CardDev[] getLibrary(){
		return library;
	}

	public void setLibrary(CardDev[] library){
		this.library = library;
	}

	public Map<Colours, Integer> getResAll(){
		return resAll;
	}

	public void setResAll(Map<Colours, Integer> resAll){
		this.resAll = (EnumMap<Colours, Integer>)resAll;
	}

	public String getNickname(){
		return nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public boolean[] getActiveLeader(){
		return activeLeader;
	}

	public void setActiveLeader(boolean[] activeLeader){
		this.activeLeader = activeLeader;
	}

	public Colours getChoosingRes(){
		return choosingRes;
	}

	public void setChoosingRes(Colours choosingRes){
		this.choosingRes = choosingRes;
	}

	public CardLeader[] getMyLeaderCards(){
		return myLeaderCards;
	}

	public void setMyLeaderCards(CardLeader[] myLeaderCards){
		this.myLeaderCards = myLeaderCards;
	}

	protected PropertyChangeSupport getPcs(){return pcs;}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}
}
