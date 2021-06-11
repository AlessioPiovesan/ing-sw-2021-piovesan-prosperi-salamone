package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.enterprise.event.Event;
import java.util.ArrayList;
import java.util.LinkedList;

public class InitialLeader implements GUIController{
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    public AnchorPane mainPane;
    private GUI gui;
    private LinkedList<CardLeader> selected;
    private LinkedList<ImageView> cards;

    public InitialLeader(){
        selected = new LinkedList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
    }

    @Override
    public void setGui(GUI gui){
        this.gui = gui;
    }

    @Override
    public Pane getMainPane(){
        return mainPane;
    }

    public void selectCard(Event event){    }

    public void showCards(ArrayList<CardLeader> list){
        for(var i=0; i<4;i++){
        var path = getPath(list.get(i));
        cards.get(i).setImage(new Image(path));
        }
    }

    private String getPath(CardLeader card){
        return "graphics/cards/front/lead/"+card.name()+".png";
    }
}
