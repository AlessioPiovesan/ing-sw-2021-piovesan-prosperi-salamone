package it.polimi.ingsw.server.viewer_server;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private final List<it.polimi.ingsw.server.viewer_server.Observer<T>> observers = new ArrayList<>();

    public void addObserver(it.polimi.ingsw.server.viewer_server.Observer<T> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(it.polimi.ingsw.server.viewer_server.Observer<T> observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notify(T message) {
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }
    }

}
