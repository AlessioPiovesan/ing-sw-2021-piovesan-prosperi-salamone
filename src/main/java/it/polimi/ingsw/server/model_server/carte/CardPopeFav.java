package it.polimi.ingsw.server.model_server.carte;

public enum CardPopeFav implements Card {
    CARDPOPEFAV21(2),
    CARDPOPEFAV22(2),
    CARDPOPEFAV23(2),
    CARDPOPEFAV31(3),
    CARDPOPEFAV32(3),
    CARDPOPEFAV33(3),
    CARDPOPEFAV41(4),
    CARDPOPEFAV42(4),
    CARDPOPEFAV43(4);

    private final int winPnt;
    private boolean flipped;
    private int pos;

    CardPopeFav(int winPnt) {
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public int getPos() {
        return pos;
    }

    protected void setPos(int p) {
        pos = p;
    }

    public boolean getFlipped() {
        return flipped;
    }

    @Override
    public int getWinPnt() {
        return winPnt;
    }

    @Override
    public void flipCard() {
        flipped = !flipped;
    }
}
