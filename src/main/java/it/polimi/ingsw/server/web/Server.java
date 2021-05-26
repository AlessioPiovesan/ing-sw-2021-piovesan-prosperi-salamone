package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Mod;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.Market;
import it.polimi.ingsw.server.viewer.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable{

    private final ServerSocket serverSocket;
    private final ExecutorService executor;
    private final Map<String, SocketServerToClient> waitingForConnection;
    private final Map<SocketServerToClient, SocketServerToClient> playingConnection;

    public Server(int port) throws IOException{
        this.serverSocket = new ServerSocket(port);
        //It creates threads when necessary, otherwise it re-uses existing one when possible
        executor = new ThreadPoolExecutor(4, 20, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(true));
        waitingForConnection = new HashMap<>();
        playingConnection = new HashMap<>();
    }

    @Override
    public void run(){
        while(!serverSocket.isClosed()){
            try{
                var newSocket = serverSocket.accept();
                var socketConnection = new SocketServerToClient(newSocket, this);
                executor.submit(socketConnection);
            } catch(IOException e){
                System.err.println("Connection Error!");
            }
        }
    }

    //Wait for the another players
    public synchronized void lobby(SocketServerToClient c, String name, int players){
        waitingForConnection.put(name, c);
        c.send("Ciao " + name + " sei nella lobby, aspetta.");
        if(waitingForConnection.size() == players){ /*TARATO PER 4 GIOCATORI*/
            var keys = new ArrayList<>(waitingForConnection.keySet());
            var game = new Game(Mod.newID(), players, new Market());
            var gameController = new GameController(Controller.newID(), 4, game);
            gameController.setBoardController();
            for(var i = 0; i < players; i++)
                new RemoteView(waitingForConnection.get(keys.get(i)), gameController.getGame().getSingleB(i));
            for(var i = 0; i < players; i++){
                var statoditutteleboard = "";
                waitingForConnection.get(keys.get(i)).asyncSend(statoditutteleboard);
            }
            for(var i = 0; i < players; i++){
                for(var j = 0; j < players; j++)
                    playingConnection.put(waitingForConnection.get(keys.get(i)), waitingForConnection.get(keys.get(j)));
            }
            for(var i = 0; i < players; i++)
                waitingForConnection.remove(keys.get(i));
        }
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
}