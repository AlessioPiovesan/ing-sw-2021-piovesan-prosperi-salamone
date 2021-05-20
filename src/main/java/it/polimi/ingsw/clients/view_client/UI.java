package it.polimi.ingsw.clients.view_client;

import it.polimi.ingsw.server.accessories.cards.Card;

import java.beans.PropertyChangeListener;

public interface UI extends PropertyChangeListener {

    void run();

    void showCards(Card card);

    void setIfSingle(boolean b);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
