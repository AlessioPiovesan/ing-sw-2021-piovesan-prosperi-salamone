package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.controllers.GUIController;
import it.polimi.ingsw.clients.viewer.controllers.InitialLeader;
import it.polimi.ingsw.clients.viewer.controllers.ResizeHandler;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class GUI extends Application implements UI{

    private static final String BOARDS = "boards.fxml";
    private static final String INITIALLEADERCARDS = "initialLeaderCards.fxml";
    private static final String STRONGBOX = "strongBoxPane.fxml";
    private final HashMap<String, Scene> nameMapScene;
    private final HashMap<String, GUIController> nameMapController;
    private boolean single;
    private Scene currentScene;
    private Stage stage;

    public GUI(){
        nameMapScene = new HashMap<>();
        nameMapController = new HashMap<>();
        try{
            setup();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method setup creates all the stage phases which will be updated in other methods, in particular:
     * - MENU: the game's main menu with Play and Quit buttons;
     * - SETUP: small windows containing player's inserted nickname, the server IP and port;
     * - LOADER: the game loader containing player's chosen color, god power and places their workers;
     * - GUI: the effective game GUI (island board).
     * Each stage scene is put inside an hashmap, which links their name to their fxml filename.
     */
    public void setup() throws IOException{
        var fxmList = new ArrayList<>(Arrays.asList(INITIALLEADERCARDS, BOARDS, STRONGBOX));
        for(String path : fxmList){
            var thisPath = "/fxml/" + path;
            var loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(thisPath)));
            var root = (Parent)loader.load();
            var thisScene = new Scene(root);
            var controller = (GUIController)loader.getController();
            controller.setGui(this);
            nameMapController.put(path, controller);
            nameMapScene.put(path, thisScene);
        }
    }

    public void setCurrentScene(Scene currentScene){
        this.currentScene = currentScene;
    }

    @Override
    public void setSingle(boolean single){
        this.single = single;
    }

    @Override
    public void setClient(Client client){ }

    @Override
    public String getNickname(){
        return null;
    }

    @Override
    public void setNickname(String name){

    }

    @Override
    public void reChooseNickname(){

    }

    @Override
    public void clientSetup(){

    }

    @Override
    public void showError(String message){

    }

    @Override
    public void chooseNickname(){

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "temporaryLeaderCards" -> {
                start(new Stage());
                changeStage(INITIALLEADERCARDS);
                var controller = (InitialLeader)nameMapController.get(INITIALLEADERCARDS);
                controller.getCards((CardLeader[])evt.getNewValue());
            }
            default -> System.out.println(evt.getPropertyName() + " is not handled (GUI)");
        }
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Masters Of Renaissance");
        primaryStage.setScene(currentScene);
        this.stage = primaryStage;
        Font.loadFont(getClass().getResourceAsStream("/font/Academy Engraved LET Fonts.ttf"), 25);
    }

    public void changeStage(String newScene){
        currentScene = nameMapScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
        var controller = nameMapController.get(newScene);
        var pane = controller.getMainPane();
        var resize = new ResizeHandler(pane);
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
    }

    public void chooseInitialLeaders(CardLeader[] selected){
        if(single) LocalGameController.getInstance().getBoardController().player().setMyLeaderCards(selected);
    }
}
