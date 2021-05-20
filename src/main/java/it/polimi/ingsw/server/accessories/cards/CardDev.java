package it.polimi.ingsw.server.accessories.cards;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;

public enum CardDev implements Card {

    CARD_DEV1B1(1,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 2)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.RED, 1)},
            1),
    CARD_DEV1B2(2,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 4)},
            new Pick[]{new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.RED, 2)},
            5),
    CARD_DEV1B3(3,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 6)},
            new Pick[]{new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.BLUE, 3), new Pick(Colours.RED, 2)},
            9),
    CARD_DEV1G1(1,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.RED, 1)},
            1),
    CARD_DEV1G2(2,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 4)},
            new Pick[]{new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.RED, 2)},
            5),
    CARD_DEV1G3(3,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 6)},
            new Pick[]{new Pick(Colours.YELLOW, 2)},
            new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.RED, 2)},
            9),
    CARD_DEV1P1(1,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.RED, 1)},
            1),
    CARD_DEV1P2(2,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 4)},
            new Pick[]{new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.RED, 2)},
            5),
    CARD_DEV1P3(3,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 6)},
            new Pick[]{new Pick(Colours.GREY, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 3), new Pick(Colours.RED, 2)},
            9),
    CARD_DEV1Y1(1,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 2)},
            new Pick[]{new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.RED, 1)},
            1),
    CARD_DEV1Y2(2,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 4)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.RED, 2)},
            5),
    CARD_DEV1Y3(3,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 6)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 3), new Pick(Colours.RED, 2)},
            9),
    CARD_DEV2B1(1,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.GREY, 1)},
            2),
    CARD_DEV2B2(2,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 3), new Pick(Colours.GREY, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 3)},
            6),
    CARD_DEV2B3(3,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 5), new Pick(Colours.GREY, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.GREY, 2), new Pick(Colours.RED, 1)},
            10),
    CARD_DEV2G1(1,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 1)},
            2),
    CARD_DEV2G2(2,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 3), new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.GREY, 3)},
            6),
    CARD_DEV2G3(3,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 5), new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.GREY, 2), new Pick(Colours.RED, 1)},
            10),
    CARD_DEV2P1(1,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            2),
    CARD_DEV2P2(2,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 3), new Pick(Colours.YELLOW, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.BLUE, 3)},
            6),
    CARD_DEV2P3(3,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 5), new Pick(Colours.YELLOW, 2)},
            new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 1)},
            10),
    CARD_DEV2Y1(1,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1), new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 1)},
            2),
    CARD_DEV2Y2(2,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.BLUE, 2)},
            new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 3)},
            6),
    CARD_DEV2Y3(3,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 5), new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 1)},
            10),
    CARD_DEV3B1(1,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 3)},
            new Pick[]{new Pick(Colours.GREY, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.BLUE, 1)},
            3),
    CARD_DEV3B2(2,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 5)},
            new Pick[]{new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 2)},
            7),
    CARD_DEV3B3(3,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 7)},
            new Pick[]{new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.RED, 3)},
            11),
    CARD_DEV3G1(1,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 3)},
            new Pick[]{new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1)},
            3),
    CARD_DEV3G2(2,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 5)},
            new Pick[]{new Pick(Colours.YELLOW, 2)},
            new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.RED, 2)},
            7),
    CARD_DEV3G3(3,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 7)},
            new Pick[]{new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.RED, 3)},
            11),
    CARD_DEV3P1(1,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 3)},
            new Pick[]{new Pick(Colours.YELLOW, 2)},
            new Pick[]{new Pick(Colours.PURPLE, 1), new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1)},
            3),
    CARD_DEV3P2(2,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 5)},
            new Pick[]{new Pick(Colours.GREY, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.RED, 2)},
            7),
    CARD_DEV3P3(3,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 7)},
            new Pick[]{new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.RED, 3)},
            11),
    CARD_DEV3Y1(1,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 3)},
            new Pick[]{new Pick(Colours.BLUE, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1), new Pick(Colours.GREY, 1)},
            3),
    CARD_DEV3Y2(2,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 5)},
            new Pick[]{new Pick(Colours.BLUE, 2)},
            new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 2)},
            7),
    CARD_DEV3Y3(3,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 7)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 1), new Pick(Colours.RED, 3)},
            11),
    CARD_DEV4B1(1,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.PURPLE, 2)},
            new Pick[]{new Pick(Colours.BLUE, 1), new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 1)},
            4),
    CARD_DEV4B2(2,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 3), new Pick(Colours.GREY, 3)},
            new Pick[]{new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.RED, 1)},
            8),
    CARD_DEV4B3(3,
            ColoursCards.BLUE,
            new Pick[]{new Pick(Colours.YELLOW, 4), new Pick(Colours.GREY, 4)},
            new Pick[]{new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 3)},
            12),
    CARD_DEV4G1(1,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.YELLOW, 2)},
            new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.RED, 1)},
            4),
    CARD_DEV4G2(2,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 3), new Pick(Colours.YELLOW, 3)},
            new Pick[]{new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 1)},
            8),
    CARD_DEV4G3(3,
            ColoursCards.GREEN,
            new Pick[]{new Pick(Colours.BLUE, 4), new Pick(Colours.YELLOW, 4)},
            new Pick[]{new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 1)},
            12),
    CARD_DEV4P1(1,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.GREY, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.RED, 1)},
            4),
    CARD_DEV4P2(2,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 3), new Pick(Colours.BLUE, 3)},
            new Pick[]{new Pick(Colours.GREY, 1)},
            new Pick[]{new Pick(Colours.PURPLE, 2), new Pick(Colours.RED, 1)},
            8),
    CARD_DEV4P3(3,
            ColoursCards.VIOLET,
            new Pick[]{new Pick(Colours.PURPLE, 4), new Pick(Colours.BLUE, 4)},
            new Pick[]{new Pick(Colours.YELLOW, 1)},
            new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.PURPLE, 1)},
            12),
    CARD_DEV4Y1(1,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 2), new Pick(Colours.BLUE, 2)},
            new Pick[]{new Pick(Colours.YELLOW, 1), new Pick(Colours.PURPLE, 1)},
            new Pick[]{new Pick(Colours.BLUE, 2), new Pick(Colours.RED, 1)},
            4),
    CARD_DEV4Y2(2,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 3), new Pick(Colours.PURPLE, 3)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.YELLOW, 2), new Pick(Colours.RED, 1)},
            8),
    CARD_DEV4Y3(3,
            ColoursCards.YELLOW,
            new Pick[]{new Pick(Colours.GREY, 4), new Pick(Colours.PURPLE, 4)},
            new Pick[]{new Pick(Colours.BLUE, 1)},
            new Pick[]{new Pick(Colours.GREY, 1), new Pick(Colours.PURPLE, 3)},
            12);

    private final int lvl;
    private final ColoursCards colour;
    private final Pick[] cost;
    private final Pick[] resFrom;
    private final Pick[] resTo;
    private final int winPnt;
    boolean flipped;

    CardDev(int lvl, ColoursCards colour, Pick[] cost, Pick[] resFrom, Pick[] resTo, int winPnt) {
        this.lvl = lvl;
        this.colour = colour;
        this.cost = cost;
        this.resFrom = resFrom;
        this.resTo = resTo;
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public static Card getSpecificCard(String code) {
        return switch (code) {
            case "1B1" -> CARD_DEV1B1;
            case "2B1" -> CARD_DEV2B1;
            case "3B1" -> CARD_DEV3B1;
            case "4B1" -> CARD_DEV4B1;
            case "1B2" -> CARD_DEV1B2;
            case "2B2" -> CARD_DEV2B2;
            case "3B2" -> CARD_DEV3B2;
            case "4B2" -> CARD_DEV4B2;
            case "1B3" -> CARD_DEV1B3;
            case "2B3" -> CARD_DEV2B3;
            case "3B3" -> CARD_DEV3B3;
            case "4B3" -> CARD_DEV4B3;
            case "1G1" -> CARD_DEV1G1;
            case "2G1" -> CARD_DEV2G1;
            case "3G1" -> CARD_DEV3G1;
            case "4G1" -> CARD_DEV4G1;
            case "1G2" -> CARD_DEV1G2;
            case "2G2" -> CARD_DEV2G2;
            case "3G2" -> CARD_DEV3G2;
            case "4G2" -> CARD_DEV4G2;
            case "1G3" -> CARD_DEV1G3;
            case "2G3" -> CARD_DEV2G3;
            case "3G3" -> CARD_DEV3G3;
            case "4G3" -> CARD_DEV4G3;
            case "1P1" -> CARD_DEV1P1;
            case "2P1" -> CARD_DEV2P1;
            case "3P1" -> CARD_DEV3P1;
            case "4P1" -> CARD_DEV4P1;
            case "1P2" -> CARD_DEV1P2;
            case "2P2" -> CARD_DEV2P2;
            case "3P2" -> CARD_DEV3P2;
            case "4P2" -> CARD_DEV4P2;
            case "1P3" -> CARD_DEV1P3;
            case "2P3" -> CARD_DEV2P3;
            case "3P3" -> CARD_DEV3P3;
            case "4P3" -> CARD_DEV4P3;
            case "1Y1" -> CARD_DEV1Y1;
            case "2Y1" -> CARD_DEV2Y1;
            case "3Y1" -> CARD_DEV3Y1;
            case "4Y1" -> CARD_DEV4Y1;
            case "1Y2" -> CARD_DEV1Y2;
            case "2Y2" -> CARD_DEV2Y2;
            case "3Y2" -> CARD_DEV3Y2;
            case "4Y2" -> CARD_DEV4Y2;
            case "1Y3" -> CARD_DEV1Y3;
            case "2Y3" -> CARD_DEV2Y3;
            case "3Y3" -> CARD_DEV3Y3;
            case "4Y3" -> CARD_DEV4Y3;
            default -> null;
        };
    }

    public int getLvl() {
        return lvl;
    }

    public ColoursCards getColour() {
        return colour;
    }

    public Pick[] getCost() {
        return cost;
    }

    public Pick[] getResFrom() {
        return resFrom;
    }

    public Pick[] getResTo() {
        return resTo;
    }

    @Override
    public String toString() {
        return name() + "\n" + "This is a " + lvl + "level " + colour + " card the gives " + winPnt + "points.\nIt costs" + "\t->\t" + cost +
                "\nThis card allows you to produce\n" + resTo + "\nStarting from\n" + resFrom;

    }

    @Override
    public int getWinPnt() {
        return winPnt;
    }

    @Override
    public boolean getFlipped() {
        return flipped;
    }

    @Override
    public void flipCard() {
        flipped = !flipped;
    }
}
