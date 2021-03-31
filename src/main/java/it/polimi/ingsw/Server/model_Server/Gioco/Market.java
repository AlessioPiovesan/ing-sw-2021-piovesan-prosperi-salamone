package it.polimi.ingsw.Server.model_Server.Gioco;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Constants;

import java.util.LinkedList;
import java.util.Random;

public class Market{
    Colours[][] matrix;
    Colours external;
    LinkedList<Colours> marbles;
    LinkedList<Integer> list;
    Random random;

    {
        random = new Random ();
    }

    Market (int maxX, int maxY){
        /*creo le biglie*/
        for (int i = (int)this.marbles.size ();i < Constants.MAXMARBLESB.getVal () + this.marbles.size ();i++)
            this.marbles.add (Colours.BLUE);
        for (int i = (int)this.marbles.size ();i < Constants.MAXMARBLESG.getVal () + this.marbles.size ();i++)
            this.marbles.add (Colours.GREY);
        for (int i = (int)this.marbles.size ();i < Constants.MAXMARBLESP.getVal () + this.marbles.size ();i++)
            this.marbles.add (Colours.PURPLE);
        for (int i = (int)this.marbles.size ();i < Constants.MAXMARBLESR.getVal () + this.marbles.size ();i++)
            this.marbles.add (Colours.RED);
        for (int i = (int)this.marbles.size ();i < Constants.MAXMARBLESW.getVal () + this.marbles.size ();i++)
            this.marbles.add (Colours.WHITE);
        for (int i = (int)this.marbles.size ();i < Constants.MAXMARBLESY.getVal () + this.marbles.size ();i++)
            this.marbles.add (Colours.YELLOW);
        /*metto le biglie nel mercato*/
        this.matrix = fillMarket (maxX, maxY, marbles);
        /*segno qual è la 13sima biglia*/
        this.external = setExternal (list, marbles);
        /*il mercato verrà gestito con la cella [0][0] in alto a destra*/
    }

    private Colours[][] fillMarket (int X, int Y, LinkedList<Colours> marbles){
        this.list = new LinkedList<> ();
        for (int j = 0;j < Y;j++){
            for (int i = 0;i < X;i++){
                /*le biglie in tutto sono X * Y*/
                int r = random.nextInt (X * Y);
                while (!list.contains (r)){
                    list.add (r);
                    matrix[i][j] = marbles.get (r);
                    r = random.nextInt (X * Y);
                }
            }
        }
        return matrix;
    }

    protected Colours setExternal (LinkedList<Integer> list, LinkedList<Colours> marbles){
        /*cerca nella lista delle biglie la tredicesima biglia, ecludendo le dodici che stanno nella lista di biglie che sono nel mercato*/
        return external;
    }

    private Colours[] moveMarbles (Market market, int X, int Y, int choice){
        Colours[] res;
        Colours r1, r2;
        r1 = market.external;
        switch (choice){
            case 0:{
                market.external = matrix[X - 3][0];
                for (int i = Y;i > 0;i--){
                    r2 = matrix[0][i - 1];
                    matrix[0][i - 1] = r1;
                    r1 = r2;
                }
            }
            case 1:{
                market.external = matrix[X - 2][0];
                for (int i = Y;i > 0;i--){
                    r2 = matrix[1][i - 1];
                    matrix[1][i - 1] = r1;
                    r1 = r2;
                }
            }
            case 2:{
                market.external = matrix[X - 1][0];
                for (int i = Y;i > 0;i--){
                    r2 = matrix[2][i - 1];
                    matrix[2][i - 1] = r1;
                    r1 = r2;
                }
            }
            case 3:{
                market.external = matrix[0][Y - 1];
                for (int i = X;i > 0;i--){
                    r2 = matrix[i - 1][3];
                    matrix[i - 1][3] = r1;
                    r1 = r2;
                }
            }
            case 4:{
                market.external = matrix[0][Y - 2];
                for (int i = X;i > 0;i--){
                    r2 = matrix[i - 1][2];
                    matrix[i - 1][2] = r1;
                    r1 = r2;
                }
            }
            case 5:{
                market.external = matrix[0][Y - 3];
                for (int i = X;i > 0;i--){
                    r2 = matrix[i - 1][1];
                    matrix[i - 1][1] = r1;
                    r1 = r2;
                }
            }
            case 6:{
                market.external = matrix[0][Y - 4];
                for (int i = X;i > 0;i--){
                    r2 = matrix[i - 1][0];
                    matrix[i - 1][0] = r1;
                    r1 = r2;
                }
            }
            default:
                throw new IllegalStateException ("Unexpected value: " + choice);
        }


    }

}
