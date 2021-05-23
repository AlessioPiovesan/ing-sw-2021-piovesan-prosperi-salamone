package it.polimi.ingsw.server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketClient implements Runnable {

    private final Socket socket;
    private final Server server;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final BufferedReader in;
    private final PrintWriter out;
    private int clientID;
    private boolean active;

    public SocketClient(Socket socket, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        clientID = -1;
        active = true;

    }

    private synchronized boolean isActive() {
        return active;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

    }
}
