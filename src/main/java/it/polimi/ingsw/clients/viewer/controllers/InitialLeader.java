package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;

public class InitialLeader implements GUIController{

    public AnchorPane mainPane;
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    private LinkedList<CardLeader> selected;
    private GUI gui;

    public InitialLeader(){
        selected = new LinkedList<>();
    }

    @Override
    public void setGui(GUI gui){
        this.gui = gui;
    }

    @Override
    public Pane getMainPane(){
        return mainPane;
    }

    public void showCards(List<CardLeader> list){
        card1.setImage(new Image(getPath(list.get(0))));
        card2.setImage(new Image(getPath(list.get(1))));
        card3.setImage(new Image(getPath(list.get(2))));
        card4.setImage(new Image(getPath(list.get(3))));
    }

    private String getPath(CardLeader card){
        return "graphics/cards/front/lead/" + card.name() + ".png";
    }

    public void addCard(MouseEvent mouseEvent){
        selected.add(CardLeader.CARDLEAD01);
        if(selected.size()>2) selected.remove(0);
    }

    public void confirmCards(MouseEvent mouseEvent){
        if(selected.size() == 2) gui.chooseInitialLeaders(selected.toArray(new CardLeader[2]));
    }
}
