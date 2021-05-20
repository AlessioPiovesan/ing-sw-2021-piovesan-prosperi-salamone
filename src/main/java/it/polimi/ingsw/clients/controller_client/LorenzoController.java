package it.polimi.ingsw.clients.controller_client;

import it.polimi.ingsw.clients.model_client.single.Lorenzo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LorenzoController {

    private final Lorenzo lorenzo;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public LorenzoController(Lorenzo player) {
        lorenzo = player;
    }

    public void active() {
        lorenzo.ready();
    }

    public Lorenzo getLorenzo() {
        return lorenzo;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
