package it.polimi.ingsw.client.web;

import it.polimi.ingsw.client.view_client.ModelView;

import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketListener implements Runnable {

    private final Socket socket;
    private final ModelView modelView;
    private final ObjectInputStream inputStream;

    public SocketListener(
            Socket socket,
            ModelView modelView,
            ObjectInputStream inputStream) {
        this.modelView = modelView;
        this.socket = socket;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
    }
}
