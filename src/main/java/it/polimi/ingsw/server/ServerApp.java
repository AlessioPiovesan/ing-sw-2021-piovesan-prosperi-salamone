package it.polimi.ingsw.server;

import it.polimi.ingsw.server.web.Server;

public class ServerApp {

    private ServerApp() {
    }

    public static void start() {
        // Chiedere la porta?
        var server = new Server();
        server.run();
        System.out.println("Si accende!");
    }
}


