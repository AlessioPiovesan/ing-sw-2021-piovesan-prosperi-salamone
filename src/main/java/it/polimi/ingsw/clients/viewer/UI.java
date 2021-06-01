package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.server.accessories.cards.Card;

import java.beans.PropertyChangeListener;

public interface UI extends PropertyChangeListener{

    void showCards(Card card);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    void setSingle(boolean single);

    void showMarket();

    void  run();

    void setClient(Client client);

}
