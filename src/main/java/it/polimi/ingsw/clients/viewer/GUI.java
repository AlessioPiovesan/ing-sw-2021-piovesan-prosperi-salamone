package it.polimi.ingsw.clients.viewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.clients.multiplayer.model.ModelBoard;
import it.polimi.ingsw.clients.multiplayer.model.ModelView;
import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.controllers.*;
import it.polimi.ingsw.clients.viewer.utils.Checker;
import it.polimi.ingsw.clients.viewer.utils.Utils;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Method GUI is used to create the Graphical User Interface of the game.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */

public class GUI extends Application implements UI{

    private static final String BOARDS = "boards.fxml";
    private static final String SINGLEBOARD = "singlePlayerBoards.fxml";
    private static final String INITIALLEADERCARDS = "initialLeaderCards.fxml";
    private static final String SETUP = "setup.fxml";
    private static final String STRONGBOX = "strongBoxPane.fxml";
    private final HashMap<String, Scene> nameMapScene;
    private final HashMap<String, GUIController> nameMapController;
    private final Utils utils;
    private final ExecutorService executor;
    private final Checker checker;
    private boolean single;
    private Scene currentScene;
    private Stage stage;
    private String nickname;
    private Client client;
    private ModelView model;
    private boolean endGame;
    private boolean winner;
    private ModelBoard myBoard;

