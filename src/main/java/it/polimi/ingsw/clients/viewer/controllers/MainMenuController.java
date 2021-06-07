package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuController{
    public static final String MAIN_MENU_FXML = "MainMenu.fxml";
    private GUI gui;
    @FXML private Label nickname;
    @FXML private Label address;
    @FXML private Label port;

    public void quit(){
        System.out.println("Thanks for playing!");
        System.exit(0);
    }
}
