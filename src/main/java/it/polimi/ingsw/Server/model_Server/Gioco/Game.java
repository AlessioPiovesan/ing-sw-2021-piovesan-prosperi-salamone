package it.polimi.ingsw.Server.model_Server.Gioco;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Constants;
import it.polimi.ingsw.Server.model_Server.Carte.CardDev;
import it.polimi.ingsw.Server.model_Server.Carte.CardLeader;
import it.polimi.ingsw.Server.model_Server.Carte.CardPopeFav;
import it.polimi.ingsw.Server.model_Server.Carte.ICardLeader;
import it.polimi.ingsw.Server.model_Server.Plancia.Board;

import java.util.ArrayList;
import java.util.Random;

public class Game{

    Board[] b; /* manca l'associazione con i socket dei vari player*/
    Market market;
    Supply supply;
    ArrayList<CardDev> cardDevArrayList = new ArrayList<>();
    ArrayList<ICardLeader> cardLeadArrayList = new ArrayList<>();
    ArrayList<CardPopeFav> cardPopeArrayList = new ArrayList<>();

    Game (int gamers){
        if (gamers > 1){
            /* Board creation for each player */
            assert this.b != null;
            for (int i = 0;i < gamers;i++) this.b[i] = new Board(i);
        }
        else{
            /* SINGLE PLAYER */
        }
        /* Market creation for each Game */
        this.market = new Market();
        /* Supply creation for each Game */
        this.supply = new Supply();
        /* Resources creation for each Game */
        this.supply.cont.replace(Colours.BLUE, Constants.MAXMARBLESB.getVal() * 8);
        this.supply.cont.replace(Colours.GREY, Constants.MAXMARBLESG.getVal() * 8);
        this.supply.cont.replace(Colours.PURPLE, Constants.MAXMARBLESP.getVal() * 8);
        this.supply.cont.replace(Colours.YELLOW, Constants.MAXMARBLESY.getVal() * 8);
        /* Card cataloging for each Game */
        for (CardDev cD: CardDev.values()) cardDevArrayList.add(cD);
        for (CardLeader cL: CardLeader.values()) cardLeadArrayList.add(cL);
        for (CardPopeFav cP: CardPopeFav.values()) cardPopeArrayList.add(cP);
        /*...*/
        for (int i = 0;i < gamers;i++){
            b[i].setMyLeaderCards(giveCardLead()); /*Queste sono 4 ma ne deve scegliere 2*/
        }
    }

    public ArrayList<CardLeader> giveCardLead ( ){
        ArrayList<CardLeader> cardPlayer = new ArrayList<>();
        CardLeader card = CardLeader.card_Lead1;
        Random random = new Random();
        int i = 0;
        /*carte random*/
        while (i < 4){
            card = (CardLeader)cardLeadArrayList.toArray()[random.nextInt()];
            if (cardPlayer.contains(card)){
                cardPlayer.add(card);
                i++;
            }
        }
        return cardPlayer;
    }

    public ArrayList<CardLeader> pickCardLead ( ){
        ArrayList<CardLeader> choice = new ArrayList<CardLeader>();
        System.out.println("Prima scelta");     /*Mostri le quattro carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Iserisci la carta scelta nella lista */
        System.out.println("Seconda scelta");   /*Mostri le tre carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Iserisci la carta scelta nella lista */
        return choice;
    }

    public int nextPlayerId (Board[] b){
        Random random = new Random();
        return random.nextInt(b.length);
    }

    public void pickResources (Board[] b, int first){
        Board player = new Board(first);
        Colours choice = Colours.WHITE;

        b[first + 1].warehouse.setResources(choice.selectPref(), 1);
        b[first + 2].warehouse.setResources(choice.selectPref(), 1);
        b[first + 2].faithTrack.setPosition(1);
        b[first + 3].warehouse.setResources(choice.selectPref(), 1);
        b[first + 3].warehouse.setResources(choice.selectPref(), 1);
        b[first + 3].faithTrack.setPosition(1);
    }
}
