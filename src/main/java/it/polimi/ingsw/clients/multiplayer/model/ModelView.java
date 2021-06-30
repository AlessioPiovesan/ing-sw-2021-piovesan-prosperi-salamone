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

    private final ModelBoard[] modelBoards;
    private final ArrayList<CardDev> cardDevDeck;
    private Colours[][] market;
    private Colours externalMarble;

    public ModelView(int nPlayers, UI ui){
        modelBoards = new ModelBoard[nPlayers];
        for(var i = 0; i < nPlayers; i++){
            modelBoards[i] = new ModelBoard();
        }
        ui.setModelView(this);
        cardDevDeck = new ArrayList<>();
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

    public ModelBoard getTheirBoards(int i){
        return modelBoards[i];
    }

    public List<CardDev> getCardDevDeck(){
        return cardDevDeck;
    }

    public void cardBrought(CardDev card){
        cardDevDeck.remove(card);
    }
}
