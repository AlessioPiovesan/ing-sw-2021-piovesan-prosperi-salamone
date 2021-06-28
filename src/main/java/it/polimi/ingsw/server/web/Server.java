package it.polimi.ingsw.server.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.controller.BoardController;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Mod;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Market;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

/**
 * After the server has been created, this class Server is used to handle multiple Socket Connections
 * from different clients.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 * @see Runnable
 */
public class Server implements Runnable, PropertyChangeListener{

    private final ServerSocket serverSocket;
    private final Map<String, Integer> playersNameId;
    private final Map<String, SocketServerToClient> playingPlayersNameSocket;
    private final Map<SocketServerToClient, SocketServerToClient> playingConnection;
    private final Map<Integer, Map<Integer, List<String>>> waitingFor1Game;
    private final Map<Integer, Integer> firstRes;
    private final Controller controller;

    public Server(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        waitingFor1Game = new HashMap<>();
        playingConnection = new HashMap<>();
        firstRes = new HashMap<>();
        playingPlayersNameSocket = new HashMap<>();
        var forPlayers = new HashMap<Integer, List<String>>();
        waitingFor1Game.put(2, forPlayers);
        waitingFor1Game.put(3, forPlayers);
        waitingFor1Game.put(4, forPlayers);
        playersNameId = new HashMap<>();
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
     * @param id              of type Integer - id of the Game that is about to start
     */
    public synchronized void lobby(SocketServerToClient socketToClient, String name, int playersRequired, Integer id){
        socketToClient.send("Hi " + name + " you are in the lobby\nPlease wait for the other players to join");
        playingPlayersNameSocket.put(name, socketToClient);
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

    private void startGame(int playersRequired, Integer id){
        var mapGameForThisNPlayers = waitingFor1Game.get(playersRequired);
        var playersForThisGame = mapGameForThisNPlayers.get(id);
        var gameController = controller.getGameControllerById(id);
        var game = gameController.getGame();
        var market = game.getMarket();
        market.setId(id);
        market.setNPlayers(playersRequired);
        market.addPropertyChangeListener(this);
        var tray = market.getTray();
        var extern = game.getMarket().getExternal();
        var node = Json.toJson(tray);
        var marketString = "";
        try{
            marketString = Json.stringify(node);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        var i = 0;
        for(var name : playersForThisGame){
            var thisPlayerSocket = playingPlayersNameSocket.get(name);
            registerConnection(thisPlayerSocket, playersForThisGame);
            addPlayer(name, id);
            var board = game.getSingleB(i);
            board.setGameID(id);
            board.setNickname(name);
            board.addListener(this);
            var boardController = new BoardController(board, name);
            gameController.setBoardController(i, boardController);
            var remoteView = thisPlayerSocket.getRemoteView();
            thisPlayerSocket.setGameController(id);
            remoteView.setBoardId(i);
            thisPlayerSocket.send("myBoardId::" + i);
            board.addPropertyChangeListener(remoteView);
            remoteView.setGameId(id);
            i++;
        }
        sendMarket(marketString, playersRequired, id);
        sendExternalMarble(extern, playersRequired, id);
        sendNickname(playersRequired, id);
        controller.getGameControllerById(id).startGame();
    }

    private void sendNickname(int n, Integer id){
        var mapGameForThisNPlayers = waitingFor1Game.get(n);
        var playersForThisGame = mapGameForThisNPlayers.get(id);
        for(var name : playersForThisGame){
            var socket = playingPlayersNameSocket.get(name);
            var boardId = socket.getRemoteView().getBoardId();
            socket.send("nickname::" + name + "::" + boardId);
        }
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
        playersNameId.put(name, id);
    }

    public void removePlayer(String name){
        playersNameId.remove(name);
    }

    /**
     * Method checkExistNickname checks whether a new Nickname already exists inside the server.
     *
     * @param name   of type String - Player's Nickname
     * @param socket of type SocketServerToClient - Socket that represents the connection with a client.
     */
    public void checkExistNickname(String name, SocketServerToClient socket){
        if(playersNameId.containsKey(name)){
            var id = playersNameId.get(name);
            var playerSocket = playingPlayersNameSocket.get(name);
            var n = playerSocket.getRemoteView().getNPlayers();
            if(playerSocket.isActive()) socket.send("nicknameNo::" + playersNameId.get(name));
            else socket.send("nicknameMaybe::" + id + "::" + name + "::" + n);
        }
        else socket.send("nicknameYes::" + name);
    }

    public void firstRes(SocketServerToClient socket){
        var view = socket.getRemoteView();
        var nPlayers = view.getNPlayers();
        var id = view.getGameId();
        firstRes.merge(id, 1, Integer::sum);
        if(firstRes.get(id) == nPlayers){
            var gameController = controller.getGameControllerById(id);
            gameController.initialResourcesHandler();
        }
    }

    public void rejoin(int id, String name, SocketServerToClient socketServerToClient){
        playingPlayersNameSocket.put(name, socketServerToClient);
        List<String> playersForThisGame = null;
        for(var entry : waitingFor1Game.entrySet()){
            var thisMap = entry.getValue();
            if(thisMap.containsKey(id)) playersForThisGame = thisMap.get(id);
            if(playersForThisGame != null) break;
        }
        if(playersForThisGame != null) registerConnection(socketServerToClient, playersForThisGame);
        var gameController = controller.getGameControllerById(id);
        var remoteView = socketServerToClient.getRemoteView();
        socketServerToClient.setGameController(remoteView.getGameId());
        var bcs = gameController.getAllBoardController();
        for(var i = 0; i < bcs.length; i++){
            if(Objects.equals(bcs[i].getName(), name)) remoteView.setBoardId(i);
        }
        var game = gameController.getGame();
        var board = game.getSingleB(remoteView.getBoardId());
        board.addPropertyChangeListener(remoteView);
    }

    private synchronized void registerConnection(SocketServerToClient thisSocket, List<String> list){
        for(var name : list){
            var socket = playingPlayersNameSocket.get(name);
            if(thisSocket != socket) playingConnection.put(thisSocket, socket);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "market" -> getMarketAndSend(evt);
            case "external" -> getExternalMarbleAndSend(evt);
            case "discount0", "marketAlt0", "productAlt0", "discount1", "marketAlt1", "productAlt1", "leaderSpace" -> updateSingleBoard(evt);
            case "warehouse", "resAll", "strongBox", "faithTrack" + "library" -> updateAllBoard(evt);
            default -> System.err.println(evt.getPropertyName() + " is not handled (SERVER)");
        }
    }

    private void getMarketAndSend(PropertyChangeEvent evt){
        var source = (Market)evt.getSource();
        var n = source.getNPlayers();
        var id = source.getId();
        var node = Json.toJson(evt.getNewValue());
        var marketString = "";
        try{
            marketString = Json.stringify(node);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        sendMarket(marketString, n, id);
    }

    private void getExternalMarbleAndSend(PropertyChangeEvent evt){
        var source = (Market)evt.getSource();
        var n = source.getNPlayers();
        var id = source.getId();
        var color = (Colours)evt.getNewValue();
        sendExternalMarble(color, n, id);
    }

    private void updateSingleBoard(PropertyChangeEvent evt){
        var source = (Board)evt.getSource();
        var name = source.getNickname();
        var socket = playingPlayersNameSocket.get(name);
        socket.send(evt.getPropertyName() + "::" + evt.getNewValue());
    }

    private void updateAllBoard(PropertyChangeEvent evt){
        var source = (Board)evt.getSource();
        var gameID = source.getGameID();
        var boardID = source.getIdD();
        var n = controller.getGameControllerById(gameID).getAllBoardController().length;
        var node = Json.toJson(evt.getNewValue());
        var string = "";
        try{
            string = Json.stringify(node);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        var mapGameForThisNPlayers = waitingFor1Game.get(n);
        var playersForThisGame = mapGameForThisNPlayers.get(gameID);
        for(var name : playersForThisGame){
            var socket = playingPlayersNameSocket.get(name);
            var i = socket.getRemoteView().getBoardId();
            if(boardID == i) socket.send("this" + evt.getPropertyName() + "::" + string);
            else socket.send("that" + evt.getPropertyName() + "::" + string + "::" + i);
        }
    }

    private void sendMarket(String marketString, int n, int id){
        var mapGameForThisNPlayers = waitingFor1Game.get(n);
        var playersForThisGame = mapGameForThisNPlayers.get(id);
        for(var name : playersForThisGame){
            var socket = playingPlayersNameSocket.get(name);
            socket.send("tray::" + marketString);
        }
    }

    private void sendExternalMarble(Colours extern, int n, int id){
        var mapGameForThisNPlayers = waitingFor1Game.get(n);
        var playersForThisGame = mapGameForThisNPlayers.get(id);
        for(var name : playersForThisGame){
            var socket = playingPlayersNameSocket.get(name);
            socket.send("external::" + extern);
        }
    }
}