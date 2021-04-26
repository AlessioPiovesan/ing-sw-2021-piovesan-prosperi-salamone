package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client{

    private final String ip;
    private final int port;


    /*
    * @param ip of type String - ip reference, should be 127.0.0.1
    * @param port of type int - port reference, should be 12345
     */
    public Client (String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run ( ) throws IOException{
        var socket = new Socket(ip, port);
        System.out.println("Connection established");
        var socketIn = new Scanner(socket.getInputStream());
        var socketOut = new PrintWriter(socket.getOutputStream());
        var stdIn = new Scanner(System.in);
        String socketLine;
        try {
            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            while (true){
                String inputLine = stdIn.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
                socketLine = socketIn.nextLine();
                System.out.println(socketLine);
            }
        } catch (
                NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            stdIn.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}
