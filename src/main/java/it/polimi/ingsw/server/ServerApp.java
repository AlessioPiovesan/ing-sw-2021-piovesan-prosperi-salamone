package it.polimi.ingsw.server;

import it.polimi.ingsw.server.web.Server;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class ServerApp is used to create a Server, asking for the Port from which the Server will listen on.
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public class ServerApp{

    private static int port;

    private ServerApp(){}

    /**
     * Method start set the port for the TCP connections to the clients and starts the Server
     */
    public static void start(){
        setPort();
        Server server = null;
        try{
            server = new Server(port);
        } catch(IOException e){
            System.out.println("Error in instantiate the server because: " + e.getMessage());
        }
        System.out.println("It's ALIVE!");
        if(server != null){
            server.run();
        }
    }

    /**
     * Method setPort set the port for the TCP connections to the clients
     */
    private static void setPort(){
        try{
            System.out.print("Port?\n>\t");
            var scanner = new Scanner(System.in);
            port = scanner.nextInt();
            while(port < 1023 || port > 65535){
                System.out.println("Server port is not valid\n>\t");
                port = scanner.nextInt();
            }
        } catch(InputMismatchException e){
            System.err.println("Numeric format requested, application will now close...\n" + e.getMessage());
            System.exit(-1);
        }
    }
}


