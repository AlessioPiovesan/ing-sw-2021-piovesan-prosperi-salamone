package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class StrongBoxController implements GUIController{


    @FXML private BorderPane mainPane;
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
