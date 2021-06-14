package it.polimi.ingsw;

import it.polimi.ingsw.clients.ClientApp;
import it.polimi.ingsw.server.ServerApp;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class MasterOfRenaissanceCLI starts Command Line Interface in case the player decides to play with it.
 * The Player can decide wether to launch a server (for Multiplayer purposes) or to play offline Single Player Mode.
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone
 */
public class MastersOfRenaissanceCLI{

    public static void main(String[] args){

        var exit = true;
        while(exit){
            exit = false;
            System.out.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
            System.out.println("by\n\u001B[34mAlessio Piovesan\u001B[0m,\n\u001B[32mPier Giorgio Prosperi\u001B[0m,\n\u001B[33mMatteo Salamone\u001B[0m,\n");
            System.out.print("""
                             Hi! Welcome to Masters of Renaissance!
                             What do you want to launch?
                             0. SERVER
                             1. CLIENT SINGLE PLAYER
                             2. CLIENT MULTIPLAYER
                             > Type the number of the desired option!
                             >>\040\040""");
            var scanner = new Scanner(System.in);
            var input = 0;
            try{
                input = scanner.nextInt();
            } catch(InputMismatchException e){
                System.err.println("Numeric format requested, application will now close...");
                System.exit(-1);
            } finally{
                switch(input){
                    case 0 -> ServerApp.start();
                    case 1, 2 -> ClientApp.start(input);
                    default -> {
                        exit = true;
                        System.err.println("Invalid argument, please run the executable again with a viable options");
                    }
                }
            }
        }
    }
}
