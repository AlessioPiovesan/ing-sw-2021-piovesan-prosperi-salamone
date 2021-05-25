package it.polimi.ingsw.clients.multiplayer.model.single;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeListener;
import java.util.*;

public class ModelView{

	private final OtherBoard[] theirBoards;
	private final ThisBoard myBoard;
	private final int nPlayers;
	private Colours[][] market;
	private Colours externalMarble;
	private HashMap<String, OtherBoard> boardMap;

	public ModelView(int nPlayers, UI ui){
		this.nPlayers = nPlayers;
		myBoard = new ThisBoard();
		theirBoards = new OtherBoard[nPlayers];
		for(var i = 0; i < nPlayers; i++){
			theirBoards[i] = new OtherBoard();
		}
		this.addPropertyChangeListener(ui);
	}

	//	@Override
	//	public void propertyChange(PropertyChangeEvent evt){
	//		System.out.println(evt);
	//		switch(evt.getPropertyName()){
	//			case "myLeaderCards" -> {
	//				if(single)
	//					LocalGameController.getInstance().getGame().getBoard(false).setMyLeaderCards((CardLeader[])evt.getNewValue());
	//				else remoteView.setMyLeaderCards((CardLeader[])evt.getNewValue());
	//			}
	//			case "remoteView" -> setRemoteView((RemoteView)evt.getNewValue());
	//			case "single" -> single = (boolean)evt.getNewValue();
	//			case "firstOp" -> {
	//				var a = 1;/*Qui dà le risorse nel partita multipleyer*/
	//			}
	//			default -> doNothing();
	//		}
	//	}
	//
	//	private void doNothing(){
	//		try{
	//			wait(10);
	//		} catch(InterruptedException e){
	//			e.printStackTrace();
	//			Thread.currentThread().interrupt();
	//		}
	//	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		myBoard.getPcs().addPropertyChangeListener(listener);
		Arrays.stream(theirBoards).forEachOrdered(board -> board.getPcs().addPropertyChangeListener(listener));
	}

	public Colours[][] getMarket(){
		return market;
	}

	public void setMarket(Colours[][] market){
		this.market = market;
	}

	public void populateThis(List<CardDev> myDevCards, Colours[][] warehouse, int faithTrack, EnumMap strongBox, CardDev[] library, Map<Colours, Integer> resAll, boolean[] activeLeader, CardLeader[] myLeaderCards){
		myBoard.setMyDevCards(myDevCards);
		myBoard.setWarehouse(warehouse);
		myBoard.setFaithTrack(faithTrack);
		myBoard.setStrongBox(strongBox);
		myBoard.setLibrary(library);
		myBoard.setResAll(resAll);
		myBoard.setActiveLeader(activeLeader);
		myBoard.setMyLeaderCards(myLeaderCards);
	}

	public void populateThat(String name, List<CardDev> myDevCards, Colours[][] warehouse, int faithTrack, EnumMap strongBox, CardDev[] library, Map<Colours, Integer> resAll, boolean[] activeLeader, CardLeader[] myLeaderCards){
		var board = boardMap.get(name);
		board.setWarehouse(warehouse);
		board.setFaithTrack(faithTrack);
		board.setLibrary(library);
		board.setResAll(resAll);
		board.setActiveLeader(activeLeader);
		board.setMyLeaderCards(myLeaderCards);
	}

	public void setMyNick(String name){
		myBoard.setNickname(name);
	}

	public void setTheirNick(String name){
		for(OtherBoard board : theirBoards)
			if(board.getNickname() == ""){
				board.setNickname(name);
				boardMap.put(name, board);
			}
	}

}
