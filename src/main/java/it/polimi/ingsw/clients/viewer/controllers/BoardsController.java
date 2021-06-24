package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class BoardsController is the GUI controller of every player's board.
 * It uses FXML files for JavaFX
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class BoardsController implements GUIController{

    @FXML private ImageView redCrossImg;
    @FXML private ImageView blackCrossImg;
    @FXML private Label tile00;
    @FXML private Label tile01;
    @FXML private Label tile02;
    @FXML private Label tile03;
    @FXML private Label tile04;
    @FXML private Label tile05;
    @FXML private Label tile06;
    @FXML private Label tile07;
    @FXML private Label tile08;
    @FXML private Label tile09;
    @FXML private Label tile10;
    @FXML private Label tile11;
    @FXML private Label tile12;
    @FXML private Label tile13;
    @FXML private Label tile14;
    @FXML private Label tile15;
    @FXML private Label tile16;
    @FXML private Label tile17;
    @FXML private Label tile18;
    @FXML private Label tile19;
    @FXML private Label tile20;
    @FXML private Label tile21;
    @FXML private Label tile22;
    @FXML private Label tile23;
    @FXML private Label tile24;
    private ArrayList<Sphere> sphereList;
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
        sphereList = new ArrayList<>();
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
     * method openStrongBox graphically shows the content of the strongbox whenever a player clicks on it.
     *
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
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

    public void moveMarket(MouseEvent mouseEvent){
        var source = (Button)mouseEvent.getSource();
        var sourceId = source.getId();
        var move = getMove(sourceId);
        gui.moveMarket(move);
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

    public void moveFT(int pos){

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
}
