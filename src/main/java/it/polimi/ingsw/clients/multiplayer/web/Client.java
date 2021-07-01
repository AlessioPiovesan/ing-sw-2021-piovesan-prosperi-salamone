package it.polimi.ingsw.clients.multiplayer.web;

import it.polimi.ingsw.clients.multiplayer.model.ModelView;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Class Client handles Client side and receives messages from server
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */

public class Client implements Runnable{

    private final UI ui;
    private String ip;
    private Integer port;
    private boolean active;
    private int nPlayers;
    private Socket socket;
    private ObjectOutputStream socketOut;
    private ModelView modelView;

    public Client(int i){
        if(i == 0) ui = new CLI();
        else ui = new GUI();
        active = true;
    }

    public int getNPlayers(){
        return nPlayers;
    }

    public void setNPlayers(int n){
        nPlayers = n;
        setModelView();
    }

    /**
     * Method setModelView generates the Model View for a certain player
     */
    private void setModelView(){
        modelView = new ModelView(nPlayers, ui);
    }

    public UI getUi(){
        return ui;
    }

    public void run(){
        setup();
        connect();
        try{
            readingFromServer();
        } catch(IOException e){
            ui.showError(e.getMessage());
            try{
                socket.close();
            } catch(IOException ioException){
                ioException.printStackTrace();
            }

        }
    }

    private void setup(){
        ui.clientSetup();
    }

    /**
     * method connect establishes a connection from the client side
     */
    private void connect(){
        try{
            socket = new Socket(ip, port);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
        } catch(IOException e){
            ui.printMessage("Connection server from the client side");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method readingFromServer is used to read messages coming from the server side.
     *
     * @throws IOException standard IOException
     */
    private void readingFromServer() throws IOException{
        try(var socketIn = new ObjectInputStream(socket.getInputStream())){
            while(isActive()){
                synchronized(this){
                    var inputObject = socketIn.readObject();
                    if(inputObject instanceof String) interpreter((String)inputObject);
                    else throw new IllegalArgumentException();
                }
            }
        } catch(NoSuchElementException|ClassNotFoundException|IllegalArgumentException e){
            ui.printMessage(e.getMessage());
            send("Error! " + e.getMessage());
            new Thread(this);
        } catch(IOException ex){
            ui.showError(ex.getMessage());
            active = false;
        } finally{
            socket.close();
        }
    }

    public boolean isActive(){
        return active;
    }

    /**
     * Method interpreter interprets inputs from the client
     *
     * @param input is a message from the server
     */
    private void interpreter(String input){
        var string = input.split("::");
        switch(string[0]){
            case "Your game's ID is", "You joined the game with id" -> ui.printMessage(string[0] + " " + ConsoleColors.BLUE_BACKGROUND_BRIGHT + " " + string[1] + " " + ConsoleColors.RESET);
            case "pce" -> {
                var pce = recreatePropertyChangeEvent(string[1]);
                ui.propertyChangeMulti(pce);
            }
            case "nicknameNo" -> ui.reChooseNickname(Integer.parseInt(string[1]));
            case "nicknameMaybe" -> {
                ui.resilience(Integer.parseInt(string[1]), string[2], Integer.parseInt(string[3]));
                modelView.getBoard(Integer.parseInt(string[4])).setNickname(string[2]);
            }
            case "nicknameYes" -> setupGame(string[1]);
            case "nickname" -> modelView.getBoard(Integer.parseInt(string[2])).setNickname(string[1]);
            case "askNickname" -> ui.chooseNickname();
            case "askId" -> ui.askId();
            case "myBoardId" -> ui.setMyBoard(Integer.parseInt(string[1]));
            case "external" -> modelView.setExternalMarble(Colours.fromString(string[1]));
            case "tray" -> {
                try{
                    var node = Json.parse(string[1]);
                    var tray = Json.fromJson(node, Colours[][].class);
                    modelView.setMarket(tray);
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            case "leaderSpace" -> modelView.getBoard(Integer.parseInt(string[2])).addLeaderSpace(Colours.fromString(string[1]));
            case "leaderCards" -> modelView.getBoard(Integer.parseInt(string[3])).setMyLeaderCards(getLeaderCard(string[1]), Integer.parseInt(string[2]));
            case "warehouse" -> modelView.getBoard(Integer.parseInt(string[4])).setWarehouse(Integer.parseInt(string[1]), Colours.fromString(string[2]), Integer.parseInt(string[3]));
            case "resAll" -> modelView.getBoard(Integer.parseInt(string[3])).setResAll(Colours.fromString(string[1]), Integer.parseInt(string[2]));
            case "strongBox" -> modelView.getBoard(Integer.parseInt(string[3])).setStrongBox(Colours.fromString(string[1]), Integer.parseInt(string[2]));
            case "faithTrack" -> modelView.getBoard(Integer.parseInt(string[2])).setFaithTrack(Integer.parseInt(string[1]));
            case "newCard" -> modelView.getBoard(Integer.parseInt(string[3])).addCardinLibrary(getDevCard(string[1]), Integer.parseInt(string[2]));
            default -> ui.printMessage(string[0] + " -> (CLIENT)");
        }
    }

    /**
     * Method send sends messages.
     *
     * @param message is a message to the server
     */
    public void send(String message){
        try{
            socketOut.writeObject(message);
        } catch(IOException e){
            ui.showError(e.getMessage());
        }
    }

    /**
     * Method PrpertyChangeEvent generates a Property Change to the user
     *
     * @param src is the serialization for the server's event
     * @return the reconstructed event
     */
    private PropertyChangeEvent recreatePropertyChangeEvent(String src){
        PropertyChangeEvent pce = null;
        try{
            var node = Json.parse(src);
            var source = node.get("source");
            var propertyName = node.get("propertyName").asText();
            var oldValue = node.get("oldValue");
            var newValue = node.get("newValue");
            pce = new PropertyChangeEvent(source, propertyName, oldValue, newValue);
        } catch(IOException e){
            e.printStackTrace();
        }
        return pce;
    }

    /**
     * setupGame sets up a game after the player's nickname is confirmed
     *
     * @param name is the player's nickname
     * @see UI#askNPlayers()
     */
    private void setupGame(String name){
        ui.printMessage("Your nickname is confirmed");
        ui.setNickname(name);
        send("nickname::" + name);
        ui.askNPlayers();
    }

    private CardLeader getLeaderCard(String s){
        var code = s.split("LEAD");
        return (CardLeader)CardLeader.getSpecificCard(code[1]);
    }

    private CardDev getDevCard(String s){
        var code = s.split("_");
        return (CardDev)CardDev.getSpecificCard(code[1]);
    }

    public void setIP(String ip){
        this.ip = ip;
    }

    public String getIp(){
        return ip;
    }

    public Integer getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }
}
