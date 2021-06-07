package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenuController {
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
