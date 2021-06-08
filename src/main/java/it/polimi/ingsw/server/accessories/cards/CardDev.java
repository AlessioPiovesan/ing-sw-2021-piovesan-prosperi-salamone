package it.polimi.ingsw.server.accessories.cards;

import it.polimi.ingsw.clients.viewer.ConsoleColors;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;

public enum CardDev implements Card{

    CARDDEV_1C1(1, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 2)}, new Pick[]{new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.RED, 1)}, 1),
    CARDDEV_1C2(2, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 4)}, new Pick[]{new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.RED, 2)}, 5),
    CARDDEV_1C3(3, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 6)}, new Pick[]{new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.BLUE, 3), new Pick(Colours.RED, 2)}, 9),
    CARDDEV_1G1(1, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.RED, 1)}, 1),
    CARDDEV_1G2(2, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 4)}, new Pick[]{new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.RED, 2)}, 5),
    CARDDEV_1G3(3, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 6)}, new Pick[]{new Pick(Colours.YELLOW, 2)}, new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.RED, 2)}, 9),
    CARDDEV_1P1(1, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.RED, 1)}, 1),
    CARDDEV_1P2(2, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 4)}, new Pick[]{new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.RED, 2)}, 5),
    CARDDEV_1P3(3, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 6)}, new Pick[]{new Pick(Colours.GREY, 2)}, new Pick[]{new Pick(Colours.YELLOW, 3), new Pick(Colours.RED, 2)}, 9),
    CARDDEV_1Y1(1, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 2)}, new Pick[]{new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.RED, 1)}, 1),
    CARDDEV_1Y2(2, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 4)}, new Pick[]{new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.RED, 2)}, 5),
    CARDDEV_1Y3(3, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 6)}, new Pick[]{new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.PURPLE, 3), new Pick(Colours.RED, 2)}, 9),
    CARDDEV_2C1(1, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.GREY, 1)}, 2),
    CARDDEV_2C2(2, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 3), new Pick(Colours.GREY, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.PURPLE, 3)}, 6),
    CARDDEV_2C3(3, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 5), new Pick(Colours.GREY, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.GREY, 2), new Pick(Colours.RED, 1)}, 10),
    CARDDEV_2G1(1, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.PURPLE, 1)}, 2),
    CARDDEV_2G2(2, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 3), new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.GREY, 3)}, 6),
    CARDDEV_2G3(3, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 5), new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.GREY, 2), new Pick(Colours.RED, 1)}, 10),
    CARDDEV_2P1(1, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.BLUE, 1)}, 2),
    CARDDEV_2P2(2, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 3), new Pick(Colours.YELLOW, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.BLUE, 3)}, 6),
    CARDDEV_2P3(3, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 5), new Pick(Colours.YELLOW, 2)}, new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 1)}, 10),
    CARDDEV_2Y1(1, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1), new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 1)}, 2),
    CARDDEV_2Y2(2, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.BLUE, 2)}, new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 3)}, 6),
    CARDDEV_2Y3(3, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 5), new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 1)}, 10),
    CARDDEV_3C1(1, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 3)}, new Pick[]{new Pick(Colours.GREY, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.BLUE, 1)}, 3),
    CARDDEV_3C2(2, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 5)}, new Pick[]{new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 2)}, 7),
    CARDDEV_3C3(3, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 7)}, new Pick[]{new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.RED, 3)}, 11),
    CARDDEV_3G1(1, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 3)}, new Pick[]{new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1)}, 3),
    CARDDEV_3G2(2, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 5)}, new Pick[]{new Pick(Colours.YELLOW, 2)}, new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.RED, 2)}, 7),
    CARDDEV_3G3(3, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 7)}, new Pick[]{new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.RED, 3)}, 11),
    CARDDEV_3P1(1, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 3)}, new Pick[]{new Pick(Colours.YELLOW, 2)}, new Pick[]{new Pick(Colours.PURPLE, 1), new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1)}, 3),
    CARDDEV_3P2(2, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 5)}, new Pick[]{new Pick(Colours.GREY, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.RED, 2)}, 7),
    CARDDEV_3P3(3, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 7)}, new Pick[]{new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.RED, 3)}, 11),
    CARDDEV_3Y1(1, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 3)}, new Pick[]{new Pick(Colours.BLUE, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.GREY, 1)}, 3),
    CARDDEV_3Y2(2, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 5)}, new Pick[]{new Pick(Colours.BLUE, 2)}, new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 2)}, 7),
    CARDDEV_3Y3(3, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 7)}, new Pick[]{new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.PURPLE, 1), new Pick(Colours.RED, 3)}, 11),
    CARDDEV_4C1(1, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.PURPLE, 2)}, new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 1)}, 4),
    CARDDEV_4C2(2, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 3), new Pick(Colours.GREY, 3)}, new Pick[]{new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.RED, 1)}, 8),
    CARDDEV_4C3(3, ColoursCards.CYAN, new Pick[]{new Pick(Colours.YELLOW, 4), new Pick(Colours.GREY, 4)}, new Pick[]{new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 3)}, 12),
    CARDDEV_4G1(1, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.YELLOW, 2)}, new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.RED, 1)}, 4),
    CARDDEV_4G2(2, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 3), new Pick(Colours.YELLOW, 3)}, new Pick[]{new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 1)}, 8),
    CARDDEV_4G3(3, ColoursCards.GREEN, new Pick[]{new Pick(Colours.BLUE, 4), new Pick(Colours.YELLOW, 4)}, new Pick[]{new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 1)}, 12),
    CARDDEV_4P1(1, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.GREY, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.RED, 1)}, 4),
    CARDDEV_4P2(2, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 3), new Pick(Colours.BLUE, 3)}, new Pick[]{new Pick(Colours.GREY, 1)}, new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 1)}, 8),
    CARDDEV_4P3(3, ColoursCards.VIOLET, new Pick[]{new Pick(Colours.PURPLE, 4), new Pick(Colours.BLUE, 4)}, new Pick[]{new Pick(Colours.YELLOW, 1)}, new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.PURPLE, 1)}, 12),
    CARDDEV_4Y1(1, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.BLUE, 2)}, new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1)}, new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 1)}, 4),
    CARDDEV_4Y2(2, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.PURPLE, 3)}, new Pick[]{new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.RED, 1)}, 8),
    CARDDEV_4Y3(3, ColoursCards.YELLOW, new Pick[]{new Pick(Colours.GREY, 4), new Pick(Colours.PURPLE, 4)}, new Pick[]{new Pick(Colours.BLUE, 1)}, new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.PURPLE, 3)}, 12);

    private final int lvl;
    private final ColoursCards colour;
    private final Pick[] cost;
    private final Pick[] resFrom;
    private final Pick[] resTo;
    private final int winPnt;
    boolean flipped;

    CardDev(int lvl, ColoursCards colour, Pick[] cost, Pick[] resFrom, Pick[] resTo, int winPnt){
        this.lvl = lvl;
        this.colour = colour;
        this.cost = cost;
        this.resFrom = resFrom;
        this.resTo = resTo;
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public static Card getSpecificCard(String code){
        for(var card : CardDev.values())
            if(card.name().contains(code.toUpperCase())) return card;
        return null;
    }

    public int getLvl(){
        return lvl;
    }

    public ColoursCards getColour(){
        return colour;
    }

    public Pick[] getCost(){
        return cost;
    }

    public Pick[] getResFrom(){
        return resFrom;
    }

    public Pick[] getResTo(){
        return resTo;
    }

    @Override
    public String toString(){
        var color = switch(colour){
            case CYAN -> ConsoleColors.CYAN_BACKGROUND_BRIGHT;
            case GREEN -> ConsoleColors.GREEN_BACKGROUND_BRIGHT;
            case VIOLET -> ConsoleColors.PURPLE_BACKGROUND_BRIGHT;
            case YELLOW -> ConsoleColors.YELLOW_BACKGROUND_BRIGHT;
        };

        return color + (ConsoleColors.PURPLE + name() + ConsoleColors.RESET) + "\n" + "This is a " + " level " + lvl + " " + ColoursCards.colored(colour.name()) + " card that gives " + winPnt + "points.\nIt costs" + "\t->\t" + showPick(cost) + "\nThis card allows you to produce\n" + showPick(resTo) + "\nStarting from\n" + showPick(resFrom) + ConsoleColors.RESET;
    }

    private String showPick(Pick[] picks){
        var string = new StringBuilder();
        for(var pick : picks)
            string.append(pick.getQnt()).append(" - ").append(pick.getResource().getType());
        return String.valueOf(string);
    }

    @Override
    public int getWinPnt(){
        return winPnt;
    }

    @Override
    public boolean getFlipped(){
        return flipped;
    }

    @Override
    public void flipCard(){
        flipped = !flipped;
    }
}
