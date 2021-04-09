package it.polimi.ingsw.Server.model_Server.Accessori;

public class Pick{
    public int qnt;
    Colours r;

    public Pick (Colours r, int qnt){
        this.r = r;
        this.qnt = qnt;
    }

    public Colours getR ( ){
        return r;
    }

    /*public void setR (Colours r){
        this.r = r;
    }*/

    public int getQnt ( ){
        return qnt;
    }

    /*public void setQnt (int qnt){
        this.qnt = qnt;
    }*/
}
