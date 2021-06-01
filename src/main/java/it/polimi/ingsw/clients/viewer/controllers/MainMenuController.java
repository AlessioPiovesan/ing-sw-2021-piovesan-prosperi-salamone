package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenuController{

    public static final String MAIN_MENU_FXML = "MainMenu.fxml";
    private static final String URL = "https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino";
    private GUI gui;
    private boolean muted;
    @FXML private TextField username;
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private Label confirmation;
    @FXML private ImageView music;

    /**
     * Method about opens the project home website.
     *
     * @throws URISyntaxException when the URI is not valid.
     * @throws IOException        when the browsing function stops working.
     */
    public void about() throws URISyntaxException, IOException{
        Desktop.getDesktop().browse(new URI(URL));
    }

    /**
     * Method quit kills the application when the "Quit" button is pressed.
     */
    public void quit(){
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    /**
     * Method play changes the stage scene to the setup one when the button "Play" is pressed.
     */
    //    public void play(){
    //        gui.changeStage("setup.fxml");
    //    }


    /**
     * Method start, bound to the setup FXML scene, it instantiates a socket connection with the
     * remote server and changes the scene to the loader one.
     */
    public void start(){}
}
