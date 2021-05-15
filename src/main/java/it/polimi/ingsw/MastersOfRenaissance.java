package it.polimi.ingsw;

import it.polimi.ingsw.clients.ClientApp;
import it.polimi.ingsw.server.ServerApp;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MastersOfRenaissance {

    public static void main(String[] args) {

        System.out.println("""
                Hi! Welcome to Masters of Renaissance!
                What do you want to launch?
                0. SERVER
                1. CLIENT (CLI INTERFACE)
                2. CLIENT (GUI INTERFACE)
                >Type the number of the desired option!
                >\040\040\040
                """);
        var scanner = new Scanner(System.in);
        var input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }
        switch (input) {
            case 0 -> ServerApp.main(new String[0]);
            case 1 -> ClientApp.startCLI();
            case 2 -> ClientApp.startGUI();
            default -> System.err.println("Invalid argument, please run the executable again with one of these options:\n1.server\n2.client");
        }
    }
}
