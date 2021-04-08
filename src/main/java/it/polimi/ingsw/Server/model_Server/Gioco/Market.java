package it.polimi.ingsw.Server.model_Server.Gioco;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Constants;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public class Market{
    public Colours[][] matrix;
    public Colours external;
    private LinkedList<Colours> marbles = new LinkedList<>();
    private LinkedList<Integer> list = new LinkedList<>();
    private Random random = new Random();

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
        this.list = new LinkedList<>();
        int X = Constants.XMARKET.getVal();
        int Y = Constants.YMARKET.getVal();
        Colours[][] m = new Colours[X][Y];
        for (int j = 0;j < Y;j++){
            for (int i = 0;i < X;i++){
                /*le biglie in tutto sono X * Y*/
                int r = random.nextInt((X * Y) + 1);
                if (list.contains(r))
                    while (list.contains(r))
                        r = random.nextInt((X * Y) + 1);
                list.add(r);
                m[i][j] = marbles.get(r);
            }
        }
        for (int i = 0;i < (X * Y) + 1;i++)
            if (!list.contains(i))
                setExternal(marbles.get(i));
        return m;
    }

    public void setExternal (Colours news){
        external = news;
    }

    public LinkedList<Colours> moveMarbles (int choice){
        LinkedList<Colours> res = new LinkedList<>();
        int X = Constants.XMARKET.getVal();
        int Y = Constants.YMARKET.getVal();
        Colours r1, r2;
        r1 = external;
        switch (choice){
            case 0:{
                external = matrix[0][X - 3];
                for (int i = X;i > 0;i--){
                    r2 = matrix[0][i - 1];
                    res.add(matrix[0][i - 1]);
                    matrix[0][i - 1] = r1;
                    r1 = r2;
                }
                break;
            }
            case 1:{
                external = matrix[0][X - 2];
                for (int i = X;i > 0;i--){
                    r2 = matrix[1][i - 1];
                    res.add(matrix[1][i - 1]);
                    matrix[1][i - 1] = r1;
                    r1 = r2;
                }
                break;
            }
            case 2:{
                external = matrix[0][X - 1];
                for (int i = X;i > 0;i--){
                    r2 = matrix[2][i - 1];
                    res.add(matrix[2][i - 1]);
                    matrix[2][i - 1] = r1;
                    r1 = r2;
                }
                break;
            }
            case 3:{
                external = matrix[Y - 4][0];
                for (int i = Y;i > 0;i--){
                    r2 = matrix[i - 1][3];
                    res.add(matrix[i - 1][3]);
                    matrix[i - 1][3] = r1;
                    r1 = r2;
                }
                break;
            }
            case 4:{
                external = matrix[Y - 3][0];
                for (int i = Y;i > 0;i--){
                    r2 = matrix[i - 1][2];
                    res.add(matrix[i - 1][2]);
                    matrix[i - 1][2] = r1;
                    r1 = r2;
                }
                break;
            }
            case 5:{
                external = matrix[Y - 2][0];
                for (int i = Y;i > 0;i--){
                    r2 = matrix[i - 1][1];
                    res.add(matrix[i - 1][1]);
                    matrix[i - 1][1] = r1;
                    r1 = r2;
                }
                break;
            }
            case 6:{
                external = matrix[Y - 1][0];
                for (int i = Y;i > 0;i--){
                    r2 = matrix[i - 1][0];
                    res.add(matrix[i - 1][0]);
                    matrix[i - 1][0] = r1;
                    r1 = r2;
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        return res;
    }

}
