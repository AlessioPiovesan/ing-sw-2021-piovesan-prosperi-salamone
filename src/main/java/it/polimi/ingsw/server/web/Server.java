package it.polimi.ingsw.server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 12345;
    private final SocketServer socketServer;
    private final List<SocketClient> waiting = new ArrayList<>();
    private int totalPlayers;

    public Server() {
        socketServer = new SocketServer(PORT, this);
        totalPlayers = -1;
        var thread = new Thread(this::quitter);
        thread.start();
    }

    public SocketServer getSocketServer() {
        return socketServer;
    }

    public void quitter() {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                if (reader.readLine().equalsIgnoreCase("QUIT")) {
                    getSocketServer().setActive(false);
                    System.exit(0);
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public void run() {
    }

    public void lobby(Connection connection, String name) {
    }

    public void deregisterConnection(Connection connection) {
    }
}