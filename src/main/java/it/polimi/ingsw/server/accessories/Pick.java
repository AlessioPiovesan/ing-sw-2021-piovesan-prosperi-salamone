package it.polimi.ingsw.server.accessories;

public class Pick {
    private final int qnt;
    private final Colours resource;

    public Pick(Colours resource, int qnt) {
        this.resource = resource;
        this.qnt = qnt;
    }

    public Colours getResource() {
        return resource;
    }

    public int getQnt() {
        return qnt;
    }
}
