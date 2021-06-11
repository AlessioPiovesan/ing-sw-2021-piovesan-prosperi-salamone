package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Mod;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Market;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final LinkedList<String> players;
    private final Map<String, SocketServerToClient> waitingForConnection;
    private final Map<SocketServerToClient, SocketServerToClient> playingConnection;
    private final Map<SocketServerToClient, Integer> waitingForTheGameToStart;

    public Server(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        waitingForConnection = new HashMap<>();
        playingConnection = new HashMap<>();
        waitingForTheGameToStart = new HashMap<>();
        players = new LinkedList<>();
    }

    @Override
    public void run(){
        while(!serverSocket.isClosed()){
            try{
                var newSocket = serverSocket.accept();
                var socketConnection = new SocketServerToClient(newSocket, this);
                new Thread(socketConnection).start();
            } catch(IOException e){
                System.err.println("Connection Error!");
            }
        }
    }

    //Wait for the another players
    public synchronized void lobby(SocketServerToClient c, String name, int players){
        waitingForConnection.put(name, c);
        c.send("Hi " + name + " you are in the lobby, please wait.");
        if(waitingForConnection.size() == 1){
            var game = new Game(Mod.newID(), players, new Market());
            var gameControllerID = Controller.newID();
            new GameController(gameControllerID, 4, game);
            waitingForConnection.get(name).send("Your game's ID is:: " + gameControllerID);
            waitingForTheGameToStart.put(c, gameControllerID);
        }
        if(waitingForConnection.size() == players){
            var keys = new ArrayList<>(waitingForConnection.keySet());
            var game = new Game(Mod.newID(), players, new Market());
            var gameController = new GameController(Controller.newID(), 4, game);
            gameController.setBoardController();
            for(var i = 0; i < players; i++)
                waitingForConnection.get(keys.get(i)).getRemoteView().setPlayer(gameController.getGame().getSingleB(i));
            for(var i = 0; i < players; i++){
                gameController.getBoardController()[i].sendStatus();
            }
            for(var i = 0; i < players; i++){
                for(var j = 0; j < players; j++)
                    playingConnection.put(waitingForConnection.get(keys.get(i)), waitingForConnection.get(keys.get(j)));
            }
            for(var i = 0; i < players; i++)
                waitingForConnection.remove(keys.get(i));
        }
        else c.send(players - waitingForConnection.size() + " slots left");
    }

    //Deregister connection
    public synchronized void deregisterConnection(SocketServerToClient c){
        var opponent = playingConnection.get(c);
        while(opponent != null){
            opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);
            waitingForConnection.keySet().removeIf(s -> waitingForConnection.get(s) == c);
            opponent = playingConnection.get(c);
        }
    }

    private void addPlayer(String name){
        players.add(name);
    }

    private void removePlayer(String name){
        players.remove(name);
    }

    public void checkExistNickname(String s, SocketServerToClient socket){
        if(players.contains(s)) socket.send("nicknameNo::");
        else socket.send("nicknameYes::" + s);
    }
}