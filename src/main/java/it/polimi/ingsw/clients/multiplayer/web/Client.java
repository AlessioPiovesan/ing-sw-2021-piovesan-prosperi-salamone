package it.polimi.ingsw.clients.multiplayer.web;

import it.polimi.ingsw.clients.multiplayer.model.ModelView;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Client{

    private final UI ui;
    private final ModelView modelView;
    private String ip;
    private int port;
    private boolean active;
    private int nPlayers;
    private Socket socket;

    public Client(int i){
        if(i == 0) ui = new CLI();
        else ui = new GUI();
        modelView = new ModelView(nPlayers, ui);
        active = true;
    }

    public int getNPlayers(){
        return nPlayers;
    }

    public UI getUi(){
        return ui;
    }

    public void run(){
        setup();
        connect();
        try(var socketIn = new ObjectInputStream(socket.getInputStream())){
            while(isActive()){
                var inputObject = socketIn.readObject();
                if(inputObject instanceof String)
                    interpreter((String)inputObject); /*DA MANDARE A UN QUALCHE TIPO DI INTERPRETE*/
                else{
                    throw new IllegalArgumentException();
                }
            }
        } catch(IOException|NoSuchElementException|ClassNotFoundException e){
            System.err.println("Error!" + e.getMessage());
        }
    }

    private void setup(){
        ip = ui.getIP();
        port = ui.getPort();
        nPlayers = ui.getNPlayers();
    }

    private void connect(){
        try{
            socket = new Socket(ip, port);
            System.out.println("Connection established");
            var socketIn = new ObjectInputStream(socket.getInputStream());
            asyncReadFromSocket(socketIn);
        } catch(NoSuchElementException|IOException e){
            System.out.println("Connection closed from the client side");
            Thread.currentThread().interrupt();
        }
    }

    public boolean isActive(){
        return active;
    }

    private void interpreter(String input){
        var string = input.split("::");
        switch(string[0]){
            case "Your game's ID is" -> modelView.setGameID(Integer.parseInt(string[1]));
            default -> System.out.println(string[1] + " is not handled");
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
