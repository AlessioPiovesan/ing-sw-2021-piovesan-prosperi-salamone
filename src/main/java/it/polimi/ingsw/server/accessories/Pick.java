package it.polimi.ingsw.server.accessories;

public class Pick{

    private final Colours resource;
    private final int qnt;

    public Pick(Colours resource, int qnt){
        this.resource = resource;
        this.qnt = qnt;
    }

    public Colours getResource(){
        return resource;
    }

    public int getQnt(){
        return qnt;
    }
}
