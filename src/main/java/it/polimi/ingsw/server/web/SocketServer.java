package it.polimi.ingsw.server.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class SocketServer implements Runnable {
    private final int port;
    private final ExecutorService executorService;
    private final Server server;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private boolean active;

    public SocketServer(int port, Server server) {
        this.server = server;
        this.port = port;
        executorService = Executors.newCachedThreadPool();
        active = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //Da questo punto newConnection -> SocketClient

    @Override
    public void run() {

    }
}
