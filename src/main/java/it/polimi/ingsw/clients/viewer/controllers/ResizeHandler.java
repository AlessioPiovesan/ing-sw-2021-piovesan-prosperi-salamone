package it.polimi.ingsw.clients.viewer.controllers;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class ResizeHandler is a class that is used by the Graphical User Interface.
 * This class is the one that handles the Window resizing since the scenes do not have a fixed size.
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
 */
public class ResizeHandler{
    private final ArrayList<Node> nodes;
    private final ArrayList<ArrayList<Double>> properties;
    private ChangeListener<Number> widthListener;
    private ChangeListener<Number> heightListener;
    private double rootWidth;
    private double rootHeight;

    /**
     * Constructor ResizeHandler creates a new ResizeHandler instance.
     *
     * @param pane of type Pane
     */
    public ResizeHandler(Pane pane){
        nodes = new ArrayList<>();
        properties = new ArrayList<>();
        calculateResize(pane, pane);
    }

    /**
     * Method calculateResize calculates resizing.
     *
     * @param pane   of type Pane - the root pane.
     * @param parent of type Pane - the original root pane.
     */
    public void calculateResize(Pane pane, Pane parent){
        if(pane.equals(parent)){
            properties.clear();
            nodes.clear();
            rootWidth = pane.getWidth();
            rootHeight = pane.getHeight();
        }
        for(var node : parent.getChildren()) evaluateNode(node, pane);
        widthListener = (observableValue, oldSceneWidth, newSceneWidth) -> {
            if(oldSceneWidth.intValue() <= 0){return;}
            for(var i = 0; i < nodes.size(); i++) newWidth(newSceneWidth, i);
        };
        heightListener = (observableValue, oldSceneHeight, newSceneHeight) -> {
            if(oldSceneHeight.intValue() <= 0){
                return;
            }
            for(var i = 0; i < nodes.size(); i++) resizeHeight(newSceneHeight, i);
        };
    }

    private void evaluateNode(Node node, Pane pane){
        double fontSize = 0;
        nodes.add(node);
        var bounds = node.getBoundsInLocal();
        if(node instanceof Labeled){
            fontSize = getFontSize(pane, (Labeled)node);
        }
        if(node instanceof TextField){
            fontSize = getFontSize(pane, (TextField)node);
        }
        properties.add((ArrayList<Double>)getNewParameters(pane, fontSize, node, bounds));
        if(isAPaneNotAGridPane(node)){
            assert node instanceof Pane;
            calculateResize(pane, (Pane)node);
        }
    }

    private boolean isAPaneNotAGridPane(Node node){
        return node instanceof Pane && !(node instanceof GridPane);
    }

    private void resizeHeight(Number newSceneHeight, int i){
        if(nodes.get(i) instanceof ImageView){
            ((ImageView)nodes.get(i)).setFitHeight(properties.get(i).get(1) * newSceneHeight.intValue());
            nodes.get(i).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
        }
        else if(nodes.get(i) instanceof AnchorPane && nodes.get(i).getId() != null && nodes.get(i).getId().equalsIgnoreCase("#gridPane")){
            double offset;
            nodes.get(i).setScaleY(newSceneHeight.doubleValue() / rootHeight);
            offset = ((nodes.get(i).getScaleY() - 1) / 2) * properties.get(i).get(1) * newSceneHeight.doubleValue();
            (nodes.get(i)).setLayoutY(offset + properties.get(i).get(3) * newSceneHeight.doubleValue());
        }
        else if(nodes.get(i) instanceof Region){
            ((Region)nodes.get(i)).setPrefHeight(properties.get(i).get(1) * newSceneHeight.intValue());
            nodes.get(i).setLayoutY(properties.get(i).get(3) * newSceneHeight.intValue());
        }
    }

    private void newWidth(Number newSceneWidth, int i){
        if(nodes.get(i) instanceof ImageView){
            ((ImageView)nodes.get(i)).setFitWidth(properties.get(i).get(0) * newSceneWidth.intValue());
            nodes.get(i).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
        }
        else if(nodes.get(i) instanceof AnchorPane && nodes.get(i).getId() != null && nodes.get(i).getId().equalsIgnoreCase("#gridPane")){
            double offset;
            nodes.get(i).setScaleX(newSceneWidth.doubleValue() / rootWidth);
            offset = ((nodes.get(i).getScaleX() - 1) / 2) * properties.get(i).get(0) * newSceneWidth.doubleValue();
            (nodes.get(i)).setLayoutX(offset + properties.get(i).get(2) * newSceneWidth.doubleValue());
        }
        else if(nodes.get(i) instanceof Region){
            ((Region)nodes.get(i)).setPrefWidth(properties.get(i).get(0) * newSceneWidth.intValue());
            nodes.get(i).setLayoutX(properties.get(i).get(2) * newSceneWidth.intValue());
            if(nodes.get(i) instanceof Labeled || nodes.get(i) instanceof TextField){
                int fontSizeTemp = (int)(properties.get(i).get(4) * newSceneWidth.intValue());
                nodes.get(i).setStyle("-fx-font-size: " + fontSizeTemp + "px;");
            }
        }
    }

    @NotNull
    private List<Double> getNewParameters(Pane pane, double fontSize, Node node, Bounds bounds){
        var boundsWPerPaneW = bounds.getWidth() / pane.getWidth();
        var boundsHPerPaneH = bounds.getHeight() / pane.getHeight();
        var nodeXPerPaneW = node.getLayoutX() / pane.getWidth();
        var nodeYPerPaneH = node.getLayoutY() / pane.getHeight();
        return new ArrayList<>(Arrays.asList(boundsWPerPaneW, boundsHPerPaneH, nodeXPerPaneW, nodeYPerPaneH, fontSize));
    }

    private double getFontSize(Pane pane, TextField node){
        double fontSize;
        fontSize = node.getFont().getSize() / pane.getWidth();
        return fontSize;
    }

    private double getFontSize(Pane pane, Labeled node){
        double fontSize;
        fontSize = node.getFont().getSize() / pane.getWidth();
        return fontSize;
    }

    /**
     * Method getWidthListener returns the widthListener of this ResizeHandler object.
     *
     * @return the widthListener (type ChangeListener&lt;Number&gt;) of this ResizeHandler object.
     */
    public ChangeListener<Number> getWidthListener(){
        return widthListener;
    }

    /**
     * Method getHeightListener returns the heightListener of this ResizeHandler object.
     *
     * @return the heightListener (type ChangeListener&lt;Number&gt;) of this ResizeHandler object.
     */
    public ChangeListener<Number> getHeightListener(){
        return heightListener;
    }
}

