package it.polimi.ingsw.client;

import org.apache.commons.validator.routines.InetAddressValidator;

import java.io.PrintStream;
import java.util.Scanner;

public class CLI implements Runnable{

    private final Scanner input;
    private final PrintStream output;
    private final boolean activatedGame;
    private ConnectionSocket connectionSocket;
    private static String ip;
    private static int port;

    public static void main (String[] args){
        //String ip = "";
        //int port = 0;
        System.out.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
        System.out.println("by\n" + "\033[34m" + "Alessio Piovesan" + "\033[0m" + ",\n" + "\033[32m" + "Pier Giorgio Prosperi" + "\033[0m" + ",\n" + "\033[33m" + "Matteo Salamone" + "\033[0m" + ",\n");
        System.out.println("View full rules here: " + "src/main/java/it/polimi/ingsw/Client/resources/Masters of Renaissance__Rules_ITA.pdf" + "\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println(">Insert the server IP address");
        System.out.print(">");
        while (!(InetAddressValidator.getInstance().isValid(ip))){
            ip = scanner.nextLine();
            if (!(InetAddressValidator.getInstance().isValid(ip)))
                System.out.println("Server IP address is not valid");
        }
        System.out.println(">Insert the server port");
        System.out.print(">");
        while (port < 1023 || port > 65535){
            port = scanner.nextInt();
            if (port < 1023 || port > 65535)
                System.out.println("Server port is not valid");
        }
        CLI cli = new CLI();
        cli.run();
    }

    public CLI ( ){
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        activatedGame = true;
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