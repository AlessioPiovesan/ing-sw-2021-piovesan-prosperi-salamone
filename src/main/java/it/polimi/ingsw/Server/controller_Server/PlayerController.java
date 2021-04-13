package it.polimi.ingsw.Server.controller_Server;


import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Gioco.Game;
import it.polimi.ingsw.Server.model_Server.Plancia.Board;

import java.util.HashMap;
import java.util.List;

class BoardController{
    Game game;
    Controller controller;
    Board player;

    public BoardController (Game game, Board player){
        this.game = game;
        this.player = player;
    }

    public Game getMod ( ){
        return game;
    }

    public Board getPlayer ( ){
        return player;
    }

    private void addFaithPnt (int i, List<Colours> res){
        int a = player.checkDiscardedResources(res);
        for (int j = 0;j < controller.getGamers();j++){
            if(j!=i){
            player.faithTrack.setPosition(a);
            }
        }

    }

    public void checkResAllPlayer(int i){
        System.out.println(player.getResAll());
    }
    }
