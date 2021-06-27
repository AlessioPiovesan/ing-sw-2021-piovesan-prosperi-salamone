package it.polimi.ingsw.clients.multiplayer.model;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.accessories.Colours;

/**
 * Class ModelView is the client mirror of the Model on the server side.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class ModelView{

    private final ThisBoard myBoard;
    private final OtherBoard[] theirBoards;
    private final int nPlayers;
    private Colours[][] market;
    private Colours externalMarble;
    private int gameID;

    public ModelView(int nPlayers, UI ui){
        this.nPlayers = nPlayers;
        myBoard = new ThisBoard();
        theirBoards = new OtherBoard[nPlayers];
        for(var i = 0; i < nPlayers - 1; i++){
            theirBoards[i] = new OtherBoard();
        }
        ui.setModelView(this);
    }

    public Colours getExternalMarble(){
        return externalMarble;
    }

    public void setExternalMarble(Colours externalMarble){
        this.externalMarble = externalMarble;
    }

    public Colours[][] getMarket(){
        return market;
    }

    public void setMarket(Colours[][] market){
        this.market = market;
    }

    public ThisBoard getMyBoard(){
        return myBoard;
    }

    public void setGameID(int gameID){
        this.gameID = gameID;
    }
}
