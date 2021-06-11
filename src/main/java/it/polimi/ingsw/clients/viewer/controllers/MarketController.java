package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.scene.layout.Pane;

public class MarketController implements GUIController{

    private GUI gui;

    @Override
    public void setGui(GUI gui){
        this.gui = gui;
    }

    @Override
    public Pane getMainPane(){
        return null;
    }
}
