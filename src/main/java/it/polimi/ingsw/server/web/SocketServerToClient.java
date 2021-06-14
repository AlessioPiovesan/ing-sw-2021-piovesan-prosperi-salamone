package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.viewer.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Class SocketServerToClient is the class used to create a connection between the Server and the clients.
 * @see Runnable
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
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

    /**
     * Method readFromClient is the method used to read any inputs from the client's side.
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
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

    /**
     * method asyncSend creates a new independent Thread to handle messages sent from the Server.
     * @param message of Type String
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void asyncSend(final String message){
        new Thread(() -> send(message)).start();
    }

    private synchronized boolean isActive(){
        return active;
    }

    /**
     * method interpreter is used to interpretate messages from Clients.
     * @param input of type String
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
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

    /**
     * This send method is synchronized and is used to handle messages from the Server side.
     * @param message of type Object
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
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
