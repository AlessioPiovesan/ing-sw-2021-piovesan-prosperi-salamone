package it.polimi.ingsw.server.accessories.cards;

import it.polimi.ingsw.clients.viewer.ConsoleColors;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.Requirements;

public enum CardLeader implements Card{

    CARDLEAD01(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.YELLOW)}, "-1 Servant", Colours.PURPLE, 2),
    CARDLEAD02(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.CYAN)}, "-1 Shield", Colours.BLUE, 2),
    CARDLEAD03(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.CYAN)}, "-1 Stone", Colours.GREY, 2),
    CARDLEAD04(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, "-1 Coin", Colours.YELLOW, 2),
    CARDLEAD05(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.YELLOW, 5)})}, "+2 space for Stones to the warehouse", Colours.GREY, 3),
    CARDLEAD06(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.GREY, 5)})}, "+2 space for Servants to the warehouse", Colours.PURPLE, 3),
    CARDLEAD07(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.PURPLE, 5)})}, "+2 space for Shields to the warehouse", Colours.BLUE, 3),
    CARDLEAD08(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.BLUE, 5)})}, "+2 space for Coins to the warehouse", Colours.YELLOW, 3),
    CARDLEAD09(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.CYAN)}, "Transform a white marble to a purple one", Colours.PURPLE, 5),
    CARDLEAD10(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.GREEN), new Requirements.RequirementsCard(1, ColoursCards.VIOLET)}, "Transform a white marble to a blue one", Colours.BLUE, 5),
    CARDLEAD11(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.YELLOW), new Requirements.RequirementsCard(1, ColoursCards.CYAN), new Requirements.RequirementsCard(1, ColoursCards.CYAN)}, "Transform a white marble to a grey one", Colours.GREY, 5),
    CARDLEAD12(new Requirements[]{new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.VIOLET), new Requirements.RequirementsCard(1, ColoursCards.GREEN)}, "Transform a white marble to a yellow one", Colours.YELLOW, 5),
    CARDLEAD13(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.YELLOW)}, "From a Shield to a chosen resource and a faith", Colours.BLUE, 4),
    CARDLEAD14(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.CYAN)}, "From a Servant to a chosen resource and a faith", Colours.PURPLE, 4),
    CARDLEAD15(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.VIOLET)}, "From a Stone to a chosen resource and a faith", Colours.GREY, 4),
    CARDLEAD16(new Requirements[]{new Requirements.RequirementsCard(2, ColoursCards.GREEN)}, "From a Coin to a chosen resource and a faith", Colours.YELLOW, 4);

    private final Requirements[] req;
    private final Colours special;
    private final String ability;
    private final int winPnt;
    private boolean flipped;

    CardLeader(Requirements[] req, String ability, Colours special, int winPnt){
        this.req = req;
        this.ability = ability;
        this.special = special;
        this.winPnt = winPnt;
        this.flipped = true;
    }

    public static Card getSpecificCard(String code){
        for(CardLeader card : CardLeader.values())
            if(card.name().contains(code.toUpperCase())) return card;
        return null;
    }

    public Requirements[] getReq(){
        return req;
    }

    public String getAbility(){
        return ability;
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

    public Colours getSpecial(){
        return special;
    }

    @Override
    public String toString(){
        return ConsoleColors.PURPLE + name() + ConsoleColors.RESET + "\t->\t" + "this card gives you the ability of\n" + ConsoleColors.RED_UNDERLINED + ability + ConsoleColors.RESET + " and " + winPnt + " points,\n\tto activate this card you need:\n" + showReq(req);
    }

    private String showReq(Requirements[] requirements){
        var req = new StringBuilder();
        for(var requirement : requirements)
            req.append("\t\t").append(requirement.toString()).append("\n");
        return String.valueOf(req);
    }
}
