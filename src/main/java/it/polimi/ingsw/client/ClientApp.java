package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view_client.CLI;
import it.polimi.ingsw.client.view_client.GUI;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {

        var client = new Client("127.0.0.1", 12345);
        try {
            client.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Si accende!");

    }

    public static void startCLI() {
        CLI.main(new String[0]);
    }

    public static void startGUI() {
        System.out.println("You selected the GUI interface, have fun!\nStarting...");
        GUI.main(new String[0]);
    }
}


