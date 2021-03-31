package it.polimi.ingsw.Server.model_Server.Carte;


import it.polimi.ingsw.Server.model_Server.Accessori.Colours;

public enum CardDev implements Card{

    CARD_DEV1G1 (1, Colours.GREEN, new Colours[]{Colours.BLUE, Colours.BLUE}, new Colours[]{Colours.YELLOW}, new Colours[]{Colours.RED}, 1);

    private int lvl;
    private Colours c;
    private Colours[] cost;
    private Colours[] resFrom;
    private Colours[] resTo;
    private int winPnt;
    boolean flipped;

    CardDev (int lvl, Colours c, Colours[] cost, Colours[] resFrom, Colours[] resTo, int winPnt){
        this.lvl = lvl;
        this.c = c;
        this.cost = cost;
        this.resFrom = resFrom;
        this.resTo = resTo;
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public int getLvl(){return lvl;}
    public Colours getC (){return c;}
    public Colours[] getCost (){return cost;}
    public Colours[] getResFrom (){return resFrom;}
    public Colours[] getResTo (){return resTo;}

    @Override
    public int getWinPnt (){return winPnt;}
    @Override
    public boolean getFlipped ( ){return flipped;}
    @Override
    public void flipCard(){flipped= !flipped;}

}
