package it.polimi.ingsw.server.web;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class socketClient implements Runnable {
    private final Socket socket;
    private final Server server;
    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;
    private int clientID;
    private boolean active;
    private final Logger logger = Logger.getLogger(getClass().getName());

    private synchronized boolean isActive(){
        return active;
    }

    public socketClient (Socket socket, Server server){
        this.server = server;
        this.socket = socket;
        try{
            inputStream = new BufferedInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());
            clientID = -1;
            active = true;
        } catch (IOException e){
            System.err.println("Error during initialization of the client!");
            System.err.println(e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }


    @Override
    public void run() {

    }
}
