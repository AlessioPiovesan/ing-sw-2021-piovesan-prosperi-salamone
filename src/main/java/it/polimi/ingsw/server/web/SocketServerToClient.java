package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.viewer.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class SocketServerToClient implements Runnable{

    private final Socket socket;
    private final Server server;
    private final RemoteView remoteView;
    private final ObjectOutputStream socketOut;
    private final ObjectInputStream socketIn;
    private boolean newSocket;
    private boolean active;

    public SocketServerToClient(Socket newSocket, Server server) throws IOException{
        this.socket = newSocket;
        this.server = server;
        remoteView = new RemoteView(this);
        active = true;
        this.newSocket = true;
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
    }

    public RemoteView getRemoteView(){
        return remoteView;
    }

    @Override
    public void run(){
        if(newSocket){
            newSocket = false;
            asyncSend("askNickname");
        }
        new Thread(this::readFromClient);
    }

    private void readFromClient(){
        try{
            while(isActive()){
                var inputObject = socketIn.readObject();
                if(inputObject instanceof String) interpreter((String)inputObject);
                else throw new IllegalArgumentException();
            }
        } catch(NoSuchElementException|ClassNotFoundException|IllegalArgumentException e){
            System.err.println(e.getMessage());
            asyncSend("Error! " + e.getMessage());
            new Thread(this);
        } catch(IOException ex){
            System.err.println(ex.getMessage());
            active = false;
        } finally{
            close();
        }
    }

    public void asyncSend(final String message){
        new Thread(() -> send(message)).start();
    }

    private synchronized boolean isActive(){
        return active;
    }

    private void interpreter(String input){
        var string = input.split("::");
        switch(string[0]){
            case "nickname" -> remoteView.setNickname(string[1]);
            case "nplayers" -> remoteView.setNPlayers(Integer.parseInt(string[1]));
            case "checkNickname" -> server.checkExistNickname(string[1], this);
            default -> System.err.println(string[0] + " is not handled (SOCKETSERVERTOCLIENT)");
        }
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    public synchronized void send(Object message){
        try{
            socketOut.writeObject(message);
            socketOut.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void closeConnection(){
        try{
            socket.close();
        } catch(IOException e){
            System.err.println("Error when closing socket!");
        }
        asyncSend("Connection closed!");
        active = false;
    }

    public void sendInLobby(){
        server.lobby(this, remoteView.getNickname(), remoteView.getnPlayers());
    }
}
