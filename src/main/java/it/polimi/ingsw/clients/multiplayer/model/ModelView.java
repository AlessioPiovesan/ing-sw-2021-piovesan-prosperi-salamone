package it.polimi.ingsw.clients.multiplayer.model;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Colours[][] market;
    private Colours externalMarble;
    private ArrayList<CardDev> cardDevDeck;

    public ModelView(int nPlayers, UI ui){
        myBoard = new ThisBoard();
        theirBoards = new OtherBoard[nPlayers];
        for(var i = 0; i < nPlayers - 1; i++){
            theirBoards[i] = new OtherBoard();
        }
        ui.setModelView(this);
        cardDevDeck.addAll(Arrays.asList(CardDev.values()));
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

    public void setMyBoard(int i){
        theirBoards[i] = myBoard;
    }

    public OtherBoard getTheirBoards(int i){
        return theirBoards[i];
    }

    public List<CardDev> getCardDevDeck(){
        return cardDevDeck;
    }

    public void cardBrought(CardDev card){
        cardDevDeck.remove(card);
    }
}
