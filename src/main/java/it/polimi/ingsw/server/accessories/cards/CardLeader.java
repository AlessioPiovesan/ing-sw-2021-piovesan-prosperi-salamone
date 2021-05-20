package it.polimi.ingsw.server.accessories.cards;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.Requirements;

public enum CardLeader implements Card {

    CARDLEAD01(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.YELLOW)}, "-1 Servant", Colours.PURPLE, 2),
    CARDLEAD02(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, "-1 Shield", Colours.BLUE, 2),
    CARDLEAD03(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, "-1 Stone", Colours.GREY, 2),
    CARDLEAD04(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, "-1 Coin", Colours.YELLOW, 2),
    CARDLEAD05(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.YELLOW, 5)})}, "+2 space for Stones to the warehouse", Colours.GREY, 3),
    CARDLEAD06(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.GREY, 5)})}, "+2 space for Servants to the warehouse", Colours.PURPLE, 3),
    CARDLEAD07(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.PURPLE, 5)})}, "+2 space for Shields to the warehouse", Colours.BLUE, 3),
    CARDLEAD08(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.BLUE, 5)})}, "+2 space for Coins to the warehouse", Colours.YELLOW, 3),
    CARDLEAD09(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, "Transform a white marble to a purple one", Colours.PURPLE, 5),
    CARDLEAD10(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, "Transform a white marble to a blue one", Colours.BLUE, 5),
    CARDLEAD11(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.BLUE), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, "Transform a white marble to a grey one", Colours.GREY, 5),
    CARDLEAD12(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.GREEN)}, "Transform a white marble to a yellow one", Colours.YELLOW, 5),
    CARDLEAD13(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.YELLOW)}, "From a Shield to a chosen resource and a faith", Colours.BLUE, 4),
    CARDLEAD14(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.BLUE)}, "From a Servant to a chosen resource and a faith", Colours.PURPLE, 4),
    CARDLEAD15(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.VIOLET)}, "From a Stone to a chosen resource and a faith", Colours.GREY, 4),
    CARDLEAD16(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.GREEN)}, "From a Coin to a chosen resource and a faith", Colours.YELLOW, 4);

    private final Requirements[] req;
    private final Colours special;
    private final String ability;
    private final int winPnt;
    private boolean flipped;

    CardLeader(Requirements[] req, String ability, Colours special, int winPnt) {
        this.req = req;
        this.ability = ability;
        this.special = special;
        this.winPnt = winPnt;
        this.flipped = false;
    }

    public static CardLeader getSpecificCard(String n) {
        switch (n) {
            case "01" -> {
                return CARDLEAD01;
            }
            case "02" -> {
                return CARDLEAD02;
            }
            case "03" -> {
                return CARDLEAD03;
            }
            case "04" -> {
                return CARDLEAD04;
            }
            case "05" -> {
                return CARDLEAD05;
            }
            case "06" -> {
                return CARDLEAD06;
            }
            case "07" -> {
                return CARDLEAD07;
            }
            case "08" -> {
                return CARDLEAD08;
            }
            case "09" -> {
                return CARDLEAD09;
            }
            case "10" -> {
                return CARDLEAD10;
            }
            case "11" -> {
                return CARDLEAD11;
            }
            case "12" -> {
                return CARDLEAD12;
            }
            case "13" -> {
                return CARDLEAD13;
            }
            case "14" -> {
                return CARDLEAD14;
            }
            case "15" -> {
                return CARDLEAD15;
            }
            case "16" -> {
                return CARDLEAD16;
            }
            default -> {
                return null;
            }
        }
    }

    public Requirements[] getReq() {
        return req;
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

    public Colours getSpecial() {
        return special;
    }

    @Override
    public String toString() {
        return name() + "\t->\t" +
                "this card gives you the ability of" +
                ability +
                "and " + winPnt + "points, to activate this card you need:\n"
                + req;
    }
}
