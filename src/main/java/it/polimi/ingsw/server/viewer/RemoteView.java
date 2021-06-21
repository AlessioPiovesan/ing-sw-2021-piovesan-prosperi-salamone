package it.polimi.ingsw.server.viewer;

import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.web.SocketServerToClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

/**
 * Class RemoteView is used as a Listener for the model on the Server Side and makes messages ready for the Client.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 * @see PropertyChangeListener
 */
public class RemoteView implements PropertyChangeListener{

    private final SocketServerToClient socketServerToClient;
    private Board player;
    private String nickname;
    private int nPlayers;
    private Integer gameId;

    public RemoteView(SocketServerToClient socketServerToClient){
        this.socketServerToClient = socketServerToClient;
    }

    public void setPlayer(Board player){
        this.player = player;
    }

    public void setNPlayers(int nPlayers){
        this.nPlayers = nPlayers;
        socketServerToClient.send("askId::");
    }

    public int getnPlayers(){
        return nPlayers;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setMyLeaderCards(CardLeader[] myLeaderCards){
        socketServerToClient.send(Arrays.toString(myLeaderCards));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){

        switch(evt.getPropertyName()){
            case "active" -> {
                /*deve distribuire le risorse*/
            }
            case "boardStatus" -> System.err.println("WE'RE HERE");
            default -> System.err.println(" is not handled (REMOTEVIEW)");
        }
    }

    public Integer getId(){
        return gameId;
    }

    public void setGameId(Integer gameId){
        this.gameId = gameId;
        socketServerToClient.send("Setup complete");
        socketServerToClient.sendInLobby();
    }
}
