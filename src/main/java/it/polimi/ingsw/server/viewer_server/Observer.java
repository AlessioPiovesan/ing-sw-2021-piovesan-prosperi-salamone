package it.polimi.ingsw.server.viewer_server;

public interface Observer<T> {

    void update(T message);

}
