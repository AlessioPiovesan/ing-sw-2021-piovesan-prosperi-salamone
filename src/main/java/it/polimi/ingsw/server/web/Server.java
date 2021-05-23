package it.polimi.ingsw.server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private static final int PORT = 12345;
    private int totalPlayers;

    public Server() {
        totalPlayers = -1;
        var thread = new Thread(this::quitter);
        thread.start();
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public void deregisterConnection(Connection connection) {
    }

    public void lobby(Connection connection, String name) {
    }

    public void quitter() {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                if (reader.readLine().equalsIgnoreCase("QUIT")) {
                    //getSocketServer().setActive(false);
                    System.exit(0);
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        //It creates threads when necessary, otherwise it re-uses existing one when possible
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println(e.getMessage()); //port not available
            return;
        }
        System.out.println("Server ready");
        while (!serverSocket.isClosed()) {
            try {
                var socket = serverSocket.accept();
                executor.submit(new ServerClientHandler(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}