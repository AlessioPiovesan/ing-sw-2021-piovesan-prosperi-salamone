package it.polimi.ingsw.client.view_client;

import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.accessori.InetAddressValidator;

import java.io.PrintStream;
import java.util.Scanner;

public class CLI implements Runnable{

    private static String ip = "";
    private static int port = 0;
    private final Scanner input;
    private final PrintStream output;
    private final boolean activatedGame;
    private ConnectionSocket connectionSocket;

    public CLI ( ){
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        activatedGame = true;
    }

    public static void main (String[] args){

        System.out.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
        System.out.println("by\n\u001B[34mAlessio Piovesan\u001B[0m,\n\u001B[32mPier Giorgio Prosperi\u001B[0m,\n\u001B[33mMatteo Salamone\u001B[0m,\n");
        //System.out.println("View full rules here: " + "URL delle regole" + "\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print(">\tInsert the server IP address\n>\t");
        while (!(InetAddressValidator.getInstance().isValid(ip))){
            ip = scanner.nextLine();
            if (!(InetAddressValidator.getInstance().isValid(ip)))
                System.out.println("Server IP address is not valid");

        }
        System.out.print(">\tInsert the server port\n>\t");
        while (port < 1023 || port > 65535){
            port = scanner.nextInt();
            if (port < 1023 || port > 65535)
                System.out.println("Server port is not valid");
        }
        CLI cli = new CLI();
        cli.run();
    }

    @Override
    public void run ( ){
        setup();
    }

    public void setup ( ){
        String nickname = null;
        while (nickname == null){
            System.out.println(">Insert your nickname: ");
            System.out.print(">");
            nickname = input.nextLine();
            System.out.println(">You chose: " + nickname);
            System.out.println(">Is it ok? [y/n] ");
            System.out.print(">");
            String r = input.nextLine();
            if (!(r.equals("y"))) nickname = null;
        }
        connectionSocket = new ConnectionSocket(ip, port);
    }
}