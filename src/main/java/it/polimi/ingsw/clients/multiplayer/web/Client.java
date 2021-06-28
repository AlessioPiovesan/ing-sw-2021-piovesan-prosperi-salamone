package it.polimi.ingsw.clients.multiplayer.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.clients.multiplayer.model.ModelView;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.clients.viewer.GUI;
import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.CardDev;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

/**
 * class Client handles Client side and receives messages from server
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */

public class Client implements Runnable{

    private final UI ui;
    private String ip;
    private int port;
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
     * method readingFromServer is used to read messages coming from the server side.
     *
     * @throws IOException
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
     * method interpreter interprets inputs from the client
     *
     * @param input
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
                modelView.getMyBoard().setNickname(string[2]);
            }
            case "nicknameYes" -> {
                setupGame(string[1]);
                modelView.getMyBoard().setNickname(string[1]);
            }
            case "nickname" -> modelView.getTheirBoards(Integer.parseInt(string[2])).setNickname(string[1]);
            case "askNickname" -> ui.chooseNickname();
            case "askId" -> ui.askId();
            case "myBoardId" -> modelView.setMyBoard(Integer.parseInt(string[1]));
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
            case "discount0" -> modelView.getMyBoard().setDiscount(0, Colours.fromString(string[1]));
            case "discount1" -> modelView.getMyBoard().setDiscount(1, Colours.fromString(string[1]));
            case "marketAlt0" -> modelView.getMyBoard().setMarketAlt(0, Colours.fromString(string[1]));
            case "marketAlt1" -> modelView.getMyBoard().setMarketAlt(1, Colours.fromString(string[1]));
            case "productAlt0" -> modelView.getMyBoard().setProductAlt(0, Colours.fromString(string[1]));
            case "productAlt1" -> modelView.getMyBoard().setProductAlt(1, Colours.fromString(string[1]));
            case "leaderSpace" -> modelView.getMyBoard().addLeaderSpace(Colours.fromString(string[1]));
            case "thislibrary" -> {
                try{
                    var node = Json.parse(string[1]);
                    var card = Json.fromJson(node, CardDev.class);
                    modelView.getMyBoard().addCardinLibrary(card, Integer.parseInt(string[2]));
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            case "thiswarehouse" -> {
                List<Pick> current = getList(string);
                modelView.getMyBoard().setWarehouse(current);
            }
            case "thisresAll" -> {
                Map<Colours, Integer> map = getMap(string);
                modelView.getMyBoard().setResAll(map);
            }
            case "thisstrongBox" -> {
                Map<Colours, Integer> map = getMap(string);
                modelView.getMyBoard().setStrongBox(map);
            }
            case "thisfaithTrack" -> modelView.getMyBoard().setFaithTrack(Integer.parseInt(string[1]));
            case "thatlibrary" -> {
                try{
                    var node = Json.parse(string[1]);
                    var card = Json.fromJson(node, CardDev.class);
                    modelView.getTheirBoards(Integer.parseInt(string[3])).addCardinLibrary(card, Integer.parseInt(string[2]));
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            case "thatwarehouse" -> {
                List<Pick> current = getList(string);
                modelView.getTheirBoards(Integer.parseInt(string[2])).setWarehouse(current);
            }
            case "thatresAll" -> {
                Map<Colours, Integer> map = getMap(string);
                modelView.getTheirBoards(Integer.parseInt(string[2])).setResAll(map);
            }
            case "thatstrongBox" -> {
                Map<Colours, Integer> map = getMap(string);
                modelView.getTheirBoards(Integer.parseInt(string[2])).setStrongBox(map);
            }
            case "thatfaithTrack" -> modelView.getTheirBoards(Integer.parseInt(string[2])).setFaithTrack(Integer.parseInt(string[1]));
            default -> ui.printMessage(string[0] + " -> (CLIENT)");
        }
    }

    /**
     * method send sends messages.
     *
     * @param message
     */
    public void send(String message){
        try{
            socketOut.writeObject(message);
        } catch(IOException e){
            ui.showError(e.getMessage());
        }
    }

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
     * @param name
     * @see UI#askNPlayers()
     */
    private void setupGame(String name){
        ui.printMessage("Your nickname is confirmed");
        ui.setNickname(name);
        send("nickname::" + name);
        ui.askNPlayers();
    }

    private List<Pick> getList(String[] string){
        var current = new LinkedList<Pick>();
        try{
            var node = Json.parse(string[1]);
            var list = Json.fromJson(node, Pick[].class);
            for(var thisPick : list){
                var color = thisPick.resource();
                var qnt = thisPick.qnt();
                var pick = new Pick(color, qnt);
                current.add(pick);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return current;
    }

    private Map<Colours, Integer> getMap(String[] string){
        var map = new EnumMap<Colours, Integer>(Colours.class);
        try{
            var node = Json.parse(string[1]);
            var strong = Json.fromJson(node, Map.class);
            for(var o : strong.keySet()){
                var color = Colours.fromString((String)o);
                var qnt = (int)strong.get(o);
                map.put(color, qnt);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return map;
    }

    public void setPort(int port){
        this.port = port;
    }

    public void setIP(String ip){
        this.ip = ip;
    }
}
