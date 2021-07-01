package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SetupController implements GUIController{


    @FXML private AnchorPane mainPane;
    @FXML private TextField nicknameField;
    @FXML private TextField serverField;
    @FXML private TextField portField;
    @FXML private TextField idField;
    @FXML private TextField nPlayersField;
    private GUI gui;

    public void play(){
        var name = (String)nicknameField.getCharacters();
        var ip = (String)serverField.getCharacters();
        var port = Integer.parseInt((String)portField.getCharacters());
        Integer idGame = Integer.parseInt((String)idField.getCharacters());
        var n = Integer.parseInt((String)nPlayersField.getCharacters());
        gui.checkSetup(name, ip, port, idGame, n);
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
