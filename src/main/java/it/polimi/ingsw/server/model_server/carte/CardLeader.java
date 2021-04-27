package it.polimi.ingsw.server.model_server.carte;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.ColoursCards;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.accessori.Requirements;

public enum CardLeader implements Card{

    card_Lead1(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.YELLOW)}, Colours.PURPLE, 2),
    card_Lead2(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.BLUE, 2),
    card_Lead3(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.GREY, 2),
    card_Lead4(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, Colours.YELLOW, 2),
    card_Lead5(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.YELLOW, 5)})}, Colours.GREY, 3),
    card_Lead6(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.GREY, 5)})}, Colours.PURPLE, 3),
    card_Lead7(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.PURPLE, 5)})}, Colours.BLUE, 3),
    card_Lead8(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.BLUE, 5)})}, Colours.YELLOW, 3),
    card_Lead9(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.PURPLE, 5),
    card_Lead10(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, Colours.BLUE, 5),
    card_Lead11(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.BLUE), new Requirements.RequirementsCard(1, ColoursCards.BLUE)}, Colours.GREY, 5),
    card_Lead12(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.GREEN)}, Colours.YELLOW, 5),
    card_Lead13(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.YELLOW)}, Colours.BLUE, 4),
    card_Lead14(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.BLUE)}, Colours.PURPLE, 4),
    card_Lead15(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.VIOLET)}, Colours.GREY, 4),
    card_Lead16(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.GREEN)}, Colours.YELLOW, 4);

    private final Requirements[] req;
    private final Colours special;
    private final int winPnt;
    private boolean flipped;

    CardLeader (Requirements[] req, Colours special, int winPnt){
        this.req = req;
        this.special = special;
        this.winPnt = winPnt;
        this.flipped = false;
    }

    public Requirements[] getReq ( ){
        return req;
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

    public Colours getSpecial(){
        return special;
    }

}
