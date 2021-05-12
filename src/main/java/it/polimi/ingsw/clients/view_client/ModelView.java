package it.polimi.ingsw.clients.view_client;

public class ModelView {
    private CLI cli;
    private GUI gui;

    public ModelView(CLI cli) {
        this.cli = cli;
    }

    public ModelView(GUI gui) {
        this.gui = gui;
    }
}
