package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import javafx.application.Application;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class GUI extends Application implements UI{

    private static final String SETUP = "setup.fxml";
    private static final String MENU = "MainMenu.fxml";
    private static final String LOADER = "loadingscreen.fxml";
    private static final String BOARDS = "boards.fxml";
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean single;

    /**
     * Main class of the GUI, which is called from the "Masters Of Renaissance" launcher
     * in case user decides to play with it.
     *
     * @param args of type String[] - parsed arguments.
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void setSingle(boolean single){

    }

    @Override
    public void showMarket(){

    }

    @Override
    public void run(){}

    @Override
    public void setClient(Client client){ }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        System.out.println(evt);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{ }
}
