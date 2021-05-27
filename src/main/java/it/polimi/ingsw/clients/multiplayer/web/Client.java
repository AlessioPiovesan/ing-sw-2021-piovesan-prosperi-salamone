package it.polimi.ingsw.clients.multiplayer.web;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client{

    private final String ip;
    private final int port;
    private final UI ui;
    private boolean active;
    private int nPlayers;
    private Socket socket;

    /**
     * /*
     *
     * @param ip   of type String - ip reference, should be 127.0.0.1
     * @param port of type int - port reference, should be 12345
     */
    public Client(String ip, int port, int i){
        this.ip = ip;
        this.port = port;
        if(i == 0) ui = new CLI();
        else ui = new GUI();
        //new ModelView(ui);
        active = true;
    }

    public void setNPlayers(int players){this.nPlayers = players;}

    public int getnPlayers(){
        return nPlayers;
    }

    public UI getUi(){
        return ui;
    }

    public void run(){
        if(nPlayers == 1){
            var gameController = LocalGameController.getInstance();
            var game = gameController.getGame();
            game.getBoard(true).addPropertyChangeListener(ui);
            game.getBoard(false).addPropertyChangeListener(ui);
            gameController.startGame();
        }
        else{
            try{
                socket = new Socket(ip, port);
                System.out.println("Connection established");
                var socketIn = new ObjectInputStream(socket.getInputStream());
                var socketOut = new PrintWriter(socket.getOutputStream());
                var stdin = new Scanner(System.in);
                var t0 = asyncReadFromSocket(socketIn);
                t0.join();
                new CLI().run();
            } catch(InterruptedException|NoSuchElementException|IOException e){
                System.out.println("Connection closed from the client side");
                Thread.currentThread().interrupt();
            }
        }
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        var t = new Thread(() -> {
            try{
                while(isActive()){
                    var inputObject = socketIn.readObject();
                    if(inputObject instanceof String){
                        System.out.println((String)inputObject);
                    }
					/*else if(inputObject instanceof Board){
						((Board)inputObject).print();
					}*/ /*ALTRE COSE CHE PUO' RICEVERE*/
                    else{
                        throw new IllegalArgumentException();
                    }
                }
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncWriteToSocket(String command){
        var t = new Thread(() -> {
            try{
                var output = new PrintWriter(socket.getOutputStream(), true);
                while(isActive()){
                    output.write(command);
                }
            } catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }
}
