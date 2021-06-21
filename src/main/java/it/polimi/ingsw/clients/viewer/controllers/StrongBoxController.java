package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * class StrongBoxController is the Controller for the strongbox later used in the Graphical User Interface.
 * It has FXML attributes for JavaFX
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
 */
public class StrongBoxController implements GUIController{


    @FXML private Label coinLabel;
    @FXML private Label servantLabel;
    @FXML private Label shieldLabel;
    @FXML private Label stoneLabel;
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
