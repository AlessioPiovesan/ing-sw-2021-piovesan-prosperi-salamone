package it.polimi.ingsw.server.controller_server;


import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.plancia.Board;
import it.polimi.ingsw.server.viewer_server.Observer;

import java.util.List;

class BoardController implements Observer<Board>{
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

    private void addFaithPnt (int i, List<Colours> res){
        int a = player[i].checkDiscardedResources(res);
        for (int j = 0;j < controller.getGamers();j++){
            if (j != i){
                player[j].getFaithTrack().setPosition(a);
            }
        }

    }

    public void checkResAllPlayer (int i){
        System.out.println(player[i].getResAll());
    }

    private int getFirstPlayer(){
        return game.nextPlayerId();
    }

    @Override
    public void update (Board message){ }
}
