package it.polimi.ingsw.server.viewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.web.SocketServerToClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

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
    private int boardId;

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
        if(Objects.equals(evt.getPropertyName(), "endGame")) end();
        var node = Json.toJson(evt);
        var string = "";
        try{
            string = Json.stringify(node);
        } catch(JsonProcessingException e){
            e.printStackTrace();
        }
        socketServerToClient.send("pce::" + string);
    }

    private void end(){
        socketServerToClient.deleteGamer(nickname);
    }

    public Integer getGameId(){
        return gameId;
    }

    public void setGameId(Integer gameId){
        this.gameId = gameId;
    }

    public int getBoardId(){
        return boardId;
    }

    public void setBoardId(int boardId){
        this.boardId = boardId;
    }
}
