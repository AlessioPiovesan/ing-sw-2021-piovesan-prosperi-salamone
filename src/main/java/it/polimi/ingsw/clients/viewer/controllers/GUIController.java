package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.scene.layout.Pane;

public interface GUIController{

    void setGui(GUI gui);

    Pane getMainPane();
}
