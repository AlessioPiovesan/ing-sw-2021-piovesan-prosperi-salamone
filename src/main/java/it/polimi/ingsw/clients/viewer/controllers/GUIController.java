package it.polimi.ingsw.clients.viewer.controllers;

import it.polimi.ingsw.clients.viewer.GUI;
import javafx.scene.layout.Pane;

public interface GUIController{
    /**
     * Method setGui sets the GUI reference to the local controller.
     *
     * @param gui of type GUI - the main GUI class.
     */
    void setGui(GUI gui);

    Pane getMainPane();
}
