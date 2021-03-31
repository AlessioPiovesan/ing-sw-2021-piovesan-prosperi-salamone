package it.polimi.ingsw.Server.model_Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

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
    Board[] b; /* manca l'associazione con i socket dei vari pleyer*/
    Market market;
    Supply supply;
    ArrayList<CardDev> cardDevArrayList=new ArrayList<> ();
    ArrayList<LeaderCard> cardLeadArrayList=new ArrayList<> ();
    ArrayList<PopeFav> cardPopeArrayList=new ArrayList<> ();

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
    public ArrayList<CardLead> giveCardLead(){
        ArrayList<CardLead> cardPlayer=new ArrayList<> ();
        CardLead card=CardLead.card_Lead1;
        /*carte random*/
        if (cardPlayer.containsAll(Collections.singleton (card)))
            cardPlayer.add (card);
        pickCardLead ();
        return cardPlayer;
    }

    public CardLead[] pickCardLead(){
        CardLead[] choice = new CardLead[2];
        System.out.println ("Prima scelta");
        choice[0]=CardLead.card_Lead1;
        System.out.println ("Seconda scelta");
        choice[1]=CardLead.card_Lead1;
        return choice;
    }

    public int nextPlayerId(Board[] b){
        Random random = new Random();
        return random.nextInt (b.length);
        }

    public void pippo(Board[] b, int first){
        Board player = new Board (first);
        Colours choice=Colours.WHITE;

        b[first+1].Warehouse.comp.replace (choice.selectPref (), 1);
        b[first+2].Warehouse.comp.replace (choice.selectPref (), 1);
        b[first+2].FaithTrack.position += 1;
        b[first+3].Warehouse.comp.replace (choice.selectPref (), 1);
        b[first+3].Warehouse.comp.replace (choice.selectPref (), 1);
        b[first+3].FaithTrack.position += 1;
    }
}
