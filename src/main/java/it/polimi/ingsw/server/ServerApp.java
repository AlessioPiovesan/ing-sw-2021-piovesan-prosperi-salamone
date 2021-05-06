package it.polimi.ingsw.server;

import it.polimi.ingsw.server.web.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.out.println("Impossible to start the server!\n" + e.getMessage());
        }

        System.out.println("Si accende!");

        /*crea controller e model*/
        /*riceve l'info dal client e dice al controller il numero di giocatori*/

    }
}


