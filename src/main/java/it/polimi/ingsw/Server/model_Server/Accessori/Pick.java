package it.polimi.ingsw.Server.model_Server.Accessori;

public class Pick {
    Colours r;

    public Colours getR ( ) {
        return r;
    }

    public int getQnt ( ) {
        return qnt;
    }

    public int qnt;

    public Pick ( Colours r, int qnt ) {
        this.r = r;
        this.qnt = qnt;
    }

    public void setR ( Colours r ) {
        this.r = r;
    }

    public void setQnt ( int qnt ) {
        this.qnt = qnt;
    }
}
