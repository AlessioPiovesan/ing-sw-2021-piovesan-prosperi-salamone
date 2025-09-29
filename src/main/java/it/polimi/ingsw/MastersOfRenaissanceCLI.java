package it.polimi.ingsw;

import it.polimi.ingsw.clients.ClientApp;
import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import it.polimi.ingsw.server.ServerApp;

import java.util.Scanner;

/**
 * Class MasterOfRenaissanceCLI starts Command Line Interface in case the player decides to play with it.
 * The Player can decide whether to launch a server (for multiplayer purposes) or to play offline in Single Player Mode.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class MastersOfRenaissanceCLI {

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);
        while (true) {
            // Title banner
            System.out.println("==========================================");
            System.out.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
            System.out.println("==========================================");
            System.out.println("by " + ConsoleColors.BLUE + "Alessio Piovesan\n" + ConsoleColors.GREEN + "   Pier Giorgio Prosperi\n" + ConsoleColors.YELLOW + "   Matteo Salamone" + ConsoleColors.RESET);
            // Menu
            System.out.print("""
                    Hi!\nWelcome to Masters of Renaissance!
                    
                    What would you like to launch?
                    0 - SERVER
                    1 - CLIENT SINGLE PLAYER
                    2 - CLIENT MULTIPLAYER
                    > Type the number of the desired option!
                    >>\040\040""");
            // Read choice safely
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter 0, 1, or 2.");
                continue;
            }
            switch (choice) {
                case 0 -> {
                    System.out.println("Server will be ready in a sec");
                    ServerApp.start();
                    return;
                }
                case 1 -> {
                    System.out.println("Your single player experience is about to begin");
                    ClientApp.start(choice);
                    return;
                }
                case 2 -> {
                    System.out.println("Your multiplayer experience is about to begin");
                    ClientApp.start(choice);
                    return;
                }
                default -> {
                    exit = true;
                    System.err.println("Unknown option. Please choose 0, 1, or 2.");
                }
            }
        }
    }
}
