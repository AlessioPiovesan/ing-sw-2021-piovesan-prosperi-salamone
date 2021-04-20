package it.polimi.ingsw.server.model_server.gioco;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import it.polimi.ingsw.server.model_server.carte.CardPopeFav;
import it.polimi.ingsw.server.model_server.plancia.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game{

    private final Random random = new Random();
    private static final Board[] b = new Board[4]; /* c'è DA IMPLEMENTARE l'associazione con i socket dei vari player*/
    private static Market market;
    private static Supply supply;
    private final ArrayList<CardDev> cardDevArrayList = new ArrayList<>();
    private final ArrayList<CardLeader> cardLeadArrayList = new ArrayList<>();
    private final ArrayList<Integer> playableLeaderCards = new ArrayList<>();
    private final ArrayList<CardPopeFav> cardPopeArrayList = new ArrayList<>();

    Game (int gamers, Market market, Supply supply){
        /* Board creation for each player */
        for (int i = 0;i < gamers;i++){
            Warehouse warehouse = new Warehouse();
            StrongBox strongBox = new StrongBox();
            FaithTrack faithTrack = new FaithTrack();
            Library library = new Library();
            b[i] = new Board(i, warehouse, strongBox, faithTrack, library);
        }
        /* Market creation for each Game */
        Game.market = market;
        /* Supply creation for each Game */
        Game.supply = supply;
        /* Card cataloging for each Game */
        cardDevArrayList.addAll(Arrays.asList(CardDev.values()));
        cardLeadArrayList.addAll(Arrays.asList(CardLeader.values()));
        cardPopeArrayList.addAll(Arrays.asList(CardPopeFav.values()));
        /*...*/
        for (int i = 0;i < gamers;i++) b[i].setMyLeaderCards(giveCardLead());
    }

    public Board getB (int i){
        return b[i];
    }

    public ArrayList<CardLeader> giveCardLead ( ){
        ArrayList<CardLeader> cardPlayer = new ArrayList<>();
        int i = random.nextInt(cardLeadArrayList.size());
        cardPlayer.add(cardLeadArrayList.get(i));
        playableLeaderCards.add(i);
        while (cardPlayer.size() < 4){
            i = random.nextInt(cardLeadArrayList.size());
            if (!(playableLeaderCards.contains(i))){
                cardPlayer.add(cardLeadArrayList.get(i));
                playableLeaderCards.add(i);
            }
        }
        return cardPlayer;
    }

    private int getFirst4LeaderCard (ArrayList<CardLeader> cardPlayer, int i){
        CardLeader card;
        int rValue = random.nextInt(cardPlayer.size());
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

    public int nextPlayerId ( ){
        return this.random.nextInt(b.length);
    }

    public void pickStartingResources(int gamers, int first) {
        Colours choice = Colours.WHITE;
        switch (gamers) {
            case 2: {
                if (first + 1 > gamers) first = 0;
                else first += 1;
                b[first].warehouse.setResources(choice.selectPref(), 1);
            }
            case 3: {
                if (first + 1 > gamers) first = 0;
                else first += 1;
                b[first].warehouse.setResources(choice.selectPref(), 1);
                if (first + 1 > gamers) first = 0;
                else first += 1;
                b[first].warehouse.setResources(choice.selectPref(), 1);
                b[first].faithTrack.setPosition(1);
            }
            case 4: {
                if (first + 1 >= gamers) first = 0;
                else first += 1;
                b[first].warehouse.setResources(choice.selectPref(), 1);
                if (first + 1 >= gamers) first = 0;
                else first += 1;
                b[first].warehouse.setResources(choice.selectPref(), 1);
                b[first].faithTrack.setPosition(1);
                if (first + 1 >= gamers) first = 0;
                else first += 1;
                b[first].warehouse.setResources(choice.selectPref(), 1);
                b[first].warehouse.setResources(choice.selectPref(), 1);
                b[first].faithTrack.setPosition(1);
            }
        }
    }
}
