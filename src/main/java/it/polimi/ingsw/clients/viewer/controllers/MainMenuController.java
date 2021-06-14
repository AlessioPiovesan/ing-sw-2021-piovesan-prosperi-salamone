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

public class MainMenuController implements GUIController{

    public static final String MAIN_MENU_FXML = "MainMenu.fxml";
    @FXML private Button singleButton;
    @FXML private Button multiButton;
    @FXML private AnchorPane mainPane;
    private GUI gui;

    public void quit(){
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    public void startSingleGame(){
        var client = new Local(1);
        var ui = (GUI)client.getUi();
        ui.setCurrentScene(MastersOfRenaissanceGUI.getCurrentScene());
        var stage = (Stage)singleButton.getScene().getWindow();
        stage.close();
        client.run();
    }

    public void startMultiGame(){
        var client = new Client(1);
        var ui = (GUI)client.getUi();
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
