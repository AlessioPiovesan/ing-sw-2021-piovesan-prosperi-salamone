package it.polimi.ingsw.server.model_server.carte;

public enum CardPopeFav implements Card{
    card_P21(2),
    card_P22(2),
    card_P23(2),
    card_P31(3),
    card_P32(3),
    card_P33(3),
    card_P41(4),
    card_P42(4),
    card_P43(4);

    private final int winPnt;
    public boolean flipped;
    private int pos;

    CardPopeFav (int winPnt){
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public int getPos ( ){
        return pos;
    }

    public void setPos (int p){
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
