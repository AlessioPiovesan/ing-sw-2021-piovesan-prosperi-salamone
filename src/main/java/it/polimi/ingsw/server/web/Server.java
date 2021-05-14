package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.controller_server.Controller;
import it.polimi.ingsw.server.model_server.Mod;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12345;
    private final ServerSocket serverSocket;
    //private final Map<Integer, VirtualClient> idMapClient;
    /**
     * List of clients waiting in the lobby.
     */
    //private final List<SocketClientConnection> waiting = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final List<Connection> connections = new ArrayList<>();
    private final Map<String, Connection> waitingConnection = new HashMap<>();
    private final Map<Connection, Connection> playingConnection = new HashMap<>();
    int nextClientID;

    public Server() throws IOException {
        //idMapClient = new HashMap<>();
        this.serverSocket = new ServerSocket(PORT);
    }

    //Register connection
    private synchronized void registerConnection(Connection c) {
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c) {
        connections.remove(c);
        var opponent = playingConnection.get(c);
        if (opponent != null) {
            opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);
            waitingConnection.keySet().removeIf(s -> waitingConnection.get(s) == c);
        }
    }

    public synchronized void lobby(Connection c, String name) {
        waitingConnection.put(name, c);
        if (waitingConnection.size() == 2) {
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            var c1 = waitingConnection.get(keys.get(0));
            var c2 = waitingConnection.get(keys.get(1));
            //RemoteView player1 = new RemoteView(new Player(keys.get(0)), keys.get(1), c1);
            //RemoteView player2 = new RemoteView(new Player(keys.get(1)), keys.get(0), c2);
            var mod = new Mod();
            var controller = new Controller(mod);
            //mod.addObserver(player1);
            //mod.addObserver(player2);
            //player1.addObserver(controller);
            //player2.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();
        }
    }

    /*public GameController getGameByID(int id) {
        return idMapClient.get(id).getGameController();
    }*/

    public synchronized int createClientID() {

        int id = nextClientID;
        nextClientID++;
        return id;
    }

    public void run() {
        System.out.println("Server listening on port: " + PORT);
        while (true) {
            try {
                var socket = serverSocket.accept();
                var connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e) {
                System.err.println("Connection error!");
            }
        }
    }
}
