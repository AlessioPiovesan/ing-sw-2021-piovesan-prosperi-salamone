package it.polimi.ingsw.server.viewer_server;

import it.polimi.ingsw.server.controller_server.Controller;
import it.polimi.ingsw.server.model_server.Mod;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    private static final int PORT = 12345;
    private final ServerSocket serverSocket;
  //  private final Map<Integer, VirtualClient> idMapClient;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);

    private final List<Connection> connections = new ArrayList<Connection>();
    private final Map<String, Connection> waitingConnection = new HashMap<>();
    private final Map<Connection, Connection> playingConnection = new HashMap<>();

    public Server ( ) throws IOException{
        this.serverSocket = new ServerSocket(PORT);
    }

    //Register connection
    private synchronized void registerConnection (Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection (Connection c){
        connections.remove(c);
        Connection opponent = playingConnection.get(c);
        if (opponent != null){
            opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);
            Iterator<String> iterator = waitingConnection.keySet().iterator();
            while (iterator.hasNext()){
                if (waitingConnection.get(iterator.next()) == c){
                    iterator.remove();
                }
            }
        }
    }

    public synchronized void lobby (Connection c, String name){
        waitingConnection.put(name, c);
        if (waitingConnection.size() == 2){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            Connection c1 = waitingConnection.get(keys.get(0));
            Connection c2 = waitingConnection.get(keys.get(1));
            //RemoteView player1 = new RemoteView(new Player(keys.get(0)), keys.get(1), c1);
            //RemoteView player2 = new RemoteView(new Player(keys.get(1)), keys.get(0), c2);
            Mod mod = new Mod();
            Controller controller = new Controller(mod);
            //mod.addObserver(player1);
            //mod.addObserver(player2);
            //player1.addObserver(controller);
            //player2.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();
        }

    }

    public void run ( ){
        System.out.println("Server listening on port: " + PORT);
        while (true){
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e) {
                System.err.println("Connection error!");
            }
        }
    }

    /**
     * Method unregisterClient deletes a client from the hashmaps and active lists, unregistering his
     * connection with the server.
     *
     * @param clientID of type int - the ID of the virtual client to be removed.
     */
   /* public synchronized void unregisterClient (int clientID){
        getGameByID(clientID).unregisterPlayer(clientID);
        VirtualClient client = idMapClient.get(clientID);
        System.out.println(
                Constants.getInfo() + "Unregistering client " + client.getNickname() + "...");
        idMapClient.remove(clientID);
        nameMapId.remove(client.getNickname());
        waiting.remove(clientToConnection.get(client));
        idMapName.remove(client.getClientID());
        clientToConnection.remove(client);
        System.out.println(Constants.getInfo() + "Client has been successfully unregistered.");
    }*/

    /**
     * List of clients waiting in the lobby.
     */
    //private final List<SocketClientConnection> waiting = new ArrayList<>();

}
