package it.polimi.ingsw.Server.model_Server.Accessori;

public enum Constants{
    XMARKET(4),
    YMARKET(3),
    MAXTRACK(20),
    MAXMARBLESW(4),
    MAXMARBLESB(2),
    MAXMARBLESG(2),
    MAXMARBLESY(2),
    MAXMARBLESP(2),
    MAXMARBLESR(1);

    private final int val;

    Constants (int num){
        val = num;
    }

    public int getVal ( ){
        return val;
    }
}
