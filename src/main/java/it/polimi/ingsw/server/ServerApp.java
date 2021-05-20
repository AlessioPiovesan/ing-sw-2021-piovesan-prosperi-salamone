package it.polimi.ingsw.server;

import it.polimi.ingsw.server.web.Server;

public class ServerApp {
    public static void start() {
        Server server;
        server = new Server();
        server.run();
        System.out.println("Si accende!");
    }
}


