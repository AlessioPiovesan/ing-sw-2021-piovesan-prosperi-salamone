package it.polimi.ingsw.server.viewer;

import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.web.SocketServerToClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
    private String nickname;
    private int nPlayers;
    private Integer gameId;

    public RemoteView(SocketServerToClient socketServerToClient){
        this.socketServerToClient = socketServerToClient;
    }

    public int getNPlayers(){
        return nPlayers;
    }

    public void setNPlayers(int nPlayers){
        this.nPlayers = nPlayers;
        socketServerToClient.send("askId::");
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        socketServerToClient.send(Json.toJson(evt));
    }

    public Integer getId(){
        return gameId;
    }

    public void setGameId(Integer gameId){
        this.gameId = gameId;
    }

    private void end(){
        socketServerToClient.deleteGamer(nickname);
    }
}
