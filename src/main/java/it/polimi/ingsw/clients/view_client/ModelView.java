package it.polimi.ingsw.clients.view_client;

import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.viewer_server.RemoteView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ModelView implements PropertyChangeListener {
    private RemoteView remoteView;

    public ModelView(CLI cli) {
        cli.addPropertyChangeListener(this);
    }

    public ModelView(GUI gui) {
        gui.addPropertyChangeListener(this);
    }

    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt);
        switch (evt.getPropertyName()) {
            case "myLeaderCards": {
                remoteView.setMyLeaderCards((CardLeader[]) evt.getNewValue());
                break;
            }
            case "remoteView": {
                setRemoteView((RemoteView) evt.getNewValue());
                break;
            }
        }
    }
}
