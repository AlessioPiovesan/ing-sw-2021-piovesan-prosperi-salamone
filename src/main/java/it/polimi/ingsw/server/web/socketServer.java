package it.polimi.ingsw.server.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class socketServer implements Runnable {
    private final int port;
    private final ExecutorService executorService;
    private final Server server;
    private boolean active;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public socketServer(int port, Server server){
        this.server = server;
        this.port = port;
        executorService = Executors.newCachedThreadPool();
        active = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //Da questo punto newConnection -> socketClient

    @Override
    public void run() {

    }
}
