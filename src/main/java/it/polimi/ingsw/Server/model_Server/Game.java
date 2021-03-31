package it.polimi.ingsw.Server.model_Server;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game{
    static final int XMARKET = 4;
    static final int YMARKET = 3;
    static final int MAXTRACK = 20;
    static final int MAXMARBLESW = 4;
    static final int MAXMARBLESB = 2;
    static final int MAXMARBLESG = 2;
    static final int MAXMARBLESY = 2;
    static final int MAXMARBLESP = 2;
    static final int MAXMARBLESR = 1;

    final Mod mod;
    Board[] b;
    Market market;
    Supply supply;
    ArrayList<CardDev> cardDevArrayList;
    ArrayList<LeaderCard> cardLeadArrayList;
    ArrayList<PopeFav> cardPopeArrayList;

    Game (Mod mod, int gamers){
        this.mod = mod;

        if (gamers > 1){
            /* Board creation for each player */
            for (int i = 0;i < gamers;i++) this.b[i] = new Board (i);
        }
        else{
            /* SINGLE PLAYER */
        }
        /* Market creation for each Game */
        this.market = new Market (XMARKET, YMARKET);
        /* Supply creation for each Game */
        this.supply = new Supply ();
        /* Resource creation for each Game */
        this.supply.cont.replace (Colours.BLUE, MAXMARBLESB * 8);
        this.supply.cont.replace (Colours.GREY, MAXMARBLESG * 8);
        this.supply.cont.replace (Colours.PURPLE, MAXMARBLESP * 8);
        this.supply.cont.replace (Colours.YELLOW, MAXMARBLESY * 8);
        /* Card cataloging for each Game */
        for (CardDev cD : CardDev.values ()) cardDevArrayList.add (cD);
        for (CardLead cL : CardLead.values ()) cardLeadArrayList.add (cL);
        for (PopeFav cP : PopeFav.values ()) cardPopeArrayList.add (cP);
        /*...*/
    }
}
