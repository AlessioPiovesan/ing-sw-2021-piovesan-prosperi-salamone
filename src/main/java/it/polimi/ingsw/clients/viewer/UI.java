package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.server.accessories.cards.Card;

import java.beans.PropertyChangeListener;

public interface UI extends PropertyChangeListener{

    void showCards(Card card);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    void setSingle(boolean single);

    void showMarket();

}
