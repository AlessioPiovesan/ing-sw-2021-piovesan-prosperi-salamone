package it.polimi.ingsw.server.accessories;

/**
 * class Pick is the one which associates number of resources to its type
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public record Pick(Colours resource, int qnt){

    public Colours getResource(){
        return resource;
    }

    public int getQnt(){
        return qnt;
    }
}
