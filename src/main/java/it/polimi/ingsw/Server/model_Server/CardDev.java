package it.polimi.ingsw.Server.model_Server;


enum CardDev implements Card{
    CARD_DEV1G1 (1, Colours.GREEN, new Resources[]{Resources.SHEILD, Resources.SHEILD}, new Resources[]{Resources.COIN}, new Resources[]{Resources.FAITH}, 1);

    private int lvl;
    private Colours c;
    private Resources[] cost;
    private Resources[] resFrom;
    private Resources[] resTo;
    private int winPnt;
    boolean flipped;

    CardDev (int lvl, Colours c, Resources[] cost, Resources[] resFrom, Resources[] resTo, int winPnt){
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
    public Resources[] getCost (){return cost;}
    public Resources[] getResFrom (){return resFrom;}
    public Resources[] getResTo (){return resTo;}

    @Override
    public int getWinPnt (){return winPnt;}
    @Override
    public boolean getFlipped ( ){return flipped;}
    @Override
    public void flipCard(){flipped= !flipped;}

}
