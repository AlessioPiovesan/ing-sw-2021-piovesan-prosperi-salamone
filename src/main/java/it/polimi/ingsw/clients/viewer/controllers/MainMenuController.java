package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.MastersOfRenaissanceGUI;
import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.web.Local;
import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * MainMenuController is the controller class for the scene Main Menu in the Graphical User Interface.
 * It uses FXML files for JavaFX
 *
 * @author Alessio Piovesan
 */
public class MainMenuController implements GUIController{

    @FXML private Button singleButton;
    @FXML private Button multiButton;
    @FXML private AnchorPane mainPane;
    private GUI gui;

    /**
     * Method quit quits the game if a player wants to
     *
     * @author Alessio Piovesan
     */
    public void quit(){
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    /**
     * Method startSingleGame starts a single player game if the player decides to
     *
     * @author Alessio Piovesan
     */
    public void startSingleGame(){
        var client = new Local(1);
        var ui = (GUI)client.getUi();
        ui.setCurrentScene(MastersOfRenaissanceGUI.getCurrentScene());
        var stage = (Stage)singleButton.getScene().getWindow();
        stage.close();
        client.run();
    }

    /**
     * Method startMultiGame starts a multiplayer game if the player decides to
     *
     * @author Alessio Piovesan
     */
    public void startMultiGame(){
        var client = new Client(1);
        var ui = (GUI)client.getUi();
        ui.setClient(client);
        ui.setCurrentScene(MastersOfRenaissanceGUI.getCurrentScene());
        var stage = (Stage)multiButton.getScene().getWindow();
        stage.close();
        client.run();
    }

    @Override
    public void setGui(GUI gui){
        this.gui = gui;
    }

    @Override
    public Pane getMainPane(){
        return mainPane;
    }
}
