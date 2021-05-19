package it.polimi.ingsw.server.web;

import it.polimi.ingsw.clients.web.Client;
import it.polimi.ingsw.server.controller_server.Controller;
import it.polimi.ingsw.server.model_server.Mod;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final socketServer socketServer;
    private final int port = 12345;
    private int totalPlayers;
    private final List<socketClient> waiting = new ArrayList<>();

    public it.polimi.ingsw.server.web.socketServer getSocketServer() {
        return socketServer;
    }

    public void quitter(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            if (scanner.next().equalsIgnoreCase("QUIT")){
                getSocketServer().setActive(false);
                System.exit(0);
                break;
            }
        }
    }

    public Server(){
        socketServer = new socketServer(port, this);
        totalPlayers = -1;
        Thread thread = new Thread(this::quitter);
        thread.start();
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