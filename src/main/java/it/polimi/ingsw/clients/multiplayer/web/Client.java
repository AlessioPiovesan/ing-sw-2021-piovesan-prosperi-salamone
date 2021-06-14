package it.polimi.ingsw.clients.multiplayer.web;

import it.polimi.ingsw.clients.multiplayer.model.ModelView;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Client{

    private final UI ui;
    private String ip;
    private int port;
    private boolean active;
    private int nPlayers;
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ModelView modelView;

    public Client(int i){
        if(i == 0) ui = new CLI();
        else ui = new GUI();
        setSingle();
        active = true;
    }

    private void setSingle(){
        ui.setSingle(false);
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
        try{
            while(isActive()){
                var inputObject = socketIn.readObject();
                if(inputObject instanceof String) interpreter((String)inputObject);
                else{
                    throw new IllegalArgumentException();
                }
            }
        } catch(IOException|NoSuchElementException|ClassNotFoundException e){
            ui.showError(e.getMessage());
            new Thread(new Runnable(){
                @Override
                public void run(){
                    run();
                }
            });
        }
    }

    private void setup(){
        ui.clientSetup();
    }

    private void connect(){
        try{
            socket = new Socket(ip, port);
            System.out.println("Connection established");
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new ObjectOutputStream(socket.getOutputStream());
        } catch(IOException e){
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
            case "nicknameNo" -> ui.reChooseNickname();
            case "nicknameYes" -> ui.setNickname(string[1]);
            case "askNickname" -> ui.chooseNickname();
            default -> System.out.println(string[0] + " is not handled (CLIENT)");
        }
    }


    public void setActive(boolean active){
        this.active = active;
    }

    public void setPort(int port){
        this.port = port;
    }

    public void setIP(String ip){
        this.ip = ip;
    }

    public synchronized void send(Object message){
        try{
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch(IOException e){
            asyncSend("Error in sending message: " + e.getMessage());
            ui.showError(e.getMessage());
        }
    }

    public void asyncSend(final Object message){
        new Thread(() -> send(message)).start();
    }
}