    public GUI(){
        single = false;
        endGame = false;
        checker = new Checker(this);
        utils = new Utils(this);
        executor = new ThreadPoolExecutor(0, 16, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(true));
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
     * - MENU: the game's main menu with Single Player, Multi Player and Quit.
     * - SETUP: small windows containing player's inserted nickname, the server IP and port;
     * - LOADER: the game loader.
     * - GUI: the effective game GUI.
     * Each stage scene is put inside an hashmap, which links their name to their fxml filename.
     */
    public void setup() throws IOException{
        var fxmList = new ArrayList<>(Arrays.asList(INITIALLEADERCARDS, BOARDS, SINGLEBOARD, SETUP));
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
    public void setClient(Client client){this.client = client;}

    @Override
    public String getNickname(){
        return nickname;
    }

    @Override
    public void setNickname(String name){
        nickname = name;
    }

    @Override
    public void reChooseNickname(int id){

    }

    @Override
    public void clientSetup(){
        executor.execute(() -> {
            start(new Stage());
            changeStage(SETUP);
        });
        var controller = (SetupController)nameMapController.get(SETUP);
        controller.setGui(this);
        try{
            while(client.getIp() == null || client.getPort() == null) wait();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String message){

    }

    @Override
    public void chooseNickname(){

    }

    @Override
    public void askNPlayers(){

    }

    @Override
    public void printMessage(String message){

    }

    @Override
    public void askId(){

    }

    @Override
    public void propertyChangeMulti(PropertyChangeEvent evt){
        executor.execute(() -> {
            switch(evt.getPropertyName()){
                case "temporaryLeaderCards" -> initialLeaderScene(evt);
                //                case "firstOp" -> {
                //
                //            var newSceneName = "";
                //
                //             newSceneName = (BOARDS);
                //            var controller = (BoardsController)nameMapController.get(newSceneName);
                //            var tray = getLocalGameController().getGame().getMarket().getTray();
                //            var extern = getLocalGameController().getGame().getMarket().getExternal();
                //            getLocalGameController().nextPlayer();
                //            changeStage(newSceneName);
                //            controller.getCards(getMyCardLeader());
                //            controller.colorMarket(tray, extern);
                //                client.send("initialResource::");}
                //                case "redCrossMove" -> client.send("checkVaticanReport::");
                case "nextAction" -> nextAction();
                //                case "preference" -> chooseRes();
                //                case "doublePreference" -> doubleChooseRes();
                //                case "change" -> output.println(evt.getNewValue());
                //                case "marketBuffer" -> {
                //                    var current = new LinkedList<Colours>();
                //                    try{
                //                        var list = Json.fromJson((JsonNode)evt.getNewValue(), Colours[].class);
                //                        Collections.addAll(current, list);
                //                    } catch(IOException e){
                //                        e.printStackTrace();
                //                    }
                //                    resourcesHandlerMarket(current);
                //                }
                //                case "costCardDev" -> {
                //                    var current = new LinkedList<Pick>();
                //                    try{
                //                        var list = Json.fromJson((JsonNode)evt.getNewValue(), Pick[].class);
                //                        Collections.addAll(current, list);
                //                    } catch(IOException e){
                //                        e.printStackTrace();
                //                    }
                //                    payForCardDev(current);
                //                }
                //                case "winPoints" -> output.println("Points: " + evt.getNewValue());
                //                case "gameID" -> output.println("Your Game ID: " + evt.getNewValue() + "\nTell the other players");
                //                case "wrong" -> output.println("There's something wrong" + evt.getNewValue());
                default -> System.out.println(evt.getPropertyName() + " is not handled (GUI-MULTI)");
            }
        });
    }

    @Override
    public void setModelView(ModelView model){
        this.model = model;
    }

    @Override
    public void resilience(int i, String s, int n){

    }

    @Override
    public ModelBoard getMyBoard(){
        return myBoard;
    }

    @Override
    public void setMyBoard(int i){
        myBoard = model.getBoard(i);
    }

    private void initialLeaderScene(PropertyChangeEvent evt){
        start(new Stage());
        changeStage(INITIALLEADERCARDS);
        var controller = (InitialLeader)nameMapController.get(INITIALLEADERCARDS);
        controller.getCards((CardLeader[])evt.getNewValue());
    }

    private void nextAction(){
        if(single && getLocalGameController().isLorenzoPlaying()) getLocalGameController().moveLorenzo();
    }

    private LocalGameController getLocalGameController(){
        return LocalGameController.getInstance();
    }

    /**
     * method start creates the first stage where the scenes are going to be changed
     *
     * @param primaryStage of type Stage
     */
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Masters Of Renaissance");
        primaryStage.setScene(currentScene);
        this.stage = primaryStage;
        Font.loadFont(getClass().getResourceAsStream("/font/Academy Engraved LET Fonts.ttf"), 25);
    }

    /**
     * method changeStage is used to update the current scene shown to the player
     *
     * @param newScene of type String
     */
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

    /**
     * Method chooseInitialLeaders is used to record the initial Leader Card choice of the player
     *
     * @param selected of type CardLeader - player's selection
     */
    public void chooseInitialLeaders(CardLeader[] selected){
        if(single) LocalGameController.getInstance().getBoardController().player().setMyLeaderCards(selected);
        else{
            var node = Json.toJson(selected);
            var string = "";
            try{
                string = Json.stringify(node);
            } catch(JsonProcessingException e){
                e.printStackTrace();
            }
            client.send("initialLeaders::" + string);
        }
    }

    public CardLeader[] getMyCardLeader(){
        if(single) return getLocalGameController().getBoardController().player().getMyLeaderCards();
        else return getMyBoard().getMyLeaderCards();
    }

    public int getFaithTrack(){
        if(single) return getLocalGameController().getBoardController().player().getPositionFT();
        else return getMyBoard().getFaithTrack();
    }

    public Integer getLorenzo(){
        if(single) return getLocalGameController().getBoardController().lorenzo().getPositionFT();
        else return null;
    }

    public List<Pick> getWarehouse(){
        if(single) return getLocalGameController().getBoardController().player().checkWarehouseStatus();
        else return getMyBoard().getWarehouse();
    }

    public Map<Colours, Integer> getStrongBox(){
        if(single) return getLocalGameController().getBoardController().player().checkStrongBoxStatus();
        else return getMyBoard().getStrongBox();
    }

    public List<ArrayList<CardDev>> getLibrary(){
        if(single) return getLocalGameController().getBoardController().player().checkLibraryStatus();
        else return getMyBoard().getLibrary();
    }

    private void doubleChooseRes(){
        chooseRes();
        chooseRes();
    }

    public void wakeALeader(CardLeader card){
        if(single) getLocalGameController().cardLeadAction(card);
        else client.send("cardLeadAction::" + card.name());
    }

    private void prepareResForWarehouse(Colours colour, int qnt){
        if(single) prepareResForWarehouseS(colour, qnt);
        //        else prepareResForWarehouseM(colour, qnt);
    }

    /**
     * method prepareResForWarehouse is the method used to place resources in the warehouse.
     * The same method is used whenever a resource is taken from the warehouse by subtracting the value instead of adding it
     *
     * @param colour is a colour
     * @param qnt    is the quantity of the selected resource
     */
    private void prepareResForWarehouseS(Colours colour, int qnt){
        //        var newPick = new Pick(colour, qnt);
        //        var response = 0;
        //        var bc = getLocalGameController().getBoardController();
        //        if(colour == Colours.RED) bc.moveFT(1);
        //        else{
        //            output.print("Shelf for this resource? [1-3]\n" + Colours.getColoredType(colour) + "\n>\t");
        //            output.flush();
        //            var lvl = getWarehouseShelfLvl();
        //            var player = bc.player();
        //            while(!player.checkWarehouseAdd(newPick, lvl)){
        //                response = getResponse();
        //                lvl = actOnResponse(colour, response, lvl);
        //                if(response == 1) break;
        //            }
        //            if(response != 1) bc.warehouseResHandler(colour, qnt, lvl);
        //        }
    }

    private void chooseRes(){
        var dialog = new ChoiceDialog<String>(Colours.BLUE.getType(), Colours.GREY.getType(), Colours.PURPLE.getType(), Colours.YELLOW.getType());
        dialog.setContentText("Select a preferred resource to add to you belongings:");
        var response = dialog.showAndWait();
        response.ifPresent(s -> prepareResForWarehouse(Colours.fromString(s), 1));
    }

    /**
     * method propertyChange is part of the Observer Pattern that can react to Model's changes.
     * TEMPORARYLEADERCARDS : starts the GUI and calls for changeStage.
     *
     * @param evt is an event
     * @see #changeStage(String)
     * @see #start(Stage)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "temporaryLeaderCards" -> initialLeaderScene(evt);
            case "firstOp" -> {
                var newSceneName = "";
                if(single) newSceneName = (SINGLEBOARD);
                else newSceneName = (BOARDS);
                var controller = (BoardsController)nameMapController.get(newSceneName);
                var tray = getLocalGameController().getGame().getMarket().getTray();
                var extern = getLocalGameController().getGame().getMarket().getExternal();
                getLocalGameController().nextPlayer();
                changeStage(newSceneName);
                controller.getCards(getMyCardLeader());
                controller.colorMarket(tray, extern);
            }
            case "marketBuffer" -> {
                var list = new LinkedList<Colours>();
                for(var o : (Object[])evt.getNewValue())
                    list.add((Colours)o);
                resourcesHandlerMarket(list);
            }
            case "preference" -> chooseRes();
            case "doublePreference" -> doubleChooseRes();
            case "nextAction" -> nextAction();
            default -> System.out.println(evt.getPropertyName() + " is not handled (GUI)");
        }
    }

    private synchronized void resourcesHandlerMarket(List<Colours> bufferMarket){
        //                var keep = new ArrayList<Colours>();
        //                var loose = new ArrayList<Colours>();
        //                for(Colours colour : bufferMarket){
        //                    if(colour == Colours.WHITE) loose.add(colour);
        //                    else{
        //                        var j = resourcesDestination(colour, "\nDo you want to store it in your personal warehouse?\n0) No\n1)Yes\n>\t", "Command not valid, unknown");
        //                        if(j == 0) loose.add(colour);
        //                        else keep.add(colour);
        //                    }
        //                }
        //                if(single) getLocalGameController().addFaithPnt(loose);
        //                else client.send("addFaithPnt::" + loose.size());
        //                output.println("This resources need handling:");
        //                var colors = new StringBuilder();
        //                for(Colours colour : keep) colors.append(Colours.getColoredType(colour)).append("\t");
        //                output.println(colors);
        //                for(Colours colours : keep)
        //                    prepareResForWarehouse(colours, 1, single);
        //                drawer.showWarehouse(getWarehouse());
        //                if(single) getLocalGameController().nextPlayer();
        //                else{
        //                    output.println("Wait for next turn");
        //                    client.send("nextPlayer::");
        //                }
    }

    public void marketAction(int move){
        if(single) getLocalGameController().marketAction(move);
        else client.send("marketAction::" + move);
    }

    public void checkSetup(String name, String ip, int port, Integer idGame, int n){
        if(InetAddressValidator.getInstance().isValid(ip)) client.setIP(ip);
        if(port < 1023 || port > 65535) client.setPort(port);
        notifyAll();
        if(client.isActive()){
            client.send("nickname::" + name);
            client.send("NPlayers::" + n);
            client.send("id::" + idGame);
        }
    }
}
