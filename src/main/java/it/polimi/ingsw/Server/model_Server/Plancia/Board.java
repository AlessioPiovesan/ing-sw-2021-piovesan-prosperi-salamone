package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Pick;
import it.polimi.ingsw.Server.model_Server.Carte.CardDev;
import it.polimi.ingsw.Server.model_Server.Carte.CardLeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Board{
    private static List<CardLeader> myLeaderCards = new ArrayList<>();
    private static final ArrayList<CardDev> myDevCards = new ArrayList<>();
    public Warehouse warehouse;
    public FaithTrack faithTrack;
    protected StrongBox strongBox;
    protected Library l;
    int idD;
    private HashMap<Colours, Integer> resAll;

    public Board (int id){
        this.idD = id;
        /* Warehouse creation for a single Board */
        this.warehouse = new Warehouse();
        /* Strongbox creation for a single Board */
        this.strongBox = new StrongBox();
        /* Faith Track creation for a single board */
        this.faithTrack = new FaithTrack();
        /* ________ creation for a single board */
        this.l = new Library();
        resAll = new HashMap<>();
        resAll.put(Colours.BLUE, 0);
        resAll.put(Colours.YELLOW, 0);
        resAll.put(Colours.GREY, 0);
        resAll.put(Colours.PURPLE, 0);
    }

    public void setMyLeaderCards (List<CardLeader> cards){ myLeaderCards = cards; }

    private LinkedList<Pick> chooseResources ( ){ /* DA IMPLEMENTARE */
        LinkedList<Pick> choice = new LinkedList<>();
        boolean stop = true;
        warehouse.checkStatus();
        strongBox.checkStatus();
        /*Raccoglia la scelta che fa il giocatore*/  /* DA IMPLEMENTARE */
        while (stop){
            choice.add(new Pick(Colours.BLUE, 1));
            if (/*condizione*/ true) stop = false;
        }
        return choice;
    }

    public int checkDiscardedResources (List<Colours> discarded){
        return  discarded.size();
    }

    public void productionAll(Pick[] start){
        for (int i = 0;i < 3;i++){
            CardDev card = l.getCard(i);
            Pick[] res = card.getResFrom();
            production(res);
        }
    }

    public void production(Pick[] start){
        for (int i = 0;i < start.length;i++){
            strongBox.addResources(start[i]);
        }
    }

    private void addResAll(Pick[] add){
        for (int i = 0;i < add.length;i++){
            resAll.replace(add[i].getR(), resAll.get(add[i]) + add[i].getQnt());
        }
    }
    public HashMap<Colours, Integer> getResAll(){
        return resAll;
    }

    }
