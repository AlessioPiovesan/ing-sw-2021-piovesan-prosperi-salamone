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
    private boolean active;

    public SocketServerToClient(Socket newSocket, Server server){
        this.socket = newSocket;
        this.server = server;
        remoteView = new RemoteView(this);
        active = true;
    }

    public RemoteView getRemoteView(){
        return remoteView;
    }

    public void asyncSend(final Object message){
        new Thread(() -> send(message)).start();
    }

    public synchronized void send(Object message){
        try{
            var socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run(){
        sendInLobby();
        try(var socketIn = new ObjectInputStream(socket.getInputStream())){
            while(isActive()){
                var inputObject = socketIn.readObject();
                if(inputObject instanceof String){
                    interpreter((String)inputObject); /*DA MANDARE A UN QUALCHE TIPO DI INTERPRETE*/
                }
					/*else if(inputObject instanceof Board){
						((Board)inputObject).print();
					}*/ /*ALTRE COSE CHE PUO' RICEVERE*/
                else{
                    throw new IllegalArgumentException();
                }
            }
        } catch(IOException|NoSuchElementException|ClassNotFoundException e){
            System.err.println("Error!" + e.getMessage());
        } finally{
            close();
        }
    }

    private void sendInLobby(){
        server.lobby(this, remoteView.getNickname(), remoteView.getnPlayers());
    }

    private synchronized boolean isActive(){
        return active;
    }

    private void interpreter(String input){
        var string = input.split("::");
        switch(string[0]){
            case "nickname" -> remoteView.setNickname(string[1]);
            case "nplayers" -> remoteView.setNPlayers(Integer.parseInt(string[1]));
            default -> System.err.println(string[0] + " is not handled");
        }
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    public synchronized void closeConnection(){
        send("Connection closed!");
        try{
            socket.close();
        } catch(IOException e){
            System.err.println("Error when closing socket!");
        }
        active = false;
    }
}
