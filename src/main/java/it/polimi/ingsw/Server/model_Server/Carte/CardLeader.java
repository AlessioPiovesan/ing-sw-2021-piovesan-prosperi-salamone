package it.polimi.ingsw.Server.model_Server.Carte;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Requirements;

public enum CardLeader implements ICardLeader, Card{

    card_Lead1 (new Requirements.RequirementsCard (1, Colours.GREEN), "-1 Servant", 1){
        @Override
        public String getSpecial(){return "Special";}
    };

    private Requirements req;
    private String special;
    private int winPnt;
    private boolean flipped;

    CardLeader( Requirements req, String special, int winPnt){
        this.req = req;
        this.special=special;
        this.winPnt=winPnt;
        this.flipped=true;
    }

    @Override
    public Requirements getReq (){return req;}
    @Override
    public int getWinPnt ( ){return winPnt;}
    @Override
    public boolean getFlipped(){return flipped;}
    @Override
    public void flipCard(){flipped= !flipped;}

}
