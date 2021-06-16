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

/**
 * After the server has been created, this class Server is used to handle multiple Socket Connections
 * from different clients.
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 * @see Runnable
 */
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
                System.out.println("New connection established");
                new Thread(socketConnection).start();
            } catch(IOException e){
                System.err.println("Connection Error!");
            }
        }
    }

    /**
     * Class Lobby is used to create a virtual room where players wait to join a game.
     * The first player who starts the server will be the Host of the game, others can join the same match by using a Game ID provided by the Host.
     *
     * @param socketToClient of type SocketServerToClient - Socket that represents the connection with a Client
     * @param name           of type String - Player's nickname
     * @param players        of type Int - Number of Players
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public synchronized void lobby(SocketServerToClient socketToClient, String name, int players){
        waitingForConnection.put(name, socketToClient);
        socketToClient.send("Hi " + name + " you are in the lobby, please wait.");
        if(waitingForConnection.size() == 1){
            var game = new Game(Mod.newID(), players, new Market());
            var gameControllerID = Controller.newID();
            new GameController(gameControllerID, 4, game);
            waitingForConnection.get(name).send("Your game's ID is:: " + gameControllerID);
            waitingForTheGameToStart.put(socketToClient, gameControllerID);
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
        else socketToClient.send(players - waitingForConnection.size() + " slots left");
    }

    /**
     * Method used in case a Player decides to leave the game.
     *
     * @param socketToClient of type SocketServerToClient - Socket that represents the connection with a Client
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public synchronized void deregisterConnection(SocketServerToClient socketToClient){
        var opponent = playingConnection.get(socketToClient);
        while(opponent != null){
            opponent.closeConnection();
            playingConnection.remove(socketToClient);
            playingConnection.remove(opponent);
            waitingForConnection.keySet().removeIf(s -> waitingForConnection.get(s) == socketToClient);
            opponent = playingConnection.get(socketToClient);
        }
    }

    private void addPlayer(String name){
        players.add(name);
    }

    private void removePlayer(String name){
        players.remove(name);
    }

    /**
     * Method checkExistNickname checks whether a new Nickname already exists inside the server.
     *
     * @param name   of type String - Player's Nickname
     * @param socket of type SocketServerToClient - Socket that represents the connection with a client.
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public void checkExistNickname(String name, SocketServerToClient socket){
        if(players.contains(name)) socket.send("nicknameNo::");
        else socket.send("nicknameYes::" + name);

    }
}