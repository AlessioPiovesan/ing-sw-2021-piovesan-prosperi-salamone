package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Mod;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Market;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * After the server has been created, this class Server is used to handle multiple Socket Connections
 * from different clients.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 * @see Runnable
 */
public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final Map<String, Integer> players;
    private final Map<String, SocketServerToClient> playingPlayers;
    private final Map<SocketServerToClient, SocketServerToClient> playingConnection;
    private final Map<Integer, Map<Integer, List<String>>> waitingFor1Game;
    private final Controller controller;

    public Server(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        waitingFor1Game = new HashMap<>();
        playingConnection = new HashMap<>();
        playingPlayers = new HashMap<>();
        var forPlayers = new HashMap<Integer, List<String>>();
        waitingFor1Game.put(2, forPlayers);
        waitingFor1Game.put(3, forPlayers);
        waitingFor1Game.put(4, forPlayers);
        players = new HashMap<>();
        controller = Controller.getInstance();
    }

    @Override
    public void run(){
        while(!serverSocket.isClosed()){
            try{
                var newSocket = serverSocket.accept();
                var socketConnection = new SocketServerToClient(newSocket, this);
                System.out.println("New connection established -> " + newSocket.getLocalSocketAddress());
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
     * @param socketToClient  of type SocketServerToClient - Socket that represents the connection with a Client
     * @param name            of type String - Player's nickname
     * @param playersRequired of type Int - Number of Players
     * @param id
     */
    public synchronized void lobby(SocketServerToClient socketToClient, String name, int playersRequired, Integer id){
        socketToClient.send("Hi " + name + " you are in the lobby\nPlease wait for the other players to join");
        playingPlayers.put(name, socketToClient);
        if(id == null) createNewGame(socketToClient, name, playersRequired);
        else joinNewGame(socketToClient, name, playersRequired, id);
    }

    private void joinNewGame(SocketServerToClient socketToClient, String name, int playersRequired, Integer id){
        var thisGame = controller.getGameControllerById(id);
        if(thisGame == null){
            socketToClient.send("There is no game with this id\nYou will be redirected to a new game. ");
            lobby(socketToClient, name, playersRequired, null);
        }
        else{
            var list = waitingFor1Game.get(playersRequired).get(id);
            if(list != null) list.add(name);
            else{
                socketToClient.send("There is no game with this id and that number of players\nYou will be redirected to a new game. ");
                lobby(socketToClient, name, playersRequired, null);
            }
            socketToClient.send("You joined the game with id::" + id);
        }
        if(waitingFor1Game.get(playersRequired).get(id).size() == playersRequired) startGame(playersRequired, id);
    }

    private void startGame(int playersRequired, int id){
        var mapGameForThisNPlayers = waitingFor1Game.get(playersRequired);
        var playersForThisGame = mapGameForThisNPlayers.get(id);
        var gameController = controller.getGameControllerById(id);
        var i = 0;
        for(var name : playersForThisGame){
            var thisPlayerSocket = playingPlayers.get(name);
            registerConnection(thisPlayerSocket, playersForThisGame);
            addPlayer(name, id);
            var game = gameController.getGame();
            var board = game.getSingleB(i);
            var remoteView = thisPlayerSocket.getRemoteView();
            remoteView.setBoardId(i);
            board.addPropertyChangeListener(remoteView);
            remoteView.setGameId(id);
            i++;
        }
        controller.getGameControllerById(id).startGame();
    }

    private void createNewGame(SocketServerToClient socketToClient, String name, int playersRequired){
        var game = new Game(Mod.newID(), playersRequired, new Market());
        var gameControllerID = Controller.newID();
        var gameController = new GameController(gameControllerID, playersRequired, game);
        socketToClient.send("Your game's ID is::" + gameControllerID);
        controller.addGame(gameControllerID, gameController);
        var map = waitingFor1Game.get(playersRequired);
        var list = new LinkedList<String>();
        list.add(name);
        map.put(gameControllerID, list);
        waitingFor1Game.put(playersRequired, map);
    }

    private synchronized void registerConnection(SocketServerToClient thisSocket, List<String> list){
        for(var name : list){
            var socket = playingPlayers.get(name);
            if(thisSocket != socket) playingConnection.put(thisSocket, socket);
        }
    }

    /**
     * Method used in case a Player decides to leave the game.
     *
     * @param socketToClient of type SocketServerToClient - Socket that represents the connection with a Client
     */
    public synchronized void deregisterConnection(SocketServerToClient socketToClient){
        var opponent = playingConnection.get(socketToClient);
        while(opponent != null){
            opponent.closeConnection();
            playingConnection.remove(socketToClient);
            playingConnection.remove(opponent);
            opponent = playingConnection.get(socketToClient);
        }
    }

    private void addPlayer(String name, int id){
        players.put(name, id);
    }

    public void removePlayer(String name){
        players.remove(name);
    }

    /**
     * Method checkExistNickname checks whether a new Nickname already exists inside the server.
     *
     * @param name   of type String - Player's Nickname
     * @param socket of type SocketServerToClient - Socket that represents the connection with a client.
     */
    public void checkExistNickname(String name, SocketServerToClient socket){
        if(players.containsKey(name)){
            socket.send("nicknameExist::" + players.get(name));
        }
        else socket.send("nicknameYes::" + name);


    }
}