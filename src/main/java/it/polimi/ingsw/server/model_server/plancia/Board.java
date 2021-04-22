package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.model_server.carte.CardDev;
import it.polimi.ingsw.server.model_server.carte.CardLeader;

import java.util.*;

public class Board{
    private static final LinkedList<CardDev> myDevCards = new LinkedList<>();
    private final CardLeader[] myLeaderCards = new CardLeader[2];
    private final Warehouse warehouse;
    private final FaithTrack faithTrack;
    protected StrongBox strongBox;
    protected Library library;
    int idD;
    private final EnumMap<Colours, Integer> resAll;

    public Board (int id, Warehouse warehouse, StrongBox strongBox, FaithTrack faithTrack, Library library){
        this.idD = id;
        /* Warehouse creation for a single Board */
        this.warehouse = warehouse;
        /* Strongbox creation for a single Board */
        this.strongBox = strongBox;
        /* Faith Track creation for a single board */
        this.faithTrack = faithTrack;
        /* ________ creation for a single board */
        this.library = library;
        resAll = new EnumMap<>(Colours.class);
        resAll.put(Colours.BLUE, 0);
        resAll.put(Colours.YELLOW, 0);
        resAll.put(Colours.GREY, 0);
        resAll.put(Colours.PURPLE, 0);
    }

    public List<CardLeader> pickCardLead ( ){
        ArrayList<CardLeader> choice = new ArrayList<>();
        System.out.println("Prima scelta");     /*Mostri le quattro carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Inserisci la carta scelta nella lista */
        System.out.println("Seconda scelta");   /*Mostri le tre carte e ne scegli una*/
        choice.add(CardLeader.card_Lead1);      /*Inserisci la carta scelta nella lista */
        return choice;
    }

    public void setMyLeaderCards (List<CardLeader> cards){ /*DA IMPLEMENTARE*/
        /*Mostra le carte*/
        /*Registra la scelta*/
        myLeaderCards[0] = cards.get(3);
        /*Registra la scelta*/
        myLeaderCards[1] = cards.get(2);
    }

    public int checkDiscardedResources (List<Colours> discarded){
        return discarded.size();
    }

    public void productionAll (Pick[] start){
        for (int i = 0;i < 3;i++){
            CardDev card = library.getCardOnTop(i);
            Pick[] res = card.getResFrom();
            production(res);
        }
    }

    public void production (Pick[] start){
        for (int i = 0;i < start.length;i++){
            strongBox.addResources(start[i]);
        }
    }

    public Map<Colours, Integer> getResAll ( ){
        return resAll;
    }

    public Warehouse getWarehouse ( ){
        return warehouse;
    }

    public FaithTrack getFaithTrack ( ){
        return faithTrack;
    }

    private LinkedList<Pick> chooseResources ( ){ /* DA IMPLEMENTARE */
        LinkedList<Pick> choice = new LinkedList<>();
        boolean stop = true;
        /*Mostra tutte le risorse del giocatore*/
        for (Colours colours: Colours.values())
            System.out.println(getResAll().get(colours));
        /* DA IMPLEMENTARE */
        while (stop){
            /*Il giocatore sceglie tutte le risorse*/
            choice.add(new Pick(Colours.BLUE, 1));
            if (/*condizione che dice che la scelta è stata fatta ed è conclusa*/ true) stop = false;
        }
        return choice;
    }

    public void updateResAll ( ){
        List<Pick> w = warehouse.checkStatus();
        for (Pick pick: w)
            resAll.replace(pick.getResource(), pick.getQnt() + resAll.get(pick.getResource()));
        for (Colours c: Arrays.asList(Colours.BLUE, Colours.GREY, Colours.PURPLE, Colours.YELLOW))
            resAll.replace(c, resAll.get(c) + strongBox.getComp().get(c));
    }

}
