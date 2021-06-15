package it.polimi.ingsw.server.accessories;

/**
 * class Pick is the one which associates number of resources to its type
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
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
