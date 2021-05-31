package it.polimi.ingsw.server;

import it.polimi.ingsw.server.web.Server;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServerApp{

    private static int port;

    private ServerApp(){
    }

    public static void start(){
        setPort();
        Server server = null;
        try{
            server = new Server(port);
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("It works!");
        if(server != null){
            server.run();
        }
    }

    private static void setPort(){
        try{
            System.out.print("Port?\n>\t");
            var scanner = new Scanner(System.in);
            port = scanner.nextInt();
            while(port < 1023 || port > 65535){
                System.out.println("Server port is not valid");
                port = scanner.nextInt();
            }
        } catch(InputMismatchException e){
            System.err.println("Numeric format requested, application will now close...\n" + e.getMessage());
            System.exit(-1);
        }
    }
}


