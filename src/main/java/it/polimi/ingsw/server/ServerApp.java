package it.polimi.ingsw.server;

import it.polimi.ingsw.MastersOfRenaissance;
import it.polimi.ingsw.server.web.Server;

import java.io.IOException;

public class ServerApp {
    public static void start() {
        Server server;
        server = new Server();
        server.run();
        System.out.println("Si accende!");
    }
}


