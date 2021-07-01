package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedList;

/**
 * class InitialLeader is the class responsible to show the players the initial Leader Cards that can be chosen in the GUI.
 * It uses FXML files for JavaFX
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class InitialLeader implements GUIController{

    private final LinkedList<CardLeader> selected;
    private final LinkedList<CardLeader> thisCards;
    @FXML private AnchorPane mainPane;
    @FXML private ImageView card1;
    @FXML private ImageView card2;
    @FXML private ImageView card3;
    @FXML private ImageView card4;
    private GUI gui;

    public InitialLeader(){
        selected = new LinkedList<>();
        thisCards = new LinkedList<>();
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
     * Method getCards is used to get the Cards' Images
     *
     * @param list is the four initial leader cards the player receives at the start o the game
     */
    public void getCards(CardLeader[] list){
        Collections.addAll(thisCards, list);
        card1.setImage(new Image(getPath(list[0])));
        card2.setImage(new Image(getPath(list[1])));
        card3.setImage(new Image(getPath(list[2])));
        card4.setImage(new Image(getPath(list[3])));
    }

    private String getPath(CardLeader card){
        return "/graphics/cards/front/lead/" + card.name() + ".png";
    }

    /**
     * Method addCard adds the Leader Cards based on the player's mouse action
     *
     * @param mouseEvent is the mouse click
     */
    public void addCard(MouseEvent mouseEvent){
        var source = (ImageView)mouseEvent.getSource();
        var id = source.getId();
        var thisCard = getCardLeader(id);
        selected.add(thisCard);
        if(selected.size() > 2) selected.remove(0);
    }

    @Nullable
    private CardLeader getCardLeader(String id){
        return switch(id){
            case "card1" -> thisCards.get(0);
            case "card2" -> thisCards.get(1);
            case "card3" -> thisCards.get(2);
            case "card4" -> thisCards.get(3);
            default -> null;
        };
    }

    public void confirmCards(){
        if(selected.size() == 2) gui.chooseInitialLeaders(selected.toArray(new CardLeader[2]));
    }
}
