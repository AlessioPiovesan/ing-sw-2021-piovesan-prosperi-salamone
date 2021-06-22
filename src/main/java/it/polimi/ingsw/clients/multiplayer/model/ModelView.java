package it.polimi.ingsw.clients.multiplayer.model;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class ModelView is the client mirror of the Model on the server side.
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class ModelView{

    private final OtherBoard[] theirBoards;
    private final ThisBoard myBoard;
    private final int nPlayers;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Colours[][] market;
    private Colours externalMarble;
    private HashMap<String, OtherBoard> boardMap;
    private int gameID;

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
    //				if(model)
    //					LocalGameController.getInstance().getGame().getBoard(false).setMyLeaderCards((CardLeader[])evt.getNewValue());
    //				else remoteView.setMyLeaderCards((CardLeader[])evt.getNewValue());
    //			}
    //			case "remoteView" -> setRemoteView((RemoteView)evt.getNewValue());
    //			case "model" -> model = (boolean)evt.getNewValue();
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

    /**
     * populateThis populates the playing player's board with dev cards, warehouse, faith track, strongbox, library, resources and leader cards
     * @param myDevCards
     * @param warehouse
     * @param faithTrack
     * @param strongBox
     * @param library
     * @param resAll
     * @param myLeaderCards
     */
    public void populateThis(List<CardDev> myDevCards, Colours[][] warehouse, int faithTrack, Map<Colours, Integer> strongBox, CardDev[] library, Map<Colours, Integer> resAll, CardLeader[] myLeaderCards){
        myBoard.setMyDevCards(myDevCards);
        myBoard.setWarehouse(warehouse);
        myBoard.setFaithTrack(faithTrack);
        myBoard.setStrongBox(strongBox);
        myBoard.setLibrary(library);
        myBoard.setResAll(resAll);
        myBoard.setMyLeaderCards(myLeaderCards);
    }

    /**
     * populateThat populates the remaining players' boards that are not playing.
     * @param name
     * @param warehouse
     * @param faithTrack
     * @param library
     * @param resAll
     * @param myLeaderCards
     */
    public void populateThat(String name, Colours[][] warehouse, int faithTrack, CardDev[] library, Map<Colours, Integer> resAll, CardLeader[] myLeaderCards){
        var board = boardMap.get(name);
        board.setWarehouse(warehouse);
        board.setFaithTrack(faithTrack);
        board.setLibrary(library);
        board.setResAll(resAll);
        board.setMyLeaderCards(myLeaderCards);
    }

    public void setMyNick(String name){
        myBoard.setNickname(name);
    }

    public void setTheirNick(String name){
        for(OtherBoard board : theirBoards)
            if(board.getNickname().equals("")){
                board.setNickname(name);
                boardMap.put(name, board);
            }
    }

    public void setGameID(int n){
        gameID = n;
        pcs.firePropertyChange("gameID", "", gameID);
    }
}
