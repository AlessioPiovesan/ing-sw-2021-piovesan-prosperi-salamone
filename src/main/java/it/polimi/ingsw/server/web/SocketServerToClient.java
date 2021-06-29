package it.polimi.ingsw.server.web;

import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller.BoardController;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.viewer.RemoteView;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    private GameController gameController;
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

    public void setGameController(Integer id){
        gameController = Controller.getInstance().getGameControllerById(id);
    }

    private BoardController getBoardController(){
        var id = remoteView.getBoardId();
        return gameController.getBoardController(id);
    }

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

    public synchronized boolean isActive(){
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
            case "NPlayers" -> remoteView.setNPlayers(Integer.parseInt(string[1]));
            case "id" -> {
                if(string[1].equals("0")) remoteView.setGameId(null);
                else remoteView.setGameId(Integer.parseInt(string[1]));
                send("Setup complete");
                sendInLobby();
            }
            case "addFaithPnt" -> getBoardController().moveFT(1);
            case "checkNickname" -> server.checkExistNickname(string[1], this);
            case "initialLeaders" -> prepareInitialLeaders(string[1]);
            case "initialResource" -> server.firstRes(this);
            case "nextPlayer" -> gameController.nextPlayer();
            case "join" -> server.rejoin(Integer.parseInt(string[1]), string[2], this);
            case "warehouseResHandler" -> {
                var boardController = getBoardController();
                var color = Colours.fromString(string[1]);
                var qnt = Integer.parseInt(string[2]);
                var lvl = Integer.parseInt(string[3]);
                if(boardController.checkWarehouseAdd(new Pick(color, qnt), lvl))
                    boardController.warehouseResHandler(color, qnt, lvl);
                else send("preference::");
            }
            case "cardLeadAction" -> {
                var code1 = string[1].split(ConsoleColors.PURPLE);
                var code = code1[0].split(ConsoleColors.RESET);
                var card = (CardLeader)Objects.requireNonNull(CardLeader.getSpecificCard(code[0]));
                gameController.cardLeadAction(card);
            }
            case "cardDevAction" -> {
                var code1 = string[1].split(ConsoleColors.PURPLE);
                var code = code1[0].split(ConsoleColors.RESET);
                var slot = Integer.parseInt(string[2]);
                var card = (CardDev)Objects.requireNonNull(CardDev.getSpecificCard(code[0]));
                gameController.cardDevAction(card, slot);
            }
            case "checkVaticanReport" -> getBoardController().checkVaticanReport();
            case "marketAction" -> gameController.marketAction(Integer.parseInt(string[1]));
            case "moveFT" -> getBoardController().moveFT(Integer.parseInt(string[1]));
            case "endGame" -> gameController.endGame();
            default -> System.err.println(string[0] + " is not handled (SOCKETSERVERTOCLIENT)");
        }
    }

    private void prepareInitialLeaders(String src){
        try{
            var node = Json.parse(src);
            var initialLeaders = Json.fromJson(node, CardLeader[].class);
            var boardId = remoteView.getBoardId();
            var board = gameController.getBoardController(boardId);
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

    public RemoteView getRemoteView(){
        return remoteView;
    }
}
