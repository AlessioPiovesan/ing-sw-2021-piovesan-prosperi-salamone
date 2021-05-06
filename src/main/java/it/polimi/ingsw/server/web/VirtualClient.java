package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.controller_server.GameController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class VirtualClient implements PropertyChangeListener {
    private int clientID;
    private String nickname;
    //private SocketClientConnection socketClientConnection;
    private GameController gameController;

    /**
     * Constructor VirtualClient creates a new VirtualClient instance.
     *
     * @param clientID               of type int - the client ID.
     * @param nickname               of type String - the player's nickname.
     * param socketClientConnection of type SocketClientConnection - the class linking client to
     *                               server via socket.
     * @param gameController         of type GameHandler - GameHandler reference.
     */
    public VirtualClient(
            int clientID,
            String nickname,
            //SocketClientConnection socketClientConnection,
            GameController gameController) {
        this.nickname = nickname;
        this.clientID = clientID;
        //this.socketClientConnection = socketClientConnection;
        this.gameController = gameController;
    }


    public VirtualClient() {
    }


    public int getClientID() {
        return clientID;
    }


   /* public boolean isConnected() {
        return (socketClientConnection != null);
    }*/

    public GameController getGameController() {
        return gameController;
    }

    public String getNickname() {
        return nickname;
    }

    /*public SocketClientConnection getConnection() {
        return socketClientConnection;
    }*/

   /* public void send (Answer serverAnswer){
        SerializedAnswer message = new SerializedAnswer();
        message.setServerAnswer(serverAnswer);
        socketClientConnection.sendSocketMessage(message);
    }

    public void win (Answer win){
        SerializedAnswer winner = new SerializedAnswer();
        winner.setServerAnswer(win);
        socketClientConnection.sendSocketMessage(winner);
        gameHandler.sendAllExcept(new LoseMessage(nickname), clientID);
        gameHandler.endGame();
    }

    public void sendAll (Answer serverAnswer){
        gameController.sendAll(serverAnswer);
    }*/

    @Override
    public void propertyChange (PropertyChangeEvent evt){
       /* if (evt.getNewValue() instanceof ChallengerMessages){
            send((ChallengerMessages)evt.getNewValue());
        }
        else if (evt.getNewValue() instanceof CustomMessage){
            send((CustomMessage)evt.getNewValue());
        }*/
    }
}
