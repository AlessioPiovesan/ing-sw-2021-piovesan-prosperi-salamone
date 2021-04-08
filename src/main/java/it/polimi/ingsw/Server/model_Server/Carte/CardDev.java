package it.polimi.ingsw.Server.model_Server.Carte;


import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Pick;

public enum CardDev implements Card{

    /*DEV1G1 stands for first level 1 green DEV card*/
    CARD_DEV1G1(1, Colours.GREEN, new Pick[]{new Pick(Colours.BLUE, 2)}, new Colours[]{Colours.YELLOW}, new Colours[]{Colours.RED}, 1),
    CARD_DEV2G1(1, Colours.GREEN, new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.BLUE, 2)}, new Colours[]{Colours.GREY, Colours.PURPLE}, new Colours[]{Colours.YELLOW, Colours.YELLOW, Colours.RED}, 4),
    CARD_DEV3G1(1, Colours.GREEN, new Pick[]{new Pick(Colours.BLUE, 3)}, new Colours[]{Colours.PURPLE, Colours.PURPLE}, new Colours[]{Colours.BLUE, Colours.YELLOW, Colours.GREY}, 3),
    CARD_DEV1P1(1, Colours.PURPLE, new Pick[]{new Pick(Colours.PURPLE, 3)}, new Colours[]{Colours.YELLOW, Colours.YELLOW}, new Colours[]{Colours.PURPLE, Colours.BLUE, Colours.GREY}, 3),
    CARD_DEV2P1(1, Colours.PURPLE, new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.PURPLE, 2)}, new Colours[]{Colours.GREY, Colours.BLUE}, new Colours[]{Colours.GREY, Colours.GREY, Colours.RED}, 4),
    CARD_DEV3P1(1, Colours.PURPLE, new Pick[]{new Pick(Colours.PURPLE, 2)}, new Colours[]{Colours.GREY}, new Colours[]{Colours.RED}, 1),
    CARD_DEV1B1(1, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 3)}, new Colours[]{Colours.GREY, Colours.GREY}, new Colours[]{Colours.YELLOW, Colours.PURPLE, Colours.BLUE}, 3),
    CARD_DEV2B1(1, Colours.BLUE, new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.YELLOW, 2)}, new Colours[]{Colours.BLUE, Colours.GREY}, new Colours[]{Colours.RED, Colours.PURPLE, Colours.PURPLE}, 4),
    CARD_DEV3B1(1, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 2)}, new Colours[]{Colours.BLUE}, new Colours[]{Colours.RED}, 1),
    CARD_DEV1Y1(1, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 3)}, new Colours[]{Colours.BLUE, Colours.BLUE}, new Colours[]{Colours.YELLOW, Colours.PURPLE, Colours.GREY}, 3),
    CARD_DEV2Y1(1, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.BLUE, 2)}, new Colours[]{Colours.YELLOW, Colours.PURPLE}, new Colours[]{Colours.RED, Colours.BLUE, Colours.BLUE}, 4),
    CARD_DEV3Y1(1, Colours.YELLOW, new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1), new Pick(Colours.YELLOW, 1)}, new Colours[]{Colours.BLUE}, new Colours[]{Colours.YELLOW}, 2),
    CARD_DEV1G2(2, Colours.GREEN, new Pick[]{new Pick(Colours.BLUE, 4)}, new Colours[]{Colours.GREY}, new Colours[]{Colours.RED, Colours.RED}, 5),
    CARD_DEV2G2(2, Colours.GREEN, new Pick[]{new Pick(Colours.BLUE, 3), new Pick(Colours.PURPLE, 2)}, new Colours[]{Colours.BLUE, Colours.PURPLE}, new Colours[]{Colours.GREY, Colours.GREY, Colours.GREY}, 6),
    CARD_DEV3G2(2, Colours.GREEN, new Pick[]{new Pick(Colours.BLUE, 5)}, new Colours[]{Colours.YELLOW, Colours.YELLOW}, new Colours[]{Colours.RED, Colours.RED, Colours.GREY, Colours.GREY}, 7),
    CARD_DEV1P2(2, Colours.PURPLE, new Pick[]{new Pick(Colours.PURPLE, 5)}, new Colours[]{Colours.YELLOW}, new Colours[]{Colours.RED, Colours.RED}, 5),
    CARD_DEV2P2(2, Colours.PURPLE, new Pick[]{new Pick(Colours.PURPLE, 3), new Pick(Colours.YELLOW, 2)}, new Colours[]{Colours.YELLOW, Colours.PURPLE}, new Colours[]{Colours.BLUE, Colours.BLUE, Colours.BLUE}, 6),
    CARD_DEV3P2(2, Colours.PURPLE, new Pick[]{new Pick(Colours.PURPLE, 5)}, new Colours[]{Colours.GREY, Colours.GREY}, new Colours[]{Colours.RED, Colours.RED, Colours.YELLOW, Colours.YELLOW}, 7),
    CARD_DEV1B2(2, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 4)}, new Colours[]{Colours.PURPLE}, new Colours[]{Colours.RED, Colours.RED}, 5),
    CARD_DEV2B2(2, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 3), new Pick(Colours.GREY, 2)}, new Colours[]{Colours.YELLOW, Colours.GREY}, new Colours[]{Colours.PURPLE, Colours.PURPLE, Colours.PURPLE}, 6),
    CARD_DEV3B2(2, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 5)}, new Colours[]{Colours.PURPLE, Colours.PURPLE}, new Colours[]{Colours.RED, Colours.RED, Colours.BLUE, Colours.BLUE}, 7),
    CARD_DEV1Y2(2, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 4)}, new Colours[]{Colours.BLUE}, new Colours[]{Colours.RED, Colours.RED}, 5),
    CARD_DEV2Y2(2, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.BLUE, 2)}, new Colours[]{Colours.GREY, Colours.BLUE}, new Colours[]{Colours.YELLOW, Colours.YELLOW, Colours.YELLOW}, 6),
    CARD_DEV3Y2(2, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 4)}, new Colours[]{Colours.BLUE, Colours.BLUE}, new Colours[]{Colours.RED, Colours.RED, Colours.PURPLE, Colours.PURPLE}, 7),
    CARD_DEV1G3(3, Colours.GREEN, new Pick[]{new Pick(Colours.BLUE, 6)}, new Colours[]{Colours.YELLOW, Colours.YELLOW}, new Colours[]{Colours.RED, Colours.RED, Colours.GREY, Colours.GREY, Colours.GREY}, 9),
    CARD_DEV2G3(3, Colours.GREEN, new Pick[]{new Pick(Colours.BLUE, 4), new Pick(Colours.PURPLE, 2)}, new Colours[]{Colours.YELLOW, Colours.PURPLE}, new Colours[]{Colours.RED, Colours.BLUE, Colours.BLUE, Colours.GREY, Colours.GREY}, 10),
    CARD_DEV3G3(3, Colours.GREEN, new Pick[]{new Pick(Colours.GREY, 7)}, new Colours[]{Colours.PURPLE}, new Colours[]{Colours.RED, Colours.RED, Colours.RED, Colours.YELLOW}, 11),
    CARD_DEV1P3(3, Colours.PURPLE, new Pick[]{new Pick(Colours.GREY, 7)}, new Colours[]{Colours.GREY, Colours.GREY}, new Colours[]{Colours.RED, Colours.RED, Colours.YELLOW, Colours.YELLOW, Colours.YELLOW}, 9),
    CARD_DEV2P3(3, Colours.PURPLE, new Pick[]{new Pick(Colours.PURPLE, 4), new Pick(Colours.YELLOW, 2)}, new Colours[]{Colours.GREY, Colours.BLUE}, new Colours[]{Colours.RED, Colours.YELLOW, Colours.YELLOW, Colours.YELLOW, Colours.PURPLE, Colours.PURPLE}, 10),
    CARD_DEV3P3(3, Colours.PURPLE, new Pick[]{new Pick(Colours.PURPLE, 7)}, new Colours[]{Colours.YELLOW}, new Colours[]{Colours.RED, Colours.RED, Colours.RED, Colours.GREY}, 11),
    CARD_DEV1B3(3, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 6)}, new Colours[]{Colours.PURPLE, Colours.PURPLE}, new Colours[]{Colours.RED, Colours.RED, Colours.BLUE, Colours.BLUE, Colours.BLUE}, 9),
    CARD_DEV2B3(3, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 5), new Pick(Colours.GREY, 2)}, new Colours[]{Colours.YELLOW, Colours.BLUE}, new Colours[]{Colours.RED, Colours.PURPLE, Colours.PURPLE, Colours.GREY, Colours.GREY}, 10),
    CARD_DEV3B3(3, Colours.BLUE, new Pick[]{new Pick(Colours.YELLOW, 7)}, new Colours[]{Colours.GREY}, new Colours[]{Colours.RED, Colours.RED, Colours.RED, Colours.BLUE}, 11),
    CARD_DEV1Y3(3, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 7)}, new Colours[]{Colours.BLUE, Colours.BLUE}, new Colours[]{Colours.RED, Colours.RED, Colours.PURPLE, Colours.PURPLE, Colours.PURPLE}, 9),
    CARD_DEV2Y3(3, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 5), new Pick(Colours.PURPLE, 2)}, new Colours[]{Colours.GREY, Colours.PURPLE}, new Colours[]{Colours.RED, Colours.BLUE, Colours.BLUE, Colours.YELLOW, Colours.YELLOW}, 10),
    CARD_DEV3Y3(3, Colours.YELLOW, new Pick[]{new Pick(Colours.GREY, 7)}, new Colours[]{Colours.BLUE}, new Colours[]{Colours.RED, Colours.RED, Colours.RED, Colours.PURPLE}, 11);

    boolean flipped;
    private int lvl;
    private Colours c;
    private Pick[] cost;
    private Colours[] resFrom;
    private Colours[] resTo;
    private int winPnt;

    CardDev (int lvl, Colours c, Pick[] cost, Colours[] resFrom, Colours[] resTo, int winPnt){
        this.lvl = lvl;
        this.c = c;
        this.cost = cost;
        this.resFrom = resFrom;
        this.resTo = resTo;
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public int getLvl ( ){
        return lvl;
    }

    public Colours getC ( ){
        return c;
    }

    public Pick[] getCost ( ){
        return cost;
    }

    public Colours[] getResFrom ( ){
        return resFrom;
    }

    public Colours[] getResTo ( ){
        return resTo;
    }

    @Override
    public int getWinPnt ( ){
        return winPnt;
    }

    @Override
    public boolean getFlipped ( ){
        return flipped;
    }

    @Override
    public void flipCard ( ){
        flipped = !flipped;
    }
}
