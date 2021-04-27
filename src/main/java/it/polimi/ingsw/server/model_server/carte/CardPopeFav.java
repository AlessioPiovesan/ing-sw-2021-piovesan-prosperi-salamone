package it.polimi.ingsw.server.model_server.carte;

public enum CardPopeFav implements Card{
    cardPopeFav21(2),
    cardPopeFav22(2),
    cardPopeFav23(2),
    cardPopeFav31(3),
    cardPopeFav32(3),
    cardPopeFav33(3),
    cardPopeFav41(4),
    cardPopeFav42(4),
    cardPopeFav43(4);

    private final int winPnt;
    private boolean flipped;
    private int pos;

    CardPopeFav (int winPnt){
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public int getPos ( ){
        return pos;
    }

    protected void setPos (int p){
        pos = p;
    }

    public boolean getFlipped ( ){
        return flipped;
    }

    @Override
    public int getWinPnt ( ){
        return winPnt;
    }

    @Override
    public void flipCard ( ){
        flipped = !flipped;
    }
}
