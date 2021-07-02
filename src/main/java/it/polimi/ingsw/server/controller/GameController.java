package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.game.Game;

import java.util.LinkedList;

/**
 * Class GameController is the controller that handles one game.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class GameController{

    private final BoardController[] boardController;
    private final int id;
    private final Game game;
    private final int[] win;


    public GameController(int newID, int gamers, Game game){
        id = newID;
        this.game = game;
        win = new int[gamers];
        boardController = new BoardController[gamers];
    }

    /**
     * Method setBoardController creates boards based on the number of players.
     * links a board to his board controller.
     *
     * @see Game#getNGamers()
     * @see Game#getSingleB(int)
     */
    public void setBoardController(int i, BoardController boardController){
        this.boardController[i] = boardController;
    }

    /**
     * Method addFaithPnt gives Faith Points to the players not playing if the current player decides
     * to discard some resources.
     *
     * @param waste of type Colours
     * @see Board#moveFT(int)
     */
    public void addFaithPnt(int waste){
        for(var j = 0; j < game.getNGamers(); j++){
            if(j != game.getPlayingPlayer()){
                game.getSingleB(j).moveFT(waste);
            }
        }
    }

    /**
     * Method cardDevAction assigns a card to a player and deletes the same card from the deck
     *
     * @param cardToBeBought is the card that the selected player wants to buy
     * @param slot           is the library slot where the card will be placed
     */
    public void cardDevAction(CardDev cardToBeBought, int slot){
        Board b = game.getSingleB(game.getPlayingPlayer());
        game.getCardDevDeck().remove(cardToBeBought);
        b.buyCardDev(cardToBeBought, slot);
    }

    /**
     * Method nextPlayer end the turn of a player and start the turn of the next
     *
     * @see Game#checkNext()
     */
    public void nextPlayer(){
        game.nextReady();
        var next = game.checkNext();
        game.setPlayingPlayer(next);
        game.nextReady();
    }

    public BoardController getBoardController(int id){return boardController[id];}

    /**
     * Method cardLeadAction activates a leader card special ability
     *
     * @param cardToBeActivated the card of which power is about to be activate
     */
    public void cardLeadAction(CardLeader cardToBeActivated){
        cardToBeActivated.flipCard();
        switch(cardToBeActivated){
            case CARDLEAD01, CARDLEAD02, CARDLEAD03, CARDLEAD04:{
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setDiscount(cardToBeActivated.getSpecial());
                break;
            }
            case CARDLEAD05, CARDLEAD06, CARDLEAD07, CARDLEAD08:{
                var b = game.getSingleB(game.getPlayingPlayer());
                b.addLeaderSpace(cardToBeActivated.getSpecial());
                break;
            }
            case CARDLEAD09, CARDLEAD10, CARDLEAD11, CARDLEAD12:{
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setMarketAlt(cardToBeActivated.getSpecial());
                break;
            }
            case CARDLEAD13, CARDLEAD14, CARDLEAD15, CARDLEAD16:{
                Board b = game.getSingleB(game.getPlayingPlayer());
                b.setProductAlt(cardToBeActivated.getSpecial());
                break;
            }
            default:
        }
    }

    /**
     * Method startGame is used to start a Game
     *
     * @see Game#checkNext()
     * @see #leaderCardsHandler()
     */
    public void startGame(){
        leaderCardsHandler();
    }

    /**
     * Method leaderCardsHandler starts the distribution of the leader cards at the beginning of a game
     *
     * @see Game#myLeaderCardsHandler()
     */
    public void leaderCardsHandler(){
        game.myLeaderCardsHandler();
    }

    /**
     * Method endGame counts the points for every player
     *
     * @see Board#countWinPnt()
     */
    public void endGame(){
        var i = 0;
        while(i < game.getNGamers()){
            for(var b : game.getAllB()){
                win[i] += b.countWinPnt();
                i++;
            }
        }
    }

    /**
     * Method initialResourcesHandler starts the method in the class Game giving every player the starting resources
     *
     * @see Game#pickStartingResources()
     */
    public void initialResourcesHandler(){
        game.pickStartingResources();
    }

    /**
     * Method marketAction takes the correct resources from the market and pass them to the board
     *
     * @param moveMarket of type int is the code of the move acted on the market
     */
    public void marketAction(int moveMarket){
        var board = game.getSingleB(game.getPlayingPlayer());
        var market = game.getMarket();
        LinkedList<Colours> res = (LinkedList<Colours>)market.moveMarbles(moveMarket);
        board.marketResHandler(res);
    }

    public Game getGame(){
        return game;
    }

    public int getId(){
        return id;
    }

    public int[] getWin(){
        return win;
    }

    public BoardController[] getAllBoardController(){
        return boardController;
    }
}
