package it.polimi.ingsw.server.model_server.cards;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.Requirements;

import java.util.Arrays;

public enum CardLeader implements Card {

    CARDLEAD01(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.YELLOW)}, Colours.PURPLE, 2),
    CARDLEAD02(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.BLUE, 2),
    CARDLEAD03(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.GREY, 2),
    CARDLEAD04(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, Colours.YELLOW, 2),
    CARDLEAD05(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.YELLOW, 5)})}, Colours.GREY, 3),
    CARDLEAD06(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.GREY, 5)})}, Colours.PURPLE, 3),
    CARDLEAD07(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.PURPLE, 5)})}, Colours.BLUE, 3),
    CARDLEAD08(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.BLUE, 5)})}, Colours.YELLOW, 3),
    CARDLEAD09(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.PURPLE, 5),
    CARDLEAD10(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, Colours.BLUE, 5),
    CARDLEAD11(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.BLUE), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.GREY, 5),
    CARDLEAD12(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.GREEN)}, Colours.YELLOW, 5),
    CARDLEAD13(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.YELLOW)}, Colours.BLUE, 4),
    CARDLEAD14(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.BLUE)}, Colours.PURPLE, 4),
    CARDLEAD15(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.VIOLET)}, Colours.GREY, 4),
    CARDLEAD16(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.GREEN)}, Colours.YELLOW, 4);

    private final Requirements[] req;
    private final Colours special;
    private final String ability = "descrizione della abilità";
    private final int winPnt;
    private boolean flipped;

    CardLeader(Requirements[] req, Colours special, int winPnt) {
        this.req = req;
        this.special = special;
        this.winPnt = winPnt;
        this.flipped = false;
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
        return name() + "->\t{ " +
                "this card's requirements are:" + Arrays.toString(req) +
                ",\nspecial=" + ability +
                ",\nwinPnt=" + winPnt + "}";
    }
}
