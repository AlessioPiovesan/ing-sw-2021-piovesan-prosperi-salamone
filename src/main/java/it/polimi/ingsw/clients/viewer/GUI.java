package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class GUI extends Application implements UI{

    private static final String SETUP = "setup.fxml";
    private static final String MENU = "MainMenu.fxml";
    private static final String LOADER = "loadingscreen.fxml";
    private static final String BOARDS = "boards.fxml";
    private boolean single;

    @Override
    public void setSingle(boolean single){

    }

    @Override
    public void setClient(Client client){ }

    @Override
    public String getIP(){
        return null;
    }

    @Override
    public int getPort(){
        return 0;
    }

    @Override
    public String getNickname(){
        return null;
    }

    @Override
    public int getNPlayers(){
        return 0;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        System.out.println(evt);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        var root = (Parent)FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MENU)));
        var setup = (Parent)FXMLLoader.load(Objects.requireNonNull(getClass().getResource(SETUP)));
        var mainScene = new Scene(root);

        primaryStage.setTitle("Masters Of Renaissance");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
