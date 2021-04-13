package it.polimi.ingsw.Server.model_Server.Gioco;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Constants;
import it.polimi.ingsw.Server.model_Server.Carte.CardDev;
import it.polimi.ingsw.Server.model_Server.Carte.CardLeader;
import it.polimi.ingsw.Server.model_Server.Carte.CardPopeFav;
import it.polimi.ingsw.Server.model_Server.Carte.ICardLeader;
import it.polimi.ingsw.Server.model_Server.Plancia.Board;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game{

    Board[] b=new Board[4]; /* c'è DA IMPLEMENTARE l'associazione con i socket dei vari player*/
    Market market;
    Supply supply;
    private final Random random = SecureRandom.getInstanceStrong();
    ArrayList<CardDev> cardDevArrayList = new ArrayList<>();
    ArrayList<ICardLeader> cardLeadArrayList = new ArrayList<>();
    ArrayList<CardPopeFav> cardPopeArrayList = new ArrayList<>();

    Game (int gamers) throws NoSuchAlgorithmException{
        if (gamers == 1){ int a=1; /*fa partire il gioco sul client*/}
        /* Board creation for each player */
        for (int i = 0;i < gamers;i++){ this.b[i] = new Board(i); }
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

    public List<CardLeader> giveCardLead ( ){
        ArrayList<CardLeader> cardPlayer = new ArrayList<>();
        int i = 0;
        /*carte random*/
        while (i < 4){
            i = getFirst4LeaderCard(cardPlayer, i);
        }
        return cardPlayer;
    }

    private int getFirst4LeaderCard (ArrayList<CardLeader> cardPlayer, int i){
        CardLeader card;
        int rValue =this.random.nextInt(cardPlayer.size());
        card = (CardLeader)cardLeadArrayList.toArray()[rValue];
        if (cardPlayer.contains(card)){
            cardPlayer.add(card);
            i++;
        }
        return i;
    }

    public List<CardLeader> pickCardLead ( ){
        ArrayList<CardLeader> choice = new ArrayList<>();
        System.out.println("Prima scelta");     /*Mostri le quattro carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Inserisci la carta scelta nella lista */
        System.out.println("Seconda scelta");   /*Mostri le tre carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Inserisci la carta scelta nella lista */
        return choice;
    }

    public Board getB ( int i){
        return b[i];
    }

    public int nextPlayerId (){
        return this.random.nextInt(b.length);
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
