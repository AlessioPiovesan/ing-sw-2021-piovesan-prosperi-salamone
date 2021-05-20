package it.polimi.ingsw.clients.view_client;

import it.polimi.ingsw.server.accessories.cards.Card;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GUI implements Runnable, UI {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    @Override
    public void run() {

    }

    @Override
    public void showCards(Card list) {

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
