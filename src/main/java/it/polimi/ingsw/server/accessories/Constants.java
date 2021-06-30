package it.polimi.ingsw.server.accessories;

public enum Constants{

    MAXMARBLESB(2), MAXMARBLESG(2), MAXMARBLESP(2), MAXMARBLESR(1), MAXMARBLESW(4), MAXMARBLESY(2), MAXTRACK(24),
    XMARKET(3), YMARKET(4);

    private final int val;

    Constants(int num){
        val = num;
    }

    public int getVal(){
        return val;
    }
}
