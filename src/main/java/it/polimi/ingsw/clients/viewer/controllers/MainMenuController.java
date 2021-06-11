package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.singleplayer.web.Local;
import it.polimi.ingsw.clients.viewer.GUI;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.maven.scm.provider.git.gitexe.command.update.GitUpdateCommand;

public class MainMenuController implements GUIController{

    public static final String MAIN_MENU_FXML = "MainMenu.fxml";
    public AnchorPane mainPane;
    private GUI gui;

    public void quit(){
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    public void startSingleGame(){
        new Local(1).run();
    }

    @Override
    public void setGui(GUI gui){
        this.gui=gui;
    }

    @Override
    public Pane getMainPane(){
        return null;
    }
}
