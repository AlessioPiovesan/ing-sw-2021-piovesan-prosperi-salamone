package it.polimi.ingsw.client.accessori;

public class Pick{
    private int qnt;
    private Colours resource;

    public Pick (Colours resource, int qnt){
        this.resource = resource;
        this.qnt = qnt;
    }

    public Colours getResource ( ){
        return resource;
    }

    public int getQnt ( ){
        return qnt;
    }

}
