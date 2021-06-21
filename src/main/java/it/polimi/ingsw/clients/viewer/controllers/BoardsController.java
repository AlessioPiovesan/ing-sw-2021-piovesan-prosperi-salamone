package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * class BoardsController is the GUI controller of every player's board.
 * It uses FXML files for JavaFX
 * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
 */
public class BoardsController implements GUIController{

    @FXML private AnchorPane mainPane;
    @FXML private Button strongBoxButton;
    private GUI gui;

    public BoardsController(){}

    @Override
    public void setGui(GUI gui){
        this.gui = gui;
    }

    @Override
    public Pane getMainPane(){
        return mainPane;
    }

    /**
     * method openStrongBox graphically shows the content of the strongbox whenever a player clicks on it.
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
     */
    public void openStrongBox(){
        try{
            var newStage = new Stage();
            newStage.setTitle("Strongbox content");
            newStage.setAlwaysOnTop(true);
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            var loaded = (Parent)FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/resources/fxml/strongBoxPane.fxml"))));
            newStage.setScene(new Scene(loaded));
            newStage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
