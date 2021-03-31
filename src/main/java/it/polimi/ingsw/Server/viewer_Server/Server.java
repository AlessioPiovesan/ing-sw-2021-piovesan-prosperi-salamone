package it.polimi.ingsw.Server.viewer_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool (128);
    private List<Connection> connections = new ArrayList<Connection> ();
    private Map<Connection, Connection> playingConnection = new HashMap<> ();

    //Register connection
    private synchronized void registerConnection (Connection c){
        connections.add (c);
    }

    //Deregister connection
    public synchronized void deregisterConnection (Connection c){
        connections.remove (c);
        Connection opponent = playingConnection.get (c);
        if (opponent != null){
            opponent.closeConnection ();
            playingConnection.remove (c);
            playingConnection.remove (opponent);
            //Iterator<String> iterator = waitingConnection.keySet().iterator();
            //while(iterator.hasNext()){
            //    if(waitingConnection.get(iterator.next())==c){
            //        iterator.remove();
            //    }
            //}
        }
    }

    public synchronized void lobby (Connection c, String name){
    }

}

