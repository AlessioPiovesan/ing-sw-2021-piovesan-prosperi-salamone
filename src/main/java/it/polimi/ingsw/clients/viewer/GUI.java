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
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.*;

public class GUI extends Application implements UI{

    private static final String BOARDS = "/fxml/boards.fxml";
    private static final String INITIALLEADERCARDS = "/fxml/initialLeaderCards.fxml";
    private final HashMap<String, Scene> nameMapScene;
    private boolean single;
    private Scene currentScene;
    private Stage stage;
    private HashMap<String, GUIController> nameMapController;

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
        var fxmList = new ArrayList<>(Arrays.asList(BOARDS, INITIALLEADERCARDS));
        for(String path : fxmList){
            var loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(path)));
            var loaded = (Parent)loader.load();
            var controller = (GUIController)loader.getController();
            controller.setGui(this);
            nameMapController.put(path, controller);
            nameMapScene.put(path, new Scene(loaded));
        }
        currentScene = nameMapScene.get(BOARDS);
    }

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
    public void setNickname(String name){

    }

    @Override
    public void reChooseNickname(){

    }

    @Override
    public int getNPlayers(){
        return 0;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "temporaryLeaderCards" -> {
                changeStage(INITIALLEADERCARDS);
                chooseInitialLeaders((CardLeader[])evt.getNewValue());
            }
            default -> System.out.println(evt);
        }
    }

    public void changeStage(String newScene){
        currentScene = nameMapScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();
        var controller = (GUIController)new FXMLLoader(getClass().getResource("/fxml/" + newScene)).getController();
        var pane = controller.getMainPane();
        var resize = new ResizeHandler(pane);
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
    }

    private synchronized void chooseInitialLeaders(CardLeader[] cards){
        var list = new ArrayList<CardLeader>();
        var finale = new CardLeader[2];
        Collections.addAll(list, cards);
        var i = 0;
        var controller = new InitialLeader();
        while(i < 2){
            controller.showCards(list);
            //            var card = controller.pickCard(CardLeader.class);
            //            if(list.contains(card)){
            //                list.remove(card);
            //                finale[i] = card;
            //                i++;
            //            }
            //            else System.out.println("Something went wrong");
        }
        if(single) LocalGameController.getInstance().getBoardController().getPlayer().setMyLeaderCards(finale);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
    }
}
