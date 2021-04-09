package it.polimi.ingsw.Server.model_Server.Carte;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Pick;
import it.polimi.ingsw.Server.model_Server.Accessori.Requirements;

public enum CardLeader implements ICardLeader, Card{

    card_Lead1(new Requirements[]{new Requirements.RequirementsCard(1, Colours.GREEN), new Requirements.RequirementsCard(1, Colours.YELLOW)},
               "-1 Servant",
               2){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead2(new Requirements[]{new Requirements.RequirementsCard(1, Colours.PURPLE), new Requirements.RequirementsCard(1, Colours.BLUE)},
               "-1 Shield",
               2){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead3(new Requirements[]{new Requirements.RequirementsCard(1, Colours.GREEN), new Requirements.RequirementsCard(1, Colours.BLUE)},
               "-1 Stone",
               2){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead4(new Requirements[]{new Requirements.RequirementsCard(1, Colours.YELLOW), new Requirements.RequirementsCard(1, Colours.PURPLE)},
               "-1 Coin",
               2){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead5(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.YELLOW, 5)})},
               "+2 space for Stones to the warehouse",
               3){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead6(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.GREY, 5)})},
               "+2 space for Servants to the warehouse",
               3){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead7(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.PURPLE, 5)})},
               "+2 space for Shields to the warehouse",
               3){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead8(new Requirements[]{new Requirements.RequirementsResources(new Pick[]{new Pick(Colours.BLUE, 5)})},
               "+2 space for Coins to the warehouse",
               3){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead9(new Requirements[]{new Requirements.RequirementsCard(1, Colours.YELLOW), new Requirements.RequirementsCard(1, Colours.YELLOW), new Requirements.RequirementsCard(1, Colours.BLUE)},
               "Transform a white marble to a purple one",
               5){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead10(new Requirements[]{new Requirements.RequirementsCard(1, Colours.GREEN), new Requirements.RequirementsCard(1, Colours.GREEN), new Requirements.RequirementsCard(1, Colours.PURPLE)},
                "Transform a white marble to a blue one",
                5){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead11(new Requirements[]{new Requirements.RequirementsCard(1, Colours.YELLOW), new Requirements.RequirementsCard(1, Colours.BLUE), new Requirements.RequirementsCard(1, Colours.BLUE)},
                "Transform a white marble to a grey one",
                5){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead12(new Requirements[]{new Requirements.RequirementsCard(1, Colours.PURPLE), new Requirements.RequirementsCard(1, Colours.PURPLE), new Requirements.RequirementsCard(1, Colours.GREEN)},
                "Transform a white marble to a yellow one",
                5){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead13(new Requirements[]{new Requirements.RequirementsCard(2, Colours.YELLOW)},
                "From a Shield to a chosen resource and a faith",
                4){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead14(new Requirements[]{new Requirements.RequirementsCard(2, Colours.BLUE)},
                "From a Servant to a chosen resource and a faith",
                4){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead15(new Requirements[]{new Requirements.RequirementsCard(2, Colours.PURPLE)},
                "From a Stone to a chosen resource and a faith",
                4){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    },
    card_Lead16(new Requirements[]{new Requirements.RequirementsCard(2, Colours.GREEN)},
                "From a Coin to a chosen resource and a faith",
                4){
        @Override
        public String getSpecial ( ){
            return "Special";
        }
    };

    private final Requirements[] req;
    private final String special;
    private final int winPnt;
    private boolean flipped;

    CardLeader (Requirements[] req, String special, int winPnt){
        this.req = req;
        this.special = special;
        this.winPnt = winPnt;
        this.flipped = false;
    }

    @Override
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

}
