package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Carte.CardLeader;
import it.polimi.ingsw.Server.model_Server.Gioco.Game;

import java.util.ArrayList;

public class Board{
    int idD;
    public Warehouse warehouse;
    protected StrongBox strongBox;
    protected Library[] l;
    public FaithTrack faithTrack;
    private static ArrayList<CardLeader> myLeaderCards = new ArrayList<>();

    public Board (int id){
        this.idD = id;
        /* Warehouse creation for a single Board */
        this.warehouse = new Warehouse ();
        /* Strongbox creation for a single Board */
        this.strongBox = new StrongBox ();
        /* Faith Track creation for a single board */
        this.faithTrack = new FaithTrack ();
    }

    public void setMyLeaderCards(ArrayList<CardLeader> cards){
        this.myLeaderCards = cards;
    }
}
