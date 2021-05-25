package it.polimi.ingsw.clients.multiplayer.model.single;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.Map;

public class OtherBoard{

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private int faithTrack;
	private CardDev[] library;
	private String nickname = "";
	private EnumMap<Colours, Integer> resAll;
	private boolean[] activeLeader;
	private Colours[][] warehouse;
	private Colours choosingRes = Colours.WHITE;
	private CardLeader[] myLeaderCards = new CardLeader[2];

	protected OtherBoard(){}

	public int getFaithTrack(){
		return faithTrack;
	}

	public void setFaithTrack(int faithTrack){
		this.faithTrack = faithTrack;
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

	public void setResAll(EnumMap<Colours, Integer> resAll){
		this.resAll = resAll;
	}

	public boolean[] getActiveLeader(){
		return activeLeader;
	}

	public void setActiveLeader(boolean[] activeLeader){
		this.activeLeader = activeLeader;
	}

	public String getNickname(){
		return nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
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

	public Colours[][] getWarehouse(){
		return warehouse;
	}

	public void setWarehouse(Colours[][] warehouse){
		this.warehouse = warehouse;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}
}
