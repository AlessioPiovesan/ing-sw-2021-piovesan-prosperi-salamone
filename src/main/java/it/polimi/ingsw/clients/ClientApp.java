package it.polimi.ingsw.clients;

import it.polimi.ingsw.clients.view_client.CLI;
import it.polimi.ingsw.clients.view_client.GUI;
import it.polimi.ingsw.server.controller_server.GameController;
import it.polimi.ingsw.server.model_server.game.Game;
import it.polimi.ingsw.server.model_server.game.Market;

import java.util.InputMismatchException;
import java.util.Scanner;

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

    public ClientApp() {
        ip = "localhost";
        port = 12345;
    }

    public static void startCLI(int input) {
        System.out.println("You selected the CLI interface, have fun!\nStarting...");
        if (input == 1){
            CLI cli = new CLI();
            cli.run();
        }
    }

    public static void startGUI(int input) {
        System.out.println("You selected the GUI interface, have fun!\nStarting...");
        GUI.start();
    }

    public static void chooseUI(int input) {
        var exit = true;
        while (exit) {
            exit = false;
            System.out.println("""
                    How do you wanna play?
                    0. COMMAND-LINE INTERFACE
                    1. GRAPHICAL USER INTERFACE
                    >Type the number of the desired option!
                    >\040""");
            var scanner = new Scanner(System.in);
            var ui = 0;
            try {
                ui = scanner.nextInt();
            } catch (InputMismatchException e) {
                exit = true;
            } finally {
                    if (ui == 0)
                        startCLI(input);
                    else startGUI(input);
            }
        }
    }
}


