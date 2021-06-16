package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Class Market is used to represent and describe the Market system in the game.
 * Meaning the market formed by a matrix of 3 rows and 4 columns which can move based on the user's action.
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public class Market{

    private final LinkedList<Colours> marbles = new LinkedList<>();
    private final Random random = new Random();
    protected Colours[][] tray;
    private Colours external;

    public Market(){
        /*instantiation of the marbles*/
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESB.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.BLUE));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESG.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.GREY));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESP.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.PURPLE));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESR.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.RED));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESW.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.WHITE));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESY.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.YELLOW));
        /*putting the marbles in the market tray*/
        tray = fillMarket(marbles);
    }

    /**
     * Method fillMarket fills the Market with random marbles (described with Colours) at the start of the game.
     *
     * @param marbles of type List - the marbles used inside the Market
     * @return m of type bi-dimensional Array - returns the filled Market at the start of the game
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    private Colours[][] fillMarket(List<Colours> marbles){
        var list = new LinkedList<Integer>();
        var m = new Colours[Constants.XMARKET.getVal()][Constants.YMARKET.getVal()];
        for(var j = 0; j < Constants.YMARKET.getVal(); j++){
            fillColumns(marbles, list, Constants.XMARKET.getVal(), Constants.YMARKET.getVal(), m, j);
        }
        findFirstExternal(marbles, list);
        return m;
    }

    /**
     * Method fillColumns serves as support for fillMarket.
     * This method uses findNextMarble to fill the Market with a chosen Marble that hasn't been placed before.
     *
     * @param marbles of type List of Colours
     * @param list    of List of Integer - list of indexes of List Marbles that have already been used.
     * @param x       of type int - rows of Market
     * @param y       of type int - columns of Market
     * @param m       of type bi-dimensional Array - Market
     * @param j       of type j - column being filled by the method
     */
    private void fillColumns(List<Colours> marbles, List<Integer> list, int x, int y, Colours[][] m, int j){
        for(var i = 0; i < x; i++){
            int r = findNextMarble(list, x, y);
            list.add(r);
            m[i][j] = marbles.get(r);
        }
    }

    /**
     * Method findFirstExternal places a random marble as the external of the matrix that is later used to move the market.
     *
     * @param marbles of type List - Marbles
     * @param list    of type List
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    private void findFirstExternal(List<Colours> marbles, List<Integer> list){
        for(var i = 0; i < (Constants.XMARKET.getVal() * Constants.YMARKET.getVal()) + 1; i++)
            if(!list.contains(i)) external = marbles.get(i);
    }

    /**
     * Method findNextMarble finds a Marble that hasn't been used before.
     *
     * @param list of type List - used to know which marbles have already been placed
     * @param x    of type int - row of Market
     * @param y    of type int - column of Market
     * @return int - index of the marble that hasn't been placed
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    private int findNextMarble(List<Integer> list, int x, int y){
        var r = random.nextInt((x * y) + 1);
        if(list.contains(r)) while(list.contains(r)) r = random.nextInt((x * y) + 1);
        return r;
    }

    public Colours[][] getTray(){
        return tray;
    }

    public Colours getExternal(){
        return external;
    }

    public void setExternal(Colours res){
        external = res;
    }

    /**
     * Method moveMarbles takes resources from the Market based on the player's choice and uses the method move to move the Market
     *
     * @param choice of type int - is the choice made by the user of either row or column
     * @return List of resources from the Market
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public List<Colours> moveMarbles(int choice){
        LinkedList<Colours> res;
        res = getRes(choice);
        move(choice, external);
        return res;
    }

    /**
     * Method getRes gets the Resources from the market based on the player's choice
     *
     * @param choice of type int - Player's choice
     * @return LinkedList - returns a list of resources from the market
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Market#moveMarbles(int)
     */
    private LinkedList<Colours> getRes(int choice){
        return switch(choice){
            case 0 -> getResX(0);
            case 1 -> getResX(1);
            case 2 -> getResX(2);
            case 6 -> getResY(0);
            case 5 -> getResY(1);
            case 4 -> getResY(2);
            case 3 -> getResY(3);
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        };
    }

    /**
     * Method move moves the Market
     *
     * @param choice of type int - Player's choice
     * @param r1     of type Colours - external Marble
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Market#moveMarbles(int)
     */
    private void move(int choice, Colours r1){
        switch(choice){
            case 0 -> operationMoveX(r1, 3);
            case 1 -> operationMoveX(r1, 2);
            case 2 -> operationMoveX(r1, 1);
            case 3 -> operationMoveY(r1, 1);
            case 4 -> operationMoveY(r1, 2);
            case 5 -> operationMoveY(r1, 3);
            case 6 -> operationMoveY(r1, 4);
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }
    }

    /**
     * Method getResX gets resources from row x
     *
     * @param x of type int - row of market
     * @return LinkedList
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Market#getRes(int)
     */
    private LinkedList<Colours> getResX(int x){
        return new LinkedList<>(Arrays.asList(tray[x]).subList(0, Constants.YMARKET.getVal()));
    }

    /**
     * Method getResY gets resources from column y
     *
     * @param y of type int - column of Market
     * @return LinkedList
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Market#getRes(int)
     */
    private LinkedList<Colours> getResY(int y){
        LinkedList<Colours> res = new LinkedList<>();
        for(var i = 0; i < Constants.XMARKET.getVal(); i++) res.add(tray[i][y]);
        return res;
    }

    /**
     * Method operationMoveX saves the new external marble and then uses the method moveX
     *
     * @param r1 of type Colours - old external marble
     * @param x  of type int - row
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Market#move(int, Colours)
     */
    private void operationMoveX(Colours r1, int x){
        external = tray[Constants.XMARKET.getVal() - x][0];
        moveX(r1, x);
    }

    /**
     * Method operationMoveY saves the new external marble and then uses the method moveY
     *
     * @param r1 of type Colours - old external marble
     * @param y  of type int - column
     * @see Market#move(int, Colours)
     */
    private void operationMoveY(Colours r1, int y){
        external = tray[0][Constants.YMARKET.getVal() - y];
        moveY(r1, y);
    }

    /**
     * Method moveX moves row x
     *
     * @param r1 of type Colours - old external marble
     * @param x  of type int - row
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Market#operationMoveX(Colours, int)
     */
    private void moveX(Colours r1, int x){
        Colours r2;
        for(int i = Constants.YMARKET.getVal(); i > 0; i--){
            r2 = tray[Constants.XMARKET.getVal() - x][i - 1];
            tray[Constants.XMARKET.getVal() - x][i - 1] = r1;
            r1 = r2;
        }
    }

    /**
     * Method moveY moves column Y
     *
     * @param r1 of type Colours - old external marble
     * @param y  of type int - column
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Market#operationMoveY(Colours, int)
     */
    private void moveY(Colours r1, int y){
        Colours r2;
        for(int i = Constants.XMARKET.getVal(); i > 0; i--){
            r2 = tray[i - 1][Constants.YMARKET.getVal() - y];
            tray[i - 1][Constants.YMARKET.getVal() - y] = r1;
            r1 = r2;
        }
    }

}
