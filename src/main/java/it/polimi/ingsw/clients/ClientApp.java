package it.polimi.ingsw.clients;

import it.polimi.ingsw.clients.view_client.CLI;
import it.polimi.ingsw.clients.view_client.GUI;

public class ClientApp {

    private final String ip;
    private final int port;

    /*
     * @param ip of type String - ip reference, should be 127.0.0.1
     * @param port of type int - port reference, should be 12345
     */
    public ClientApp(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void startCLI() {
        CLI.main(new String[0]);
    }

    public static void startGUI() {
        System.out.println("You selected the GUI interface, have fun!\nStarting...");
        GUI.main(new String[0]);
    }
}


