package it.polimi.ingsw.server.controller_server;


import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.model_server.carte.CardLeader;
import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.plancia.Board;
import it.polimi.ingsw.server.viewer_server.Observer;

import java.util.ArrayList;
import java.util.List;

public class BoardController implements Observer<Board>{
    Game game;
    Controller controller;
    Board[] player;

    public BoardController (Game game, Board[] player){
        this.game = game;
        this.player = player;
    }

    public Game getMod ( ){
        return game;
    }

    public Board[] getPlayer ( ){
        return player;
    }

    private void addFaithPnt (int i, List<Colours> resExcess){
        int a = player[i].checkDiscardedResources(resExcess);
        for (var j = 0;j < controller.getGamers();j++){
            if (j != i){
                player[j].getFaithTrack().setPosition(a);
            }
        }

    }

    public void checkResAllPlayer (int i){
        System.out.println(player[i].getResAll());
    }

    public void pickCardLead (int id ){
        CardLeader[] choice = new CardLeader[2];
        System.out.println("Prima scelta");     /*Mostri le quattro carte e ne scegli una*/
        choice[0] =(CardLeader.card_Lead1);      /*Inserisci la carta scelta nella lista */
        System.out.println("Seconda scelta");   /*Mostri le tre carte e ne scegli una*/
        choice[1] = CardLeader.card_Lead2;      /*Inserisci la carta scelta nella lista */
        player[id].setMyLeaderCards(choice);
    }

    @Override
    public void update (Board message){
    }
}
