package it.polimi.ingsw.Server.model_Server.Gioco;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Constants;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public class Market{
    public Colours[][] matrix;
    public Colours external;
    private final LinkedList<Colours> marbles = new LinkedList<>();
    private final Random random = new Random();

    public Market ( ){
        /*creo le biglie*/
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESB.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.BLUE));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESG.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.GREY));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESP.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.PURPLE));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESR.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.RED));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESW.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.WHITE));
        IntStream.range(this.marbles.size(), Constants.MAXMARBLESY.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.YELLOW));
        /*metto le biglie nel mercato*/
        this.matrix = fillMarket(marbles);
        /*il mercato verrà gestito con la cella [0][0] in alto a destra*/
    }

    private Colours[][] fillMarket (LinkedList<Colours> marbles){
        LinkedList<Integer> list= new LinkedList<>();
        int X=Constants.XMARKET.getVal();
        int Y=Constants.YMARKET.getVal();
        Colours[][] m= new Colours[X][Y];
        for (int j = 0;j < Y;j++){
            fillColumns(marbles, list, X, Y, m, j);
        }
        findFirstExternal(marbles, list, X, Y);
        return m;
    }

    private void findFirstExternal (LinkedList<Colours> marbles, LinkedList<Integer> list, int X, int Y){
        for (int i = 0;i < (X * Y) + 1;i++)
            if (!list.contains(i))
                external = marbles.get(i);
    }

    private void fillColumns (LinkedList<Colours> marbles, LinkedList<Integer> list, int X, int Y, Colours[][] m, int j){
        for (int i = 0;i < X;i++){
            int r = findNextMarble(list, X, Y);
            list.add(r);
            m[i][j] = marbles.get(r);
        }
    }

    private int findNextMarble (LinkedList<Integer> list, int X, int Y){
        int r = random.nextInt((X * Y) + 1);
        if (list.contains(r))
            while (list.contains(r))
                r = random.nextInt((X * Y) + 1);
        return r;
    }

    protected void setExternal (Colours news){ external=news; }

    public LinkedList<Colours> moveMarbles (int choice){
        LinkedList<Colours> res;
        Colours r1, r2;
        r1 = external;
        switch (choice){
            case 0:{
                external = matrix[0][Constants.YMARKET.getVal() - 3];
                res=getRes(5,Constants.YMARKET.getVal()-3);
                moveY(r1, 3);
                break;
            }
            case 1:{
                external = matrix[0][Constants.YMARKET.getVal() - 2];
                res=getRes(5, Constants.YMARKET.getVal()-2);
                moveY(r1, 2);
                break;
            }
            case 2:{
                external = matrix[0][Constants.YMARKET.getVal() - 1];
                res=getRes(5, Constants.YMARKET.getVal()-1);
                moveY(r1, 1);
                break;
            }
            case 3:{
                external = matrix[Constants.XMARKET.getVal() - 1][0];
                res=getRes(Constants.XMARKET.getVal()-1,5);
                moveX(r1, 1);
                break;
            }
            case 4:{
                external = matrix[Constants.XMARKET.getVal() - 2][0];
                res=getRes(Constants.XMARKET.getVal()-2,5);
                moveX(r1, 2);
                break;
            }
            case 5:{
                external = matrix[Constants.XMARKET.getVal() - 3][0];
                res=getRes(Constants.XMARKET.getVal()-3,5);
                moveX(r1, 3);
                break;
            }
            case 6:{
                external = matrix[Constants.XMARKET.getVal() - 4][0];
                res=getRes(Constants.XMARKET.getVal()-4,5);
                moveX(r1, 4);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        return res;
    }

    private void moveX (Colours r1, int j){
        Colours r2;
        for (int i = Constants.YMARKET.getVal();i > 0;i--){
            r2 = matrix[Constants.XMARKET.getVal() - j][i - 1];
            matrix[Constants.XMARKET.getVal() - j][i - 1] = r1;
            r1 = r2;
        }
    }

    private void moveY (Colours r1, int j){
        Colours r2;
        for (int i = Constants.XMARKET.getVal();i > 0;i--){
            r2 = matrix[i - 1][Constants.YMARKET.getVal() - j];
            matrix[i - 1][Constants.YMARKET.getVal() - j] = r1;
            r1 = r2;
        }
    }

    private LinkedList<Colours> getRes(int X, int Y){
        LinkedList<Colours> res = new LinkedList<>();
        if (X>Constants.XMARKET.getVal()){
            for (int i = 0;i < Constants.XMARKET.getVal();i++){
                res.add(matrix[i][Y]);
            }
        }
        if(Y>Constants.YMARKET.getVal()){
            res.addAll(Arrays.asList(matrix[X]).subList(0, Constants.YMARKET.getVal()));
        }
        return res;
    }

}
