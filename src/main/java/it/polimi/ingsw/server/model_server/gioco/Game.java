package it.polimi.ingsw.server.model_server.gioco;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
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
    private static int nGamers = 4;

    public static void setnGamers (int gamers){
        nGamers = gamers;
    }

    Game (Market market, Supply supply){
        /* Board creation for each player */
        for (int i = 0;i < nGamers;i++){
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
        /*for (int i = 0;i < nGamers;i++) b[i].setMyLeaderCards(giveCardLead());*/
    }

    public Board getSingleB (int i){
        return b[i];
    }

    public Board[] getAllB (){
        return b;
    }

    public List<CardLeader> giveCardLead ( ){
        ArrayList<CardLeader> cardPlayer = new ArrayList<>();
        while (cardPlayer.size() < 4){
            cardPlayer.add(selectNextPlayableLeaderCards());
        }
        return cardPlayer;
    }

    private CardLeader selectNextPlayableLeaderCards ( ){
        while (true){
            int i = random.nextInt(cardLeadArrayList.size());
            if (!(playableLeaderCards.contains(i))){
                playableLeaderCards.add(i);
                return cardLeadArrayList.get(i);
            }
        }
    }

    public List<CardDev> getCardDevArrayList ( ){
        return cardDevArrayList;
    }

    public List<CardLeader> getCardLeadArrayList ( ){
        return cardLeadArrayList;
    }

    public List<CardPopeFav> getCardPopeArrayList ( ){
        return cardPopeArrayList;
    }

    public int nextPlayerId ( ){
        return this.random.nextInt(nGamers);
    }

    public void pickStartingResources (int first){
        Colours choice = Colours.WHITE;
        int next = checkNext(first);
        if (nGamers > 1){
            b[next].getWarehouse().setResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
            b[next].updateResAll();
            if (nGamers > 2){
                next = checkNext(first);
                b[next].getWarehouse().setResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
                b[next].getFaithTrack().setPosition(1);
                b[next].updateResAll();
                if (nGamers > 3){
                    next = checkNext(first);
                    b[next].getWarehouse().setResources(0, new Pick(choice.selectPref(), 1)); /*DA IMPLEMENTARE (la scelta del livello)*/
                    Colours c = choice.selectPref();
                    int lvl = b[next].getWarehouse().isThereThatRes(c);
                    if (lvl < 0) lvl = 1;
                    b[next].getWarehouse().setResources(lvl, new Pick(c, 1));
                    b[next].getFaithTrack().setPosition(1);
                    b[next].updateResAll();
                }
            }
        }
    }

    public int checkNext (int now){
        if (now++ >= nGamers - 1) return 0;
        return now;
    }
}
