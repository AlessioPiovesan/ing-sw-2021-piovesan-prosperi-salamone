package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class BoardsController implements GUIController{

    public AnchorPane mainPane;
    private GUI gui;

    @Override
    public void setGui(GUI gui){
        this.gui = gui;
    }

    @Override
    public Pane getMainPane(){
        return mainPane;
    }
}
