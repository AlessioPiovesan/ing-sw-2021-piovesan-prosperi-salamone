package it.polimi.ingsw;

import it.polimi.ingsw.clients.viewer.controllers.ResizeHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Class MastersOfRenaissanceGUI starts Graphical User Interface and prompts the Main Menu
 * to the User who can choose between Single Player, Multi Player or exiting the game.
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone
 * @see Application
 */
public class MastersOfRenaissanceGUI extends Application{

    private static final String MENU = "/fxml/mainMenu.fxml";
    private static Scene currentScene;

    public static void main(String[] args){
        launch(args);
    }

    public static Scene getCurrentScene(){
        return currentScene;
    }

    /**
     * Method start starts the scene of the Main Menu and shows it to the User.
     */

    @Override
    public void start(Stage stage) throws Exception{
        var root = (Parent)FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MENU)));
        Font.loadFont(getClass().getResourceAsStream("/font/Academy Engraved LET Fonts.ttf"), 25);
        newScene(root);
        stage.setTitle("Masters Of Renaissance");
        stage.setScene(currentScene);
        stage.show();
        var resize = new ResizeHandler((AnchorPane)currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
    }

    private static void newScene(Parent root){
        currentScene = new Scene(root);
    }
}
