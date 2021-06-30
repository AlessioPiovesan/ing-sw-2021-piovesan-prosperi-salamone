package it.polimi.ingsw.clients.singleplayer.web;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.singleplayer.model.Lorenzo;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.UI;

/**
 * class Local handles local's singleplayer game
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Local extends Client{

    private final UI ui;

    public Local(int i){
        super(i);
        if(i == 0) ui = new CLI();
        else ui = new GUI();
        setSingle();
    }

    private void setSingle(){
        ui.setSingle(true);
    }

    @Override
    public void run(){
        var gameController = LocalGameController.getInstance();
        var game = gameController.getGame();
        var lorenzo = (Lorenzo)game.getBoard(true);
        lorenzo.setUi(ui);
        var player = game.getBoard(false);
        player.addPropertyChangeListener(ui);
        lorenzo.addPropertyChangeListener(ui);
        gameController.startGame();
    }

}

