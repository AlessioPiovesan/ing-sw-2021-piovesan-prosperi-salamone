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

public class MastersOfRenaissanceGUI extends Application{

    private static final String SETUP = "/fxml/setup.fxml";
    private static final String MENU = "/fxml/mainMenu.fxml";
    private static final String LOADER = "/fxml/loadingScreen.fxml";
    private static Scene currentScene;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        var root = (Parent)FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MENU)));
        Font.loadFont(getClass().getResourceAsStream("/font/Academy Engraved LET Fonts.ttf"), 25);
        currentScene = new Scene(root);
        stage.setTitle("Masters Of Renaissance");
        stage.setScene(currentScene);
        stage.show();
        var resize = new ResizeHandler((AnchorPane)currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
    }

    public static Scene getCurrentScene(){
        return currentScene;
    }

    public static void close(){

    }
}
