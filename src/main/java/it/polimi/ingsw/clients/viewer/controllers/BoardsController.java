package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.utils.Utils;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * Class BoardsController is the GUI controller of every player's board.
 * It uses FXML files for JavaFX
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class BoardsController implements GUIController{

    @FXML private Button leader1Button;
    @FXML private Button leader2Button;
    @FXML private ImageView leader1;
    @FXML private ImageView leader2;
    @FXML private ImageView redCrossImg;
    @FXML private ImageView blackCrossImg;
    private ArrayList<AnchorPane> tileList;
    private ArrayList<Sphere> sphereList;
    private LinkedList<CardLeader> myLeaderCards;
    @FXML private AnchorPane tile00;
    @FXML private AnchorPane tile01;
    @FXML private AnchorPane tile02;
    @FXML private AnchorPane tile03;
    @FXML private AnchorPane tile04;
    @FXML private AnchorPane tile05;
    @FXML private AnchorPane tile06;
    @FXML private AnchorPane tile07;
    @FXML private AnchorPane tile08;
    @FXML private AnchorPane tile09;
    @FXML private AnchorPane tile10;
    @FXML private AnchorPane tile11;
    @FXML private AnchorPane tile12;
    @FXML private AnchorPane tile13;
    @FXML private AnchorPane tile14;
    @FXML private AnchorPane tile15;
    @FXML private AnchorPane tile16;
    @FXML private AnchorPane tile17;
    @FXML private AnchorPane tile18;
    @FXML private AnchorPane tile19;
    @FXML private AnchorPane tile20;
    @FXML private AnchorPane tile21;
    @FXML private AnchorPane tile22;
    @FXML private AnchorPane tile23;
    @FXML private AnchorPane tile24;
    @FXML private Sphere sphere0;
    @FXML private Sphere sphere1;
    @FXML private Sphere sphere2;
    @FXML private Sphere sphere3;
    @FXML private Sphere sphere4;
    @FXML private Sphere sphere5;
    @FXML private Sphere sphere6;
    @FXML private Sphere sphere7;
    @FXML private Sphere sphere8;
    @FXML private Sphere sphere9;
    @FXML private Sphere sphere10;
    @FXML private Sphere sphere11;
    @FXML private Sphere sphere12;
    @FXML private AnchorPane mainPane;
    @FXML private Button strongBoxButton;
    @FXML private Button button0;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button button3;
    @FXML private Button button4;
    @FXML private Button button5;
    @FXML private Button button6;
    private GUI gui;

    public BoardsController(){
        myLeaderCards = new LinkedList<>();
        createSphereList();
        createTileList();
        new Thread(() -> checkFT());
    }

    private void createSphereList(){
        sphereList = new ArrayList<>();
        sphereList.add(sphere1);
        sphereList.add(sphere2);
        sphereList.add(sphere3);
        sphereList.add(sphere4);
        sphereList.add(sphere5);
        sphereList.add(sphere6);
        sphereList.add(sphere7);
        sphereList.add(sphere8);
        sphereList.add(sphere9);
        sphereList.add(sphere10);
        sphereList.add(sphere11);
        sphereList.add(sphere12);
        sphereList.add(sphere0);
    }

    private void createTileList(){
        tileList = new ArrayList<>();
        tileList.add(tile00);
        tileList.add(tile01);
        tileList.add(tile02);
        tileList.add(tile03);
        tileList.add(tile04);
        tileList.add(tile05);
        tileList.add(tile06);
        tileList.add(tile07);
        tileList.add(tile08);
        tileList.add(tile09);
        tileList.add(tile10);
        tileList.add(tile11);
        tileList.add(tile12);
        tileList.add(tile13);
        tileList.add(tile14);
        tileList.add(tile15);
        tileList.add(tile16);
        tileList.add(tile17);
        tileList.add(tile18);
        tileList.add(tile19);
        tileList.add(tile20);
        tileList.add(tile21);
        tileList.add(tile22);
        tileList.add(tile23);
        tileList.add(tile24);
    }

    private void checkFT(){
        var you = gui.getFaithTrack();
        var him = gui.getLorenzo();
        tileList.get(you).getChildren().add(redCrossImg);
        if(him != null) tileList.get(him).getChildren().add(redCrossImg);
    }

    public void getCards(CardLeader[] list){
        Collections.addAll(myLeaderCards, list);
        leader1.setImage(new Image(Utils.getPath(list[0])));
        leader2.setImage(new Image(Utils.getPath(list[1])));
    }

    @Override
    public void setGui(GUI gui){
        this.gui = gui;
    }

    @Override
    public Pane getMainPane(){
        return mainPane;
    }

    /**
     * Method openStrongBox graphically shows the content of the strongbox whenever a player clicks on it.
     *
     * @author Alessio Piovesan
     * @author Pier Giorgio Prosperi
     * @author Matteo Salamone
     */
    public void openStrongBox(){
        try{
            var newStage = new Stage();
            newStage.setTitle("Strongbox content");
            newStage.setAlwaysOnTop(true);
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            var loader = new FXMLLoader((Objects.requireNonNull(getClass().getResource("/resources/fxml/strongBoxPane.fxml"))));
            var loaded = (Parent)loader.load();
            newStage.setScene(new Scene(loaded));
            newStage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method moveMarket indicates the player's move on the market
     *
     * @param mouseEvent is a mouse click
     */
    public void moveMarket(MouseEvent mouseEvent){
        var source = (Button)mouseEvent.getSource();
        var sourceId = source.getId();
        var move = getMove(sourceId);
        gui.marketAction(move);
    }

    private int getMove(String sourceId){
        return switch(sourceId){
            case "button0" -> 0;
            case "button1" -> 1;
            case "button2" -> 2;
            case "button3" -> 3;
            case "button4" -> 4;
            case "button5" -> 5;
            case "button6" -> 6;
            default -> -1;
        };
    }

    public void position(int pos){
        tileList.get(pos).getChildren();
    }

    public void colorMarket(Colours[][] tray, Colours extern){
        var listColorMarbles = new ArrayList<Color>();
        for(var j = 0; j < Constants.YMARKET.getVal(); j++){
            for(var i = 0; i < Constants.XMARKET.getVal(); i++){
                listColorMarbles.add(getColor(tray[i][j]));
            }
        }
        listColorMarbles.add(getColor(extern));
        fillMarble(listColorMarbles);
    }

    private Color getColor(Colours colours){
        return switch(colours){
            case YELLOW -> Color.YELLOW;
            case PURPLE -> Color.PURPLE;
            case WHITE -> Color.WHITE;
            case GREY -> Color.GRAY;
            case BLUE -> Color.BLUE;
            case RED -> Color.RED;
        };
    }

    private void fillMarble(List<Color> listColorMarbles){
        createSphereList();
        var materialList = new ArrayList<PhongMaterial>();
        var material = new PhongMaterial();
        material.setSpecularColor(Color.BLACK);
        for(var i = 0; i < listColorMarbles.size(); i++){
            material.setDiffuseColor(listColorMarbles.get(i));
            materialList.add(material);
            sphereList.get(i).setMaterial(materialList.get(i));
        }
    }

    public void activateLeader(MouseEvent mouseEvent){
        if(mouseEvent.getSource() == leader1Button) gui.wakeALeader(myLeaderCards.get(0));
        if(mouseEvent.getSource() == leader2Button) gui.wakeALeader(myLeaderCards.get(1));
    }

    public void buyCard(MouseEvent mouseEvent){
    }
}
