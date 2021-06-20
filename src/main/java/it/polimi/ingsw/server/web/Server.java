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
    private final Map<SocketServerToClient, SocketServerToClient> playingConnection;
    private final Map<Integer, Map<Integer, List<String>>> waitingFor1Game;
    private final Mod model;
    private final Controller controller;

    public Server(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        waitingFor1Game = new HashMap<>();
        playingConnection = new HashMap<>();
        var forPlayers = new HashMap<Integer, List<String>>();
        waitingFor1Game.put(2, forPlayers);
        waitingFor1Game.put(3, forPlayers);
        waitingFor1Game.put(4, forPlayers);
        players = new HashMap<>();
        model = Mod.getInstance();
        controller = Controller.getInstance();
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
     * @param socketToClient  of type SocketServerToClient - Socket that represents the connection with a Client
     * @param name            of type String - Player's nickname
     * @param playersRequired of type Int - Number of Players
     * @param id
     */
    public synchronized void lobby(SocketServerToClient socketToClient, String name, int playersRequired, Integer id){
        socketToClient.send("Hi " + name + " you are in the lobby\nPlease wait for the other players to join");
        if(id == null) createNewGame(socketToClient, name, playersRequired);
        else joinNewGame(socketToClient, name, playersRequired, id);

        //        if(playersRequired == 3) waitingFor3Players.add(name);
        //        if(playersRequired == 4) waitingFor4Players.add(name);
        //        if(waitingForConnection.size() == 1){
        //            var game = new Game(model.newID(), playersRequired, new Market());
        //            var gameControllerID = controller.newID();
        //            new GameController(gameControllerID, playersRequired, game);
        //            waitingForConnection.get(name).send("Your game's ID is:: " + gameControllerID);
        //            waitingForTheGameToStart.put(socketToClient, gameControllerID);
        //        }
        //        if(waitingForConnection.size() == playersRequired){
        //            var keys = new ArrayList<>(waitingForConnection.keySet());
        //            var game = new Game(model.newID(), playersRequired, new Market());
        //            var gameController = new GameController(controller.newID(), playersRequired, game);
        //            gameController.setBoardController();
        //            for(var i = 0; i < playersRequired; i++)
        //                waitingForConnection.get(keys.get(i)).getRemoteView().setPlayer(gameController.getGame().getSingleB(i));
        //            for(var i = 0; i < playersRequired; i++){
        //                gameController.getBoardController()[i].sendStatus();
        //            }
        //            for(var i = 0; i < playersRequired; i++){
        //                for(var j = 0; j < playersRequired; j++)
        //                    playingConnection.put(waitingForConnection.get(keys.get(i)), waitingForConnection.get(keys.get(j)));
        //            }
        //            for(var i = 0; i < playersRequired; i++)
        //                waitingForConnection.remove(keys.get(i));
        //        }
        //        else socketToClient.send(playersRequired - waitingForConnection.size() + " slots left");
    }

    private void joinNewGame(SocketServerToClient socketToClient, String name, int playersRequired, Integer id){
        var thisGame = controller.getGameById(id);
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
            socketToClient.send("You joined the game with id: " + id);
        }
        if(waitingFor1Game.get(playersRequired).get(id).size() == playersRequired) startGame(playersRequired, id);
    }

    private void startGame(int playersRequired, int id){
        var playersForThisGame = waitingFor1Game.get(playersRequired).get(id);
        for(var name : playersForThisGame)
            addPlayer(name, id);
        controller.getGameControllerById(id).startGame();
    }

    private void createNewGame(SocketServerToClient socketToClient, String name, int playersRequired){
        var game = new Game(model.newID(), playersRequired, new Market());
        var gameControllerID = controller.newID();
        new GameController(gameControllerID, playersRequired, game);
        socketToClient.send("Your game's ID is:: " + gameControllerID);
        var map = waitingFor1Game.get(playersRequired);
        var list = new LinkedList<String>();
        list.add(name);
        map.put(gameControllerID, list);
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

    private void removePlayer(String name){
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