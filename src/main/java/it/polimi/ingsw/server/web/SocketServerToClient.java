package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.viewer.RemoteView;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Class SocketServerToClient is the class used to create a connection between the Server and the clients.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 * @see Runnable
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

    public RemoteView getRemoteView(){return remoteView;}

    @Override
    public void run(){
        if(newSocket){
            newSocket = false;
            send("askNickname");
        }
        readFromClient();
    }

    /**
     * This send method is synchronized and is used to handle messages from the Server side.
     *
     * @param message of type Object
     */
    public void send(Object message){
        synchronized(socketOut){
            try{
                socketOut.writeObject(message);
                socketOut.flush();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Method readFromClient is the method used to read any inputs from the client's side.
     */
    private void readFromClient(){
        try{
            while(isActive()){
                synchronized(socketIn){
                    var inputObject = socketIn.readObject();
                    if(inputObject instanceof String) interpreter((String)inputObject);
                    else throw new IllegalArgumentException();
                }
            }
        } catch(NoSuchElementException|ClassNotFoundException|IllegalArgumentException e){
            System.err.println(e.getMessage());
            send("Error! " + e.getMessage());
            new Thread(this);
        } catch(EOFException eof){
            readFromClient();
        } catch(IOException ex){
            System.err.println(ex.getMessage());
            active = false;
        } finally{
            close();
        }
    }

    private synchronized boolean isActive(){
        return active;
    }

    /**
     * Method interpreter is used to interpreter messages from Clients.
     *
     * @param input of type String
     */
    private void interpreter(String input){
        var string = input.split("::");
        switch(string[0]){
            case "nickname" -> remoteView.setNickname(string[1]);
            case "nplayers" -> remoteView.setNPlayers(Integer.parseInt(string[1]));
            case "id" -> {
                if(string[1].equals("0")) remoteView.setGameId(null);
                else remoteView.setGameId(Integer.parseInt(string[1]));
                send("Setup complete");
                sendInLobby();
            }
            case "checkNickname" -> server.checkExistNickname(string[1], this);
            case "initialLeaders" -> prepareInitialLeaders(string[1]);
            default -> System.err.println(string[0] + " is not handled (SOCKETSERVERTOCLIENT)");
        }
    }

    private void prepareInitialLeaders(String src){
        try{
            var node = Json.parse(src);
            var initialLeaders = Json.fromJson(node, CardLeader[].class);
            var gameId = remoteView.getGameId();
            var game = Controller.getInstance().getGameControllerById(gameId);
            var boardId = remoteView.getBoardId();
            var board = game.getBoardController(boardId);
            board.setMyCardLeader(initialLeaders);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    public synchronized void closeConnection(){
        try{
            socket.close();
        } catch(IOException e){
            System.err.println("Error when closing socket!");
        }
        send("Connection closed!");
        active = false;
    }

    public void sendInLobby(){
        var name = remoteView.getNickname();
        var n = remoteView.getNPlayers();
        var id = remoteView.getGameId();
        server.lobby(this, name, n, id);
    }

    public void deleteGamer(String nickname){
        server.removePlayer(nickname);
    }
}
