package it.polimi.ingsw.viewer;

public interface Observer<T> {

    void update ( T message );

}
