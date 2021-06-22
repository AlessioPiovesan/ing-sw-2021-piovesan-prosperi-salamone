package it.polimi.ingsw.clients.singleplayer.controller;

import it.polimi.ingsw.clients.singleplayer.model.LocalGame;
import it.polimi.ingsw.clients.singleplayer.model.Lorenzo;
import it.polimi.ingsw.clients.singleplayer.model.SoloAction;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.game.Market;

import java.util.LinkedList;
import java.util.List;

/**
 * class LocalGameController is the controller class for the local single player game.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class LocalGameController{

    private static LocalGameController singleInstance;
    private final LocalGame game;
    private final LocalBoardController boardController;
    private final LinkedList<SoloAction> listSoloAction;
    private boolean playingPlayer;

    private LocalGameController(){
        this.game = new LocalGame(new Market());
        var lorenzo = (Lorenzo)game.getBoard(true);
        var player = game.getBoard(false);
        this.boardController = new LocalBoardController(player, lorenzo);
        listSoloAction = new LinkedList<>(SoloAction.shuffle());
    }

    public static LocalGameController getInstance(){
        if(singleInstance == null) singleInstance = new LocalGameController();
        return singleInstance;
    }

    /**
     * method moveLorenzo is the method responsible for any Lorenzo's move.
     *
     * @see #nextPlayer()
     */
    public void moveLorenzo(){
        var sa = listSoloAction.get(0);
        var message = SoloAction.printSASingle(sa);
        boardController.lorenzo().printAction(message);
        SoloAction.action(sa);
        SoloAction.pickSoloAction(listSoloAction);
        nextPlayer();
    }

    /**
     * method nextPlayer changes the playing user from Lorenzo to the active user.
     */
    public synchronized void nextPlayer(){
        playingPlayer = !playingPlayer;
        boardController.active();
    }

    public LocalGame getGame(){
        return game;
    }

    public LocalBoardController getBoardController(){
        return boardController;
    }

    /**
     * method startGame starts the game.
     */
    public void startGame(){
        playingPlayer = false;
        game.myLeaderCardsHandler();
    }

    public boolean isLorenzoPlaying(){
        return playingPlayer;
    }

    /**
     * method marketAction handles market choices and movements.
     *
     * @param moveMarket
     * @see it.polimi.ingsw.server.model.board.Board#marketResHandler(List)
     * @see Market#moveMarbles(int)
     */
    public void marketAction(int moveMarket){
        var b = boardController.player();
        var m = game.getMarket();
        /*move the marbles of the market*/
        LinkedList<Colours> res = (LinkedList<Colours>)m.moveMarbles(moveMarket);
        /*call to the method that handles the new generated resources*/
        b.marketResHandler(res);
    }

    /**
     * method addFaithPnt is used to move forward on the faith track
     *
     * @param waste
     * @see it.polimi.ingsw.server.model.board.Board#moveFT(int)
     */
    public void addFaithPnt(List<Colours> waste){
        int a = waste.size();
        boardController.lorenzo().moveFT(a);
    }

    /**
     * cardLeadAction handles all of the Leader Cards' powers.
     * 01,02,03,04: Resource discount
     * 05,06,07,08: Adds a special depot with two additional spaces for resources.
     * 09,10,11,12: White marble becomes the specified resource.
     * 13,14,15,16: Additional production power.
     *
     * @param cardToBeActivated
     * @see it.polimi.ingsw.server.model.board.Board#setDiscount(Colours)
     * @see it.polimi.ingsw.server.model.board.Board#addLeaderSpace(Colours)
     * @see it.polimi.ingsw.server.model.board.Board#setMarketAlt(Colours)
     * @see it.polimi.ingsw.server.model.board.Board#setProductAlt(Colours)
     */
    public synchronized void cardLeadAction(CardLeader cardToBeActivated){
        switch(cardToBeActivated){
            case CARDLEAD01, CARDLEAD02, CARDLEAD03, CARDLEAD04 -> game.getBoard(false).setDiscount(cardToBeActivated.getSpecial());
            case CARDLEAD05, CARDLEAD06, CARDLEAD07, CARDLEAD08 -> game.getBoard(false).addLeaderSpace(cardToBeActivated.getSpecial());
            case CARDLEAD09, CARDLEAD10, CARDLEAD11, CARDLEAD12 -> game.getBoard(false).setMarketAlt(cardToBeActivated.getSpecial());
            case CARDLEAD13, CARDLEAD14, CARDLEAD15, CARDLEAD16 -> game.getBoard(false).setProductAlt(cardToBeActivated.getSpecial());
            default -> boardController.player().somethingIsWrong("This card should not exist");
        }
    }

    /**
     * cardDevAction is the action the player makes when he buys a dev card.
     *
     * @param cardToBeBought
     * @param slot
     * @see it.polimi.ingsw.server.model.board.Board#buyCardDev(CardDev, int)
     */
    public void cardDevAction(CardDev cardToBeBought, int slot){
        var b = boardController.player();
        game.getCardDevDeck().remove(cardToBeBought);
        b.buyCardDev(cardToBeBought, slot);
    }

    public void tossCardDev(ColoursCards colour){
        var deck = game.getCardDevDeck();
        CardDev found = null;
        for(CardDev cardDev : deck)
            for(var i = 0; i < 3; i++)
                if(cardDev.getLvl() == 1 && cardDev.getColour() == colour){
                    found = cardDev;
                    break;
                }
        if(found == null){
            endGame(false);
        }
    }

    /**
     * method endGame counts winning points for the single player in order to handle personal records
     *
     * @param winner
     * @return
     * @see Board#countWinPnt()
     */
    public String endGame(boolean winner){
        if(winner){
            var win = boardController.player().countWinPnt();
            return "YOU WON!\nYour record is " + win + " points!";
        }
        else return "YOU LOSE";
    }
}
