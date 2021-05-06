package it.polimi.ingsw.server.accessories;

public interface Observer<T> {

    void update(T message);

}
