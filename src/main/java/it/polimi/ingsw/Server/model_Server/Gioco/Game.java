package it.polimi.ingsw.Server.model_Server.Gioco;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Constants;
import it.polimi.ingsw.Server.model_Server.Carte.CardDev;
import it.polimi.ingsw.Server.model_Server.Carte.CardLeader;
import it.polimi.ingsw.Server.model_Server.Carte.CardPopeFav;
import it.polimi.ingsw.Server.model_Server.Carte.ICardLeader;
import it.polimi.ingsw.Server.model_Server.Plancia.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game{

    Board[] b; /* c'è DA IMPLEMENTARE l'associazione con i socket dei vari player*/
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
            /* sta tutto sul Client */
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
        cardDevArrayList.addAll(Arrays.asList(CardDev.values()));
        cardLeadArrayList.addAll(Arrays.asList(CardLeader.values()));
        cardPopeArrayList.addAll(Arrays.asList(CardPopeFav.values()));
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
            i = getFirst4LeaderCard(cardPlayer, random, i);
        }
        return cardPlayer;
    }

    private int getFirst4LeaderCard (ArrayList<CardLeader> cardPlayer, Random random, int i){
        CardLeader card;
        card = (CardLeader)cardLeadArrayList.toArray()[random.nextInt()];
        if (cardPlayer.contains(card)){
            cardPlayer.add(card);
            i++;
        }
        return i;
    }

    public ArrayList<CardLeader> pickCardLead ( ){
        ArrayList<CardLeader> choice = new ArrayList<CardLeader>();
        System.out.println("Prima scelta");     /*Mostri le quattro carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Inserisci la carta scelta nella lista */
        System.out.println("Seconda scelta");   /*Mostri le tre carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Inserisci la carta scelta nella lista */
        return choice;
    }

    public int nextPlayerId (){
        Random random = new Random();
        return random.nextInt(b.length);
    }

    public void pickStartingResources (int first){
        Colours choice = Colours.WHITE;
        if(first+1>b.length) first=0;
        else first+=1;
        b[first].warehouse.setResources(choice.selectPref(), 1);
        if(first+1>b.length) first=0;
        else first+=1;
        b[first].warehouse.setResources(choice.selectPref(), 1);
        b[first].faithTrack.setPosition(1);
        if(first+1>b.length) first=0;
        else first+=1;
        b[first].warehouse.setResources(choice.selectPref(), 1);
        b[first].warehouse.setResources(choice.selectPref(), 1);
        b[first].faithTrack.setPosition(1);
    }
}
