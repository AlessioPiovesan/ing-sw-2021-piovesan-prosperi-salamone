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

public class Client implements Runnable{

    private final UI ui;
    private String ip;
    private int port;
    private boolean active;
    private int nPlayers;
    private Socket socket;
    private ObjectOutputStream socketOut;
    private ModelView modelView;

    public Client(int i){
        if(i == 0) ui = new CLI();
        else ui = new GUI();
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
        try{
            readingFromServer();
        } catch(IOException e){
            ui.showError(e.getMessage());
            try{
                socket.close();
            } catch(IOException ioException){
                ioException.printStackTrace();
            }

        }
    }

    private void setup(){
        ui.clientSetup();
    }

    private void connect(){
        try{
            socket = new Socket(ip, port);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
        } catch(IOException e){
            System.out.println("Connection server from the client side");
            Thread.currentThread().interrupt();
        }
    }

    private void readingFromServer() throws IOException{
        try(var socketIn = new ObjectInputStream(socket.getInputStream())){
            while(isActive()){
                synchronized(this){
                    var inputObject = socketIn.readObject();
                    if(inputObject instanceof String) interpreter((String)inputObject);
                    else throw new IllegalArgumentException();
                }
            }
        } catch(NoSuchElementException|ClassNotFoundException|IllegalArgumentException e){
            System.err.println(e.getMessage());
            asyncSend("Error! " + e.getMessage());
            new Thread(this::run);
        } catch(IOException ex){
            System.err.println(ex.getMessage());
            active = false;
        } finally{
            socket.close();
        }
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
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

    public void setPort(int port){
        this.port = port;
    }

    public void setIP(String ip){
        this.ip = ip;
    }

    public void asyncSend(final String message){
        new Thread(() -> send(message)).start();
    }

    public synchronized void send(String message){
        try{
            socketOut.writeObject(message);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
