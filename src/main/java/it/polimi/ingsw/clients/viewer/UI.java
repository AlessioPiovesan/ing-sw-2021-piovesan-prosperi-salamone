package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;

import java.beans.PropertyChangeListener;

public interface UI extends PropertyChangeListener{

    void setSingle(boolean single);

    void setClient(Client client);

    String getNickname();

    void setNickname(String name);

    void reChooseNickname();

    void clientSetup();
}
