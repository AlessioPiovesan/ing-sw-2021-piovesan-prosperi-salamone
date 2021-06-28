package it.polimi.ingsw.clients.viewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.clients.multiplayer.model.ModelView;
import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.utils.Checker;
import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import it.polimi.ingsw.clients.viewer.utils.DrawerCLI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Json;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * class CLI creates the Command Line Interface which is on of the two ways to play the game
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class CLI implements Runnable, UI{

    private final Scanner input;
    private final PrintWriter output;
    private final ExecutorService executor;
    private final Checker checker;
    private final DrawerCLI drawer;
    private String nickname;
    private boolean single;
    private Client client;
    private ModelView model;

    public CLI(){
        single = false;
        checker = new Checker(this);
        drawer = new DrawerCLI(this);
        input = new Scanner(System.in);
        output = new PrintWriter(System.out, true);
        executor = new ThreadPoolExecutor(0, 16, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(true));
    }

    public void run(){
        chooseNickname();
        client.send("nickname::" + nickname);
        client.send("nplayers::" + client.getNPlayers());
    }

    /**
     * method propertyChange is part of the Observer Pattern that can react to Model's changes.
     * TEMPORARYLEADERCARDS: used to choose the two initial leader cards
     * FIRSTOP: starts the game if single player, else handles the resources
     * NEXTPLAYER: used for single player mode to decide the next player between Lorenzo and the actual player
     * LORENZOACTIONPRINT: prints action from Lorenzo
     * REDCROSSMOVE: moves the pawn on the faith track
     * NEXTACTION: calls method nextAction, used to ask the player what is his next action
     * MARKETBUFFER: buffer for the market
     * PRODUCTIONBUFFER: buffer for the production
     * COSTCARDDEV: resources needed to buy a Development Card
     * GAMEID: prints the game's id
     * WRONG: used in case of a wrong input from the player
     *
     * @param evt of type PropertyChangeEvent
     * @see #nextAction()
     * @see #resourcesHandlerMarket(List)
     * @see #resourcesHandler(List)
     * @see #payForCardDev(List)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        executor.execute(() -> {
            switch(evt.getPropertyName()){
                case "temporaryLeaderCards" -> chooseInitialLeaders((CardLeader[])evt.getNewValue());
                case "firstOp", "nextPlayer" -> getLocalGameController().nextPlayer();
                case "redCrossMove" -> getLocalGameController().getBoardController().checkVaticanReport();
                case "nextAction" -> nextAction();
                case "marketBuffer" -> resourcesHandlerMarket(Checker.castList(Colours.class, (Collection<?>)evt.getNewValue()));
                case "productionBuffer" -> resourcesHandler(Checker.castList(Pick.class, (Collection<?>)evt.getNewValue()));
                case "costCardDev" -> payForCardDev(Checker.castList(Pick.class, (Collection<?>)evt.getNewValue()));
                case "gameID" -> output.println("Your Game ID: " + evt.getNewValue() + "\nTell the other players");
                case "wrong" -> output.println("There's something wrong" + evt.getNewValue());
                default -> output.println(evt.getPropertyName() + " is not handled (CLI)");
            }
        });
    }

    /**
     * method payForCardDev is used to handle the payment of a Development Card.
     *
     * @param cost of type List of Pick
     * @see DrawerCLI#showWarehouse(List)
     * @see DrawerCLI#showStrongBox(Map)
     */
    private synchronized void payForCardDev(List<Pick> cost){
        drawer.showWarehouse(cost);
        drawer.showStrongBox(getStrongBox());
        for(var pick : cost){
            var color = pick.resource();
            for(var i = 0; i < pick.qnt(); i++){
                output.println("The resource that needs handling is:");
                var j = resourcesDestination(color, "\nDo you want to take this from\n0) Warehouse\n1) StrongBox\n>\t", "Wrong option");
                if(j == 0) getLocalGameController().getBoardController().takeFromWarehouse(color);
                else getLocalGameController().getBoardController().takeFromStrongbox(color);
            }
        }
        getLocalGameController().nextPlayer();
    }

    /**
     * method showStatus is a method used to give a player the option to view his current game's status at any given time
     * 1: shows the warehouse
     * 2: shows the strongbox
     * 3: shows his Development Cards
     * 4: shows his Leader Cards
     * 5: shows the faith track
     * 6: back to choosing action
     *
     * @see DrawerCLI#showWarehouse(List)
     * @see DrawerCLI#showStrongBox(Map)
     * @see DrawerCLI#showLibrary(List)
     * @see DrawerCLI#showMyCardLeader(CardLeader[])
     * @see DrawerCLI#showFaithTrackSingle(Checker)
     */
    private synchronized void showStatus(){
        try{
            int s;
            do{
                output.print("""
                             What is your next action?
                             1) Show Warehouse
                             2) Show Strongbox
                             3) Show card on board
                             4) Show your Leader Cards
                             5) Show Faith Track
                             6) Main menu
                             >\040\040""");
                output.flush();
                s = input.nextInt();
                while(s < 1 || s > 6){
                    output.println("Please type a valid number [1-5]");
                    s = input.nextInt();
                }
                switch(s){
                    case 1 -> drawer.showWarehouse(getWarehouse());
                    case 2 -> drawer.showStrongBox(getStrongBox());
                    case 3 -> drawer.showLibrary(getLibrary());
                    case 4 -> drawer.showMyCardLeader(getMyCardLeader());
                    case 5 -> {
                        if(single) drawer.showFaithTrackSingle(checker);
                        else drawer.showFaithTrack(checker, model.getMyBoard().getFaithTrack());
                    }
                    case 6 -> output.println("Ok");
                    default -> output.print("There's no way you were able to that... I think...");
                }
            } while(s != 6);
            nextAction();
        } catch(NumberFormatException e){
            output.println("Not a number");
            showStatus();
        }
    }

    private CardLeader[] getMyCardLeader(){
        if(single) return getLocalGameController().getBoardController().player().getMyLeaderCards();
        else return model.getMyBoard().getMyLeaderCards();
    }

    private List<Pick> getWarehouse(){
        if(single) return getLocalGameController().getBoardController().player().checkWarehouseStatus();
        else return model.getMyBoard().getWarehouse();
    }

    private Map<Colours, Integer> getStrongBox(){
        if(single) return getLocalGameController().getBoardController().player().checkStrongBoxStatus();
        else return model.getMyBoard().getStrongBox();
    }

    private List<ArrayList<CardDev>> getLibrary(){
        if(single) return getLocalGameController().getBoardController().player().checkLibraryStatus();
        else return model.getMyBoard().getLibrary();
    }

    /**
     * method nextAction asks the player what is next action is going to be
     *
     * @see #chooseAction(String)
     */
    private synchronized void nextAction(){
        var game = getLocalGameController();
        var menu = """
                   What is your next action?
                   1) Activate production
                   2) Pick resources from the market
                   3) Buy a Development Card
                   4) Activate Leader Card
                   5) Show status
                   >\040\040""";
        executor.execute(() -> {
            if(single && game.isLorenzoPlaying()) game.moveLorenzo();
            else{
                chooseAction(menu);
            }
        });
    }

    /**
     * method chooseAction is used to record and execute player's choice for his next action
     * 1: Activate Production
     * 2: Pick resources from the market
     * 3: Buy a dev Card
     * 4: Activate a Leader Card
     * 5: Show Status
     *
     * @param menu
     * @see #startProd()
     * @see #marketMove()
     * @see #chooseCardDev()
     * @see #wakeALeader()
     * @see #showStatus()
     */
    private void chooseAction(String menu){
        try{
            output.print(menu);
            output.flush();
            var action = input.nextInt();
            while(action < 0 || action > 6){
                output.println("Please type a valid number [1-5]");
                action = input.nextInt();
            }
            switch(action){
                case 1 -> executor.execute(this::startProd);
                case 2 -> executor.execute(this::marketMove);
                case 3 -> executor.execute(this::chooseCardDev);
                case 4 -> executor.execute(this::wakeALeader);
                case 5 -> executor.execute(this::showStatus);
                default -> output.println("Not Possible");
            }
        } catch(InputMismatchException e){
            output.println("Not even a number\nRetry:\n>\t");
            nextAction();
        }
    }

    /**
     * method startProd is used to activate the production of a player's board
     */
    private synchronized void startProd(){
        var boardController = getLocalGameController().getBoardController();
        var player = boardController.player();
        var library = player.checkLibraryStatus();
        var i = 0;
        for(var librarySlot : library){
            if(librarySlot.isEmpty()){
                output.println("You don't have any development card in slot #" + ++i);
                nextAction();
            }
            else{
                ++i;
                librarySlot = (ArrayList<CardDev>)Checker.castList(CardDev.class, checker.canUseAnyCardDev(librarySlot, getLocalGameController()));
                if(!librarySlot.isEmpty()){
                    var actionableCard = librarySlot.get(librarySlot.size() - 1);
                    output.println("Do you want to activate");
                    output.println(actionableCard);
                    output.println("[y/n]");
                    var r = input.next();
                    if(Objects.equals(r, "y")) activateProdCard(actionableCard);
                    else output.println("You refused");
                }
                else output.println("You don't have the resources to activate the development card in slot #" + ++i);
            }
        }
    }

    /**
     * method activateProdCard is used after startProd has been triggered and activates the production of Development Cards
     *
     * @param card
     */
    private void activateProdCard(CardDev card){
        var boardController = getLocalGameController().getBoardController();
        var resFrom = card.getResFrom();
        for(var pick : resFrom){
            var color = pick.resource();
            var j = resourcesDestination(color, "\nDo you want to take this from\n0) Warehouse\n1) StrongBox\n>\t", "Wrong option");
            if(j == 0) executor.execute(() -> boardController.takeFromWarehouse(color));
            else executor.execute(() -> boardController.takeFromStrongbox(color));
        }
        var player = boardController.player();
        output.println("Production success");
        player.produce(card);
    }

    /**
     * method resourcesHandlerMarket is the method used to handle resources that have been chosen from the market.
     * Resources can be discarded or stored in the warehouse.
     * For every discarded resource other players get an additional Faith point which moves them further on the faith track
     *
     * @param bufferMarket
     * @see #resourcesDestination(Colours, String, String)
     */
    private synchronized void resourcesHandlerMarket(List<Colours> bufferMarket){
        var keep = new ArrayList<Colours>();
        var loose = new ArrayList<Colours>();
        for(Colours colour : bufferMarket){
            if(colour == Colours.WHITE) loose.add(colour);
            else{
                var j = resourcesDestination(colour, "\nDo you want to store it in your personal warehouse?\n0) No\n1)Yes\n>\t", "Command not valid, unknown");
                if(j == 0) loose.add(colour);
                else keep.add(colour);
            }
        }
        getLocalGameController().addFaithPnt(loose);
        output.println("This resources need handling:");
        var colors = new StringBuilder();
        for(Colours colour : keep) colors.append(colour.getType()).append("\t");
        output.println(colors);
        for(Colours colours : keep)
            prepareResForWarehouse(colours, 1);
        drawer.showWarehouse(getWarehouse());
        getLocalGameController().nextPlayer();
    }

    /**
     * method resourcesDestination lets the player decide the destination of a resource
     *
     * @param colour
     * @param s
     * @param s2
     * @return
     */
    private int resourcesDestination(Colours colour, String s, String s2){
        output.print(colour.getType() + s);
        output.flush();
        var j = -1;
        try{
            j = input.nextInt();
            while(j != 0 && j != 1){
                output.println(s2);
                j = input.nextInt();
            }
        } catch(InputMismatchException e){
            resourcesDestination(colour, s, s2);
        }
        return j;
    }

    /**
     * method resourcesHandler is used to handle resources whenever the player needs them.
     * The method asks the player if he wants to take them from the warehouse or the strongbox
     * 1: From Warehouse
     * 2: From Strongbox
     *
     * @param bufferProd
     * @see #prepareResForWarehouse(Colours, int)
     * @see it.polimi.ingsw.server.model.board.Board#storeInStrongBox(Pick[])
     */
    private synchronized void resourcesHandler(List<Pick> bufferProd){
        output.println("Where do you want to pick them from?");
        try{
            for(Pick pick : bufferProd){
                var color = pick.resource();
                output.println(color.getType());
                output.println("0)Warehouse o 1)StrongBox?");
                var j = input.nextInt();
                while(j != 0 && j != 1){
                    output.println("Command not valid, unknown");
                    j = input.nextInt();
                }
                var boardController = getLocalGameController().getBoardController();
                var player = boardController.player();
                var resourceTaken = pick.resource();
                var qntTaken = -pick.qnt();
                switch(j){
                    case 0 -> prepareResForWarehouse(resourceTaken, qntTaken);
                    case 1 -> player.storeInStrongBox(new Pick[]{new Pick(resourceTaken, qntTaken)});
                    default -> output.println("Not possible");
                }
            }
        } catch(NumberFormatException e){
            resourcesHandler(bufferProd);
        }
        drawer.showWarehouse(bufferProd);
        getLocalGameController().nextPlayer();
    }

    @Override
    public void setSingle(boolean single){this.single = single;}

    @Override
    public void setClient(Client client){this.client = client;}

    @Override
    public String getNickname(){
        return nickname;
    }

    @Override
    public void setNickname(String name){
        nickname = name;
    }

    /**
     * method reChooseNickname is used in case a player chooses a nickname already taken
     *
     * @param id
     */
    public void reChooseNickname(int id){
        output.println("This nickname is already taken\nThis player is playing a game with id: " + id);
        chooseNickname();
    }

    /**
     * method clientSetup is used to ask for IP and Port of the server
     */
    @Override
    public void clientSetup(){
        client.setIP(askIP());
        client.setPort(askPort());
    }

    @Override
    public void showError(String message){
        output.println(ConsoleColors.RED + "Fatal Error: " + message + ConsoleColors.RESET);
    }

    /**
     * method chooseNickname is used to record a player's nickname
     */
    public void chooseNickname(){
        output.print(">\tChoose a nickname\n>\t");
        output.flush();
        var name = input.next();
        output.print("Your nickname will be:\t" + ConsoleColors.BLUE_BACKGROUND_BRIGHT + " " + name + " " + ConsoleColors.RESET + "\nDo you confirm? [y/n]\n>\t");
        output.flush();
        var s = input.next();
        if(s.charAt(0) == 'y') client.send("checkNickname::" + name);
        else{
            output.println("Ok, try again\n>\t");
            chooseNickname();
        }
    }

    /**
     * method askNPlayers asks a player how many players are going to play the game
     */
    @Override
    public void askNPlayers(){
        output.print(">\tInsert the the number of players you want for this match [2-4]\n>\t");
        output.flush();
        var players = input.nextInt();
        while(players <= 1 || players >= 5){
            System.err.println("We can not support that number of players");
            players = input.nextInt();
        }
        client.setNPlayers(players);
        client.send("NPlayers::" + players);
    }

    @Override
    public void printMessage(String message){
        output.println(message);
    }

    /**
     * method askId asks the player if he already has a game to join by requesting the game ID previously provided
     * by another player who created the match.
     * In case the player does not have a game ID and wants to create a new match, an ID will be provided to him.
     */
    @Override
    public void askId(){
        var id = 0;
        output.print("Do you already have the id of a match you can join? [y/n]\n>\t");
        output.flush();
        var r = input.next();
        if(r.charAt(0) == 'y'){
            output.print(">\tIf you already have the id of a game you can join please indicate it below:\n>\t");
            output.flush();
            id = input.nextInt();
            output.println(">\tYou are about to join the game with id:\n>\t" + ConsoleColors.BLUE_BACKGROUND_BRIGHT + id + ConsoleColors.RESET);
        }
        else output.println("You will be assigned one as soon as the game is ready");
        client.send("id::" + id);
    }

    @Override
    public void propertyChangeMulti(PropertyChangeEvent evt){
        executor.execute(() -> {
            switch(evt.getPropertyName()){
                case "temporaryLeaderCards" -> {
                    try{
                        var initialLeaders = Json.fromJson((JsonNode)evt.getNewValue(), CardLeader[].class);
                        chooseInitialLeaders(initialLeaders);
                    } catch(JsonProcessingException e){
                        e.printStackTrace();
                    }
                }
                case "firstOp" -> client.send("initialResource::");
                case "nextPlayer" -> client.send("nextPlayer::");
                case "redCrossMove" -> getLocalGameController().getBoardController().checkVaticanReport();
                case "nextAction" -> nextAction();
                case "preference" -> chooseRes();
                case "marketBuffer" -> {
                    var current = new LinkedList<Colours>();
                    try{
                        var list = Json.fromJson((JsonNode)evt.getNewValue(), Colours[].class);
                        Collections.addAll(current, list);
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                    resourcesHandlerMarket(current);
                }
                case "productionBuffer" -> resourcesHandler(Checker.castList(Pick.class, (Collection<?>)evt.getNewValue()));
                case "costCardDev" -> payForCardDev(Checker.castList(Pick.class, (Collection<?>)evt.getNewValue()));
                case "gameID" -> output.println("Your Game ID: " + evt.getNewValue() + "\nTell the other players");
                case "wrong" -> output.println("There's something wrong" + evt.getNewValue());
                default -> output.println(evt.getPropertyName() + " is not handled (CLI)");
            }
        });
    }

    @Override
    public void setModelView(ModelView model){
        this.model = model;
    }

    @Override
    public void resilience(int id, String name){
        output.println("This nickname is already taken\nThis player was playing a game with id: " + id);
        output.println("Do you want to re-join the game?");
        output.println("[y/n]");
        var r = input.next();
        if(Objects.equals(r, "y")) client.send("join::" + id + "::" + name);
        else{
            output.println("You refused");
            reChooseNickname(id);
        }
    }

    /**
     * method askIP asks the player for the server's IP address
     *
     * @return
     */
    public String askIP(){
        var ip = "";
        output.print(">\tInsert the server IP address\n>\t");
        output.flush();
        ip = input.next();
        while(!(InetAddressValidator.getInstance().isValid(ip))){
            output.println("Server IP address is not valid");
            ip = input.next();
        }
        return ip;
    }

    /**
     * method askPort asks the player for the Server's port
     *
     * @return
     */
    private int askPort(){
        output.print(">\tInsert the server port\n>\t");
        output.flush();
        var port = input.nextInt();
        while(port < 1023 || port > 65535){
            System.err.println("Server port is not valid");
            port = Integer.parseInt(input.nextLine());
        }
        return port;
    }

    private void chooseRes(){
        output.println("Select a preferred resource to add to you belongings:");
        output.println("1)\t" + Colours.BLUE.getType());
        output.println("2)\t" + Colours.GREY.getType());
        output.println("3)\t" + Colours.PURPLE.getType());
        output.println("4)\t" + Colours.YELLOW.getType());
        var res = input.nextInt();
        while(res < 0 || res >= 5){
            output.println("Not valid");
            res = input.nextInt();
        }
        var choice = switch(res){
            case 1 -> Colours.BLUE;
            case 2 -> Colours.GREY;
            case 3 -> Colours.PURPLE;
            case 4 -> Colours.YELLOW;
            default -> null;
        };
        prepareResForWarehouse(choice, 1);
    }

    /**
     * method chooseInitialLeaders is used to record the player's choice at the start of the game for his Leader Cards
     *
     * @param cards of type Array of CardLeader
     */
    private synchronized void chooseInitialLeaders(CardLeader[] cards){
        var list = new ArrayList<CardLeader>();
        var finale = new CardLeader[2];
        Collections.addAll(list, cards);
        var i = 0;
        while(i < 2){
            for(CardLeader card : list) drawer.showCard(card);
            var card = (CardLeader)pickCard(CardLeader.class);
            if(list.contains(card)){
                list.remove(card);
                finale[i] = card;
                i++;
            }
            else output.println("Something went wrong");
        }
        if(single){
            var game = getLocalGameController();
            var boardController = game.getBoardController();
            var player = boardController.player();
            player.setMyLeaderCards(finale);
        }
        else{
            var node = Json.toJson(finale);
            var string = "";
            try{
                string = Json.stringify(node);
            } catch(JsonProcessingException e){
                e.printStackTrace();
            }
            client.send("initialLeaders::" + string);
        }
    }

    /**
     * method pickCard lets the player pick his Leader Card using a two digit code
     *
     * @param clazz
     * @return
     */
    private Card pickCard(Class<? extends Card> clazz){
        Card card = null;
        try{
            output.println("Code? (last digits)");
            var code = input.next();
            if(clazz == CardLeader.class) card = checker.getCorrectLeaderCardCode(code);
            if(clazz == CardDev.class) card = checker.getCorrectDevCardCode(code.toUpperCase());
        } catch(Exception e){
            e.printStackTrace();
        }
        return card;
    }

    private LocalGameController getLocalGameController(){
        return LocalGameController.getInstance();
    }

    private Colours[][] getMarketTray(){
        if(single) return getLocalGameController().getGame().getMarket().getTray();
        else return model.getMarket();
    }

    private Colours getExternalMarble(){
        if(single) return getLocalGameController().getGame().getMarket().getExternal();
        else return model.getExternalMarble();
    }

    /**
     * method marketMove is the one responsible for:
     * -handling the resources on a row or a column to the player based on his choice.
     * -moving the market as a consequence of the choice
     */
    private synchronized void marketMove(){
        var move = -1;
        Colours[][] marketTray;
        Colours externalMarble;
        marketTray = getMarketTray();
        externalMarble = getExternalMarble();
        drawer.showMarket(marketTray, externalMarble);
        output.println("\n0 ) Row\n1 ) Column");
        try{
            var i = input.nextInt();
            if(!(i == 0 || i == 1)) marketMove();
            if(i == 0) move = getRow();
            if(i == 1) move = getColumn();
            if(move == -1){
                output.println("Impossible move");
                nextAction();
            }
            else{
                if(single) getLocalGameController().marketAction(move);
                else client.send("marketAction::" + move);
            }
        } catch(NumberFormatException e){
            marketMove();
        }
    }

    private int getColumn(){
        output.println("Which one? [1-4]");
        var j = input.nextInt();
        while(!(j > -1 && j < 5)){
            output.println("This is not a valid column");
            j = input.nextInt();
        }
        return switch(j){
            case 1 -> 6;
            case 2 -> 5;
            case 3 -> 4;
            case 4 -> 3;
            default -> -1;
        };
    }

    private int getRow(){
        output.println("Which one? [1-3]");
        var j = input.nextInt();
        while(!(j > -1 && j < 4)){
            output.println("This is not a valid row");
            j = input.nextInt();
        }
        return switch(j){
            case 1 -> 0;
            case 2 -> 1;
            case 3 -> 2;
            default -> -1;
        };
    }


    /**
     * method chooseCardDev is the method used whenever a player wants to buy a Development Card with his resources.
     * Whenever the player cannot buy any Dev Card, this method will warn him.
     *
     * @see Checker#canBuyAnyCardDev(List, LocalGameController)
     */
    private synchronized void chooseCardDev(){
        var game = getLocalGameController().getGame();
        var actual = checker.canBuyAnyCardDev(game.getCardDevDeck(), getLocalGameController());
        if(actual.isEmpty()){
            output.println("You can not buy any card");
            nextAction();
        }
        else{
            for(var card : actual)
                drawer.showCard(card);
            var card = (CardDev)pickCard(CardDev.class);
            if(actual.contains(card)){
                try{
                    var slot = pickSlot(card);
                    if(slot >= 0) getLocalGameController().cardDevAction(card, slot);
                    else{
                        output.println("You don't have a slot for this card");
                        nextAction();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
            else{
                output.println("This card is not available");
                nextAction();
            }
        }
    }

    /**
     * method pickSlot is used to ask the player where he wants to store his most recently bought Dev Card in the library.
     *
     * @param card
     * @return
     */
    private int pickSlot(CardDev card){
        drawer.showLibrary(getLibrary());
        output.print("Slot for " + card.name() + "? [1-3]\n>\t");
        output.flush();
        var slot = -1;
        try{
            slot = input.nextInt();
            while(!(slot > -1 && slot < 4)){
                output.println("This is not a valid slot");
                slot = input.nextInt();
            }
        } catch(NumberFormatException e){
            output.println("This slot doesn't exist\nTry again...");
            pickSlot(card);
        }
        if(!checker.isAddable(getLocalGameController(), card, slot)) slot = -1;
        return slot - 1;
    }

    /**
     * method wakeALeader is the method used to activate a Leader's power
     *
     * @see Checker#canUseAnyCardLeader(CardLeader[], LocalGameController)
     */
    private synchronized void wakeALeader(){
        var boardController = getLocalGameController().getBoardController();
        var player = boardController.player();
        var actual = checker.canUseAnyCardLeader(player.getMyLeaderCards(), getLocalGameController());
        if(actual.isEmpty()){
            output.println("You can not activate any leader at the moment");
        }
        else{
            for(var card : actual)
                drawer.showCard(card);
            var card = (CardLeader)pickCard(CardLeader.class);
            if(actual.contains(card)) executor.execute(() -> getLocalGameController().cardLeadAction(card));
            else{
                output.println("You don't have this Leader");
            }
        }
        nextAction();
    }

    private void prepareResForWarehouse(Colours colour, int qnt){
        if(single) prepareResForWarehouseS(colour, qnt);
        else prepareResForWarehouseM(colour, qnt);
    }

    private void prepareResForWarehouseM(Colours colour, int qnt){
        if(colour == Colours.RED) client.send("moveFT::" + 1);
        else{
            drawer.showWarehouse(getWarehouse());
            output.print("Please choose the shelf for this resource? [1-3]\n" + colour.getType() + "\n>\t");
            output.flush();
            var lvl = getWarehouseShelfLvl();
            client.send("warehouseResHandler::" + colour + "::" + qnt + "::" + lvl);
        }
    }

    /**
     * method prepareResForWarehouse is the method used to place resources in the warehouse.
     * The same method is used whenever a resource is taken from the warehouse by subtracting the value instead of adding it
     *
     * @param colour
     * @param qnt
     */
    private void prepareResForWarehouseS(Colours colour, int qnt){
        var newPick = new Pick(colour, qnt);
        var response = 0;
        var bc = getLocalGameController().getBoardController();
        if(colour == Colours.RED) bc.moveFT(1);
        else{
            drawer.showWarehouse(getWarehouse());
            output.print("Shelf for this resource? [1-3]\n" + colour.getType() + "\n>\t");
            output.flush();
            var lvl = getWarehouseShelfLvl();
            var player = bc.player();
            while(!player.checkWarehouseAdd(newPick, lvl)){
                response = getResponse();
                lvl = actOnResponse(colour, response, lvl, getLocalGameController());
                if(response == 1) break;
            }
            if(response != 1) bc.warehouseResHandler(colour, qnt, lvl);
        }
    }

    /**
     * method actOnResponse is the method used to handle the warehouse whenever a resource cannot be added and the player wants to rearrange the warehouse.
     * The method asks for the starting shelf and for the destination of a resource so it can move it.
     *
     * @param colour
     * @param response
     * @param lvl
     * @param gameController
     * @return
     */
    private int actOnResponse(Colours colour, int response, int lvl, LocalGameController gameController){
        var boardController = gameController.getBoardController();
        switch(response){
            case 1 -> getLocalGameController().addFaithPnt(Collections.singletonList(colour));
            case 2 -> {
                var player = boardController.player();
                var content = player.checkWarehouseStatus();
                output.println("Starting shelf?");
                var first = getWarehouseShelfLvl();
                var firstQnt = content.get(first).qnt();
                output.println("Destination?");
                var second = getWarehouseShelfLvl();
                var secondQnt = content.get(second).qnt();
                if(first + 1 >= secondQnt && second + 1 >= firstQnt) boardController.rearrangeWarehouse(first, second);
                else output.println("You can't do that");
                drawer.showWarehouse(content);
            }
            case 3 -> {
                drawer.showWarehouse(getWarehouse());
                output.print("Shelf for this resource? [1-3]\n" + colour.getType() + "\n>\t");
                output.flush();
                lvl = getWarehouseShelfLvl();
            }
            case 4 -> nextAction();
            default -> output.println("Not possible");
        }
        return lvl;
    }

    /**
     * method getResponse asks what the player wants to do when he tries to store a resource in the warehouse
     * but this resource can not be added.
     * 1: discards the resource and gives additional faith track points to other players
     * 2: rearrange the warehouse by using actOnResponse
     * 3: lets the player choose another shelf
     * 4: goes back
     *
     * @return
     */
    private int getResponse(){
        int response;
        output.println("You can not add that resource on this shelf, try something else...");
        output.print("""
                     Choose how to proceed?
                     1) Discard the resource
                     2) Rearrange the warehouse
                     3) Put on an other shelf
                     4) Previous menu
                     >\040\040""");
        output.flush();
        response = input.nextInt();
        while(response < 1 || response > 4){
            output.println("Not a valid command\n>\t");
            response = input.nextInt();
        }
        return response;
    }

    private int getWarehouseShelfLvl(){
        var lvl = input.nextInt();
        while(!(lvl > 0 && lvl < 4)){
            output.println("This is not a valid shelf");
            lvl = input.nextInt();
        }
        return lvl - 1;
    }

}
