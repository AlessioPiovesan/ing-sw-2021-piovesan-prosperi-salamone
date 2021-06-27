package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.model.ModelView;
import it.polimi.ingsw.clients.multiplayer.web.Client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface UI extends PropertyChangeListener{

    void setSingle(boolean single);

    void setClient(Client client);

    String getNickname();

    void setNickname(String name);

    void reChooseNickname(int id);

    void clientSetup();

    void showError(String message);

    void chooseNickname();

    void askNPlayers();

    void printMessage(String message);

    void askId();

    void propertyChangeMulti(PropertyChangeEvent pce);

    void setModelView(ModelView modelView);

    void resilience(int id, String name);
}
