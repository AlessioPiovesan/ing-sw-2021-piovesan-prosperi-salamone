package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Pick;
import it.polimi.ingsw.Server.model_Server.Carte.CardDev;
import it.polimi.ingsw.Server.model_Server.Carte.CardLeader;

import java.util.ArrayList;
import java.util.LinkedList;

public class Board{
    private static ArrayList<CardLeader> myLeaderCards = new ArrayList<>();
    private static ArrayList<CardDev> myDevCards = new ArrayList<>();
    public Warehouse warehouse;
    public FaithTrack faithTrack;
    protected StrongBox strongBox;
    protected Library[] l;
    int idD;

    public Board (int id){
        this.idD = id;
        /* Warehouse creation for a single Board */
        this.warehouse = new Warehouse();
        /* Strongbox creation for a single Board */
        this.strongBox = new StrongBox();
        /* Faith Track creation for a single board */
        this.faithTrack = new FaithTrack();
    }

    public void setMyLeaderCards (ArrayList<CardLeader> cards){
        this.myLeaderCards = cards;
    }

    private LinkedList<Pick> chooseResources ( ){

        LinkedList<Pick> choice = new LinkedList<>();
        boolean stop = true;

        warehouse.checkStatus();
        strongBox.checkStatus();
        /*Raccoglia la scelta che fa il giocatore*/  /* DA IMPLEMENTARE */
        while (stop){
            choice.add(new Pick(Colours.BLUE, 1));
            if (choice.contains(Colours.WHITE)) stop = false;
        }
        return choice;

    }
}
