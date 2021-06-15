package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import it.polimi.ingsw.clients.viewer.utils.Utils;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.Requirements;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller.Controller;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.beans.PropertyChangeEvent;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*CONTROLLA CHE SIA TUTTO SCRITTO IN INGLESE*/

public class CLI implements Runnable, UI{

    private final Scanner input;
    private final PrintWriter output;
    private final ExecutorService executor;
    private String nickname;
    private boolean single;
    private Client client;

    public CLI(){
        input = new Scanner(System.in);
        output = new PrintWriter(System.out, true);
        executor = new ThreadPoolExecutor(0, 8, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(true));
    }

    public void run(){
        chooseNickname();
        client.asyncSend("nickname::" + nickname);
        client.asyncSend("nplayers::" + client.getNPlayers());
    }

    private LocalGameController getGameController(){
        return getGameController();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        executor.execute(() -> {
            switch(evt.getPropertyName()){
                case "temporaryLeaderCards" -> chooseInitialLeaders((CardLeader[])evt.getNewValue());
                case "firstOp" -> {
                    if(single) getGameController().nextPlayer();
                    else Controller.getInstance().getGameControllerById(0).initialResourcesHandler();
                }
                case "nextPlayer" -> {
                    if(single) getGameController().nextPlayer();
                    else Controller.getInstance().getGameControllerById(0);
                }
                case "wrong" -> output.println("There's something wrong" + evt.getNewValue());
                case "redCrossMove" -> getGameController().getBoardController().checkVaticanReport();
                case "nextAction" -> nextAction();
                case "marketBuffer", "bufferProd" -> resourcesHandlerMarket(Utils.castList(Colours.class, (Collection<?>)evt.getNewValue()));
                case "productionBuffer" -> resourcesHandler(Utils.castList(Pick.class, (Collection<?>)evt.getNewValue()));
                case "costCardDev" -> payForCardDev(Utils.castList(Pick.class, (Collection<?>)evt.getNewValue()));

                case "gameID" -> output.println("Game ID: " + evt.getNewValue() + "\nWait for the other players");
                default -> output.println(evt.getPropertyName() + " is not handled (CLI)");
            }
        });
    }

    private synchronized void payForCardDev(List<Pick> cost){
        showWarehouse();
        showStrongBox();
        for(var pick : cost){
            var color = pick.getResource();
            for(var i = 0; i < pick.getQnt(); i++){
                output.println("The resource that needs handling is:");
                var j = resourcesDestination(color, "\nDo you want to take this from\n0) Warehouse\n1) StrongBox\n>\t", "Wrong option");
                if(j == 0) getGameController().getBoardController().takeFromWarehouse(color);
                else getGameController().getBoardController().takeFromStrongbox(color);
            }
        }
        getGameController().nextPlayer();
    }

    private synchronized void chooseInitialLeaders(CardLeader[] cards){
        var list = new ArrayList<CardLeader>();
        var finale = new CardLeader[2];
        Collections.addAll(list, cards);
        var i = 0;
        while(i < 2){
            for(CardLeader card : list) showCard(card);
            var card = (CardLeader)pickCard(CardLeader.class);
            if(list.contains(card)){
                list.remove(card);
                finale[i] = card;
                i++;
            }
            else output.println("Something went wrong");
        }
        if(single) getGameController().getBoardController().getPlayer().setMyLeaderCards(finale);
    }

    private synchronized void showStatus(){
        try{
            int s;
            do{
                output.print("""
                             What is your next action?
                             1) Show Warehouse
                             2) Show Strongbox
                             3) Show card on board
                             4) Show Faith Track
                             5) Main menu
                             >\040\040""");
                output.flush();
                s = input.nextInt();
                while(s < 1 || s > 6){
                    output.println("Please type a valid number [1-5]");
                    s = input.nextInt();
                }
                switch(s){
                    case 1 -> showWarehouse();
                    case 2 -> showStrongBox();
                    case 3 -> showLibrary();
                    case 4 -> {
                        if(single) showFaithTrackSingle();
                        else showFaithTrack();
                    }
                    case 5 -> output.println("Ok");
                    default -> output.print("There's no way you were able to that... I think...");
                }
            } while(s != 5);
            nextAction();
        } catch(NumberFormatException e){
            output.println("Not a number");
            showStatus();
        }
    }

    private void showFaithTrack(){
        final var pos = 0;
        var normal = "███";
        var central = ConsoleColors.RED_BOLD + "███" + ConsoleColors.RESET;
        var top = "█" + ConsoleColors.RED_BOLD + "█" + ConsoleColors.RESET + "█";
        var trackTop = new StringBuilder();
        var trackCen = new StringBuilder();
        var trackN = new StringBuilder();
        for(var i = 0; i < 25; i++){
            drawFTBoxNumber(trackN, i);
            if(isPopeFavor(i)){
                drawFTBox(trackTop, ConsoleColors.YELLOW_BOLD_BRIGHT, trackCen);
            }
            if(i == pos){drawFTBox(trackTop.append(top), "\t", trackCen.append(central));}
            else{
                drawFTBox(trackTop.append(normal), "\t", trackCen.append(normal));
            }
            drawFTBox(trackTop, ConsoleColors.RESET, trackCen);
        }
        output.println(trackN);
        output.println(trackTop);
        output.println(trackCen);
        output.println(trackTop);
    }

    private void drawFTBox(StringBuilder append, String s, StringBuilder append2){
        append.append(s);
        append2.append(s);
    }

    private void showFaithTrackSingle(){
        final var pos = getGameController().getBoardController().getPlayer().checkFT();
        final var posL = getGameController().getBoardController().getLorenzo().checkFT();
        var normal = "███";
        var central = ConsoleColors.RED_BOLD + "███" + ConsoleColors.RESET;
        var top = "█" + ConsoleColors.RED_BOLD + "█" + ConsoleColors.RESET + "█";
        var centralL = ConsoleColors.BLACK_BOLD + "XXX" + ConsoleColors.RESET;
        var topL = "█" + ConsoleColors.BLACK_BOLD + "X" + ConsoleColors.RESET + "█";
        var trackTop = new StringBuilder();
        var trackCen = new StringBuilder();
        var trackN = new StringBuilder();
        for(var i = 0; i < 25; i++){
            drawFTBoxNumber(trackN, i);
            if(isPopeFavor(i)){
                drawFTBox(trackTop, ConsoleColors.YELLOW_BOLD_BRIGHT, trackCen);
            }
            if(i == pos){
                if(pos == posL) drawYouAndLorenzoBoxFT(trackTop, trackCen, i);
                else{
                    drawFTBox(trackTop.append(top), "\t", trackCen.append(central));
                }
            }
            else if(i == posL) drawYourBoxFTSingle(centralL, topL, trackTop, trackCen, i);
            else{
                drawFTBox(trackTop.append(normal), "\t", trackCen.append(normal));
            }
            if(isPopeFavor(i)){
                drawFTBox(trackTop, ConsoleColors.RESET, trackCen);
            }
        }
        output.println(trackN);
        output.println(trackTop);
        output.println(trackCen);
        output.println(trackTop);
    }

    private void drawYourBoxFTSingle(String centralL, String topL, StringBuilder trackTop, StringBuilder trackCen, int i){
        if(isPopeFavor(i)){
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.BLACK_BOLD, ConsoleColors.YELLOW_BOLD_BRIGHT);
        }
        else{
            trackTop.append(topL).append("\t");
        }
        trackCen.append(centralL).append("\t");
    }

    private void drawYouAndLorenzoBoxFT(StringBuilder trackTop, StringBuilder trackCen, int i){
        if(isPopeFavor(i)){
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.RED_BOLD, ConsoleColors.YELLOW_BOLD_BRIGHT);
        }
        else{
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.RED_BOLD, ConsoleColors.RESET);
        }
        trackCen.append(ConsoleColors.RED_BOLD + "XXX" + ConsoleColors.RESET).append("\t");
    }

    private void drawSpecialBoxFTSingle(StringBuilder trackTop, String redBold, String yellowBoldBright){
        trackTop.append("█").append(redBold).append("X").append(yellowBoldBright).append("█").append("\t");
    }

    private void drawFTBoxNumber(StringBuilder trackN, int i){
        if(i < 9) trackN.append("██").append(i).append("\t");
        else trackN.append("█").append(i).append("\t");
    }

    private boolean isPopeFavor(int i){
        return i == 5 || i == 6 || i == 7 || i == 8 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || i == 19 || i == 20 || i == 21 || i == 22 || i == 23 || i == 24;
    }

    private synchronized void nextAction(){
        var menu = """
                   What is your next action?
                   1) Activate production
                   2) Pick resources from the market
                   3) Buy a Development Card
                   4) Activate Leader Card
                   5) Show status
                   >\040\040""";
        executor.execute(() -> {
            if(single && getGameController().isLorenzoPlaying()) getGameController().moveLorenzo();
            else{
                chooseAction(menu);
            }
        });
    }

    private void chooseAction(String menu){
        try{
            output.print(menu);
            output.flush();
            var action = input.nextInt();
            while(action < 1 || action > 6){
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
            output.println("Not even a number");
            nextAction();
        }
    }

    private synchronized void startProd(){
        var boardController = getGameController().getBoardController();
        var player = boardController.getPlayer();
        var library = player.checkLibraryStatus();
        for(var librarySlot : library){
            if(librarySlot.isEmpty()){
                output.println("You don't have any development card");
                nextAction();
            }
            else{
                librarySlot = (ArrayList<CardDev>)Utils.castList(CardDev.class, canUseAnyCardDev(librarySlot));
                if(!librarySlot.isEmpty()){
                    var activableCard = librarySlot.get(librarySlot.size() - 1);
                    output.println("Do you want to activate");
                    output.println(activableCard);
                    output.println("[y/n]");
                    var r = input.next();
                    if(Objects.equals(r, "y")) activateProdCard(activableCard);
                    else output.println("You refused");
                }
                else output.println("You don't have the resources to activate any development card");
            }
        }
    }

    private synchronized void activateProdCard(CardDev card){
        var boardController = getGameController().getBoardController();
        var resFrom = card.getResFrom();
        for(var pick : resFrom){
            var color = pick.getResource();
            var j = resourcesDestination(color, "\nDo you want to take this from\n0) Warehouse\n1)StrongBox\n>\t", "Wrong option");
            if(j == 0) boardController.takeFromWarehouse(color);
            else boardController.takeFromStrongbox(color);
        }
        var player = boardController.getPlayer();
        player.produce(card);
    }

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
        getGameController().addFaithPnt(loose);
        output.println("This resources need handling:");
        var colors = new StringBuilder();
        for(Colours colour : keep) colors.append(colour.getType()).append("\t");
        output.println(colors);
        for(Colours colours : keep)
            prepareResForWarehouse(colours, 1);
        showWarehouse();
        getGameController().nextPlayer();
    }

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

    private synchronized void resourcesHandler(List<Pick> bufferProd){
        output.println("Where do you want to pick them from?");
        try{
            for(Pick pick : bufferProd){
                var color = pick.getResource();
                output.println(color.getType());
                output.println("0)Warehouse o 0)StrongBox?");
                var j = input.nextInt();
                while(j != 0 && j != 1){
                    output.println("Command not valid, unknown");
                    j = input.nextInt();
                }
                var boardController = getGameController().getBoardController();
                var player = boardController.getPlayer();
                var resourceTaken = pick.getResource();
                var qntTaken = -pick.getQnt();
                switch(j){
                    case 0 -> prepareResForWarehouse(resourceTaken, qntTaken);
                    case 1 -> player.storeSB(new Pick[]{new Pick(resourceTaken, qntTaken)});
                    default -> output.println("Not possible");
                }
            }
        } catch(NumberFormatException e){
            resourcesHandler(bufferProd);
        }
        showWarehouse();
        getGameController().nextPlayer();
    }

    private void showCard(Card card){
        printLine();
        output.println(card.toString());
        printLine();
    }

    private void printLine(){
        output.println("_________________________________________________");
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

    public void reChooseNickname(){
        output.println("This nickname is already taken");
        chooseNickname();
    }

    @Override
    public void clientSetup(){
        client.setIP(askIP());
        client.setPort(askPort());
    }

    @Override
    public void showError(String message){
        output.println("Fatal Error: " + message);
    }

    public void chooseNickname(){
        output.print(">\tChoose a nickname\n>\t");
        output.flush();
        var name = input.next();
        output.print("Your nickname will be:\t" + name + "\nDo you confirm? [y/n]\n>\t");
        output.flush();
        var s = input.next();
        if(s.charAt(0) == 'y') client.asyncSend("checkNickname::" + name);
        else{
            output.println("Ok, try again");
            chooseNickname();
        }
    }

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

    private int askNPlayers(){
        output.print(">\tInsert the the number of players you want for this match [2-4]\n>\t");
        output.flush();
        var players = input.nextInt();
        while(players <= 1 || players >= 5){
            System.err.println("We can not support that number of players");
            players = input.nextInt();
        }
        return players;
    }

    private void showMarket(){
        printLine();
        var marketTray = getGameController().getGame().getMarket().getTray();
        var externalMarble = getGameController().getGame().getMarket().getExternal();
        var print = new StringBuilder();
        print.append(ConsoleColors.BLACK + "   \t   \t   \t" + ConsoleColors.RESET).append(drawMarket(externalMarble)).append("\n");
        for(var i = 0; i < Constants.XMARKET.getVal(); i++){
            for(var j = 0; j < Constants.YMARKET.getVal(); j++){
                print.append(drawMarket(marketTray[i][j])).append("\t");
            }
            print.append("\n");
        }
        output.print(print);
        output.flush();
        printLine();
    }

    private Card pickCard(Class<? extends Card> clazz){
        Card card = null;
        try{
            output.println("Code? (last digits)");
            var code = input.next();
            if(clazz == CardLeader.class) card = getCorrectLeaderCardCode(code);
            if(clazz == CardDev.class) card = getCorrectDevCardCode(code.toUpperCase());
        } catch(Exception e){
            e.printStackTrace();
        }
        return card;
    }

    private synchronized void marketMove(){
        var move = -1;
        showMarket();
        output.println("\n0 ) Row\n1 ) Column");
        var j = -2;
        try{
            var i = input.nextInt();
            if(!(i == 0 || i == 1)) marketMove();
            if(i == 0){
                output.println("Which one? [1-3]");
                j = input.nextInt();
                while(!(j > -1 && j < 4)){
                    output.println("This is not a valid row");
                    j = input.nextInt();
                }
                move = switch(j){
                    case 1 -> 0;
                    case 2 -> 1;
                    case 3 -> 2;
                    default -> -1;
                };
            }
            if(i == 1){
                output.println("Which one? [1-4]");
                j = input.nextInt();
                while(!(j > -1 && j < 5)){
                    output.println("This is not a valid column");
                    j = input.nextInt();
                }
                move = switch(j){
                    case 1 -> 6;
                    case 2 -> 5;
                    case 3 -> 4;
                    case 4 -> 3;
                    default -> -1;
                };
            }
            if(move == -1){
                output.println("Impossible move");
                nextAction();
            }
            else getGameController().marketAction(move);
        } catch(NumberFormatException e){
            marketMove();
        }
    }

    private synchronized void chooseCardDev(){
        var game = getGameController().getGame();
        var actual = canBuyAnyCardDev(game.getCardDevDeck());
        if(actual.isEmpty()){
            output.println("You can not buy any card");
            nextAction();
        }
        else{
            for(var card : actual)
                showCard(card);
            var card = (CardDev)pickCard(CardDev.class);
            if(actual.contains(card)){
                try{
                    var slot = pickSlot(card);
                    if(slot >= 0) getGameController().cardDevAction(card, slot);
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

    private int pickSlot(CardDev card){
        showLibrary();
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
        if(!isAddable(card, slot)) slot = -1;
        return slot - 1;
    }

    private boolean isAddable(CardDev card, int slot){
        var slotCards = getGameController().getBoardController().getPlayer().checkLibraryStatus().get(slot - 1);
        if(slotCards.isEmpty()) return card.getLvl() == 1;
        else return switch(card.getLvl()){
            case 2 -> slotCards.get(slotCards.size() - 1).getLvl() != 1;
            case 3 -> slotCards.get(slotCards.size() - 1).getLvl() != 2;
            default -> false;
        };
    }

    private List<Card> canBuyAnyCardDev(List<CardDev> cards){
        var boardController = getGameController().getBoardController();
        var player = boardController.getPlayer();
        var resAll = player.getResAll();
        var list = new LinkedList<Card>();
        for(var card : cards){
            var i = 0;
            for(var cost : card.getCost()){
                var costColor = cost.getResource();
                var costQnt = cost.getQnt();
                var availability = resAll.get(costColor);
                if(costQnt <= availability) i++;
            }
            if(i == card.getCost().length) list.add(card);
        }
        return list;
    }

    private List<Card> canUseAnyCardDev(List<CardDev> cards){
        var boardController = getGameController().getBoardController();
        var player = boardController.getPlayer();
        var resAll = player.getResAll();
        var list = new LinkedList<Card>();
        for(var card : cards){
            var i = 0;
            for(var cost : card.getResFrom())
                if(cost.getQnt() <= resAll.get(cost.getResource())) i++;
            if(i == card.getResFrom().length) list.add(card);
        }
        return list;
    }

    private List<Card> canUseAnyCardLeader(CardLeader[] cards){
        var boardController = getGameController().getBoardController();
        var player = boardController.getPlayer();
        var resAll = player.getResAll();
        var slots = player.checkLibraryStatus();
        var list = new LinkedList<Card>();
        for(var card : cards)
            for(var req : card.getReq()){
                if(req.getClass() == Requirements.RequirementsResources.class)
                    checkOnRequiredRes(resAll, list, card, (Requirements.RequirementsResources)req);
                if(req.getClass() == Requirements.RequirementsCard.class)
                    checkOnRequiredCard(slots, list, card, (Requirements.RequirementsCard)req);
            }
        return list;
    }

    private void checkOnRequiredCard(List<ArrayList<CardDev>> slots, LinkedList<Card> list, CardLeader card, Requirements.RequirementsCard req){
        var colorRequired = req.getColour();
        var lvlRequired = req.getCardLvl();
        for(var slot : slots){
            for(var thisCard : slot){
                if(thisCard != null){
                    var colorCard = thisCard.getColour();
                    var lvlCard = thisCard.getLvl();
                    if(colorCard == colorRequired && lvlCard <= lvlRequired){
                        list.add(card);
                        break;
                    }
                }
            }

            if(list.contains(card)) break;
        }
    }

    private void checkOnRequiredRes(Map<Colours, Integer> resAll, LinkedList<Card> list, CardLeader card, Requirements.RequirementsResources req){
        var colorRequired = req.getColour();
        var qntRequired = req.getQnt();
        var qntAvailable = (int)resAll.get(colorRequired);
        if(qntRequired <= qntAvailable) list.add(card);
    }

    private synchronized void wakeALeader(){
        var boardController = getGameController().getBoardController();
        var player = boardController.getPlayer();
        var actual = canUseAnyCardLeader(player.getMyLeaderCards());
        if(actual.isEmpty()){
            output.println("You can not activate any leader at the moment");
        }
        else{
            for(var card : actual)
                showCard(card);
            var card = (CardLeader)pickCard(CardLeader.class);
            if(actual.contains(card)) executor.execute(() -> getGameController().cardLeadAction(card));
            else{
                output.println("You don't have this Leader");
            }
        }
        nextAction();
    }

    private void prepareResForWarehouse(Colours colour, int qnt){
        var newPick = new Pick(colour, qnt);
        var response = 0;
        var bc = getGameController().getBoardController();
        if(colour == Colours.RED) bc.moveFT(1);
        else{
            showWarehouse();
            output.print("Shelf for this resource? [1-3]\n" + colour.getType() + "\n>\t");
            output.flush();
            var lvl = getWarehouseShelfLvl();
            var player = bc.getPlayer();
            while(!player.checkWarehouseAdd(newPick, lvl)){
                response = getResponse();
                lvl = actOnResponse(colour, response, lvl, getGameController());
                if(response == 1) break;
            }
            if(response != 1) bc.warehouseResHandler(colour, qnt, lvl);
        }
    }

    private int actOnResponse(Colours colour, int response, int lvl, LocalGameController gameController){
        var boardController = gameController.getBoardController();
        switch(response){
            case 1 -> getGameController().addFaithPnt(Collections.singletonList(colour));
            case 2 -> {
                var player = boardController.getPlayer();
                var content = player.checkWarehouseStatus();
                output.println("Starting shelf?");
                var first = getWarehouseShelfLvl();
                var firstQnt = content.get(first).getQnt();
                output.println("Destination?");
                var second = getWarehouseShelfLvl();
                var secondQnt = content.get(second).getQnt();
                if(first + 1 >= secondQnt && second + 1 >= firstQnt) boardController.rearrangeWarehouse(first, second);
                else output.println("You can't do that");
                showWarehouse();
            }
            case 3 -> {
                showWarehouse();
                output.print("Shelf for this resource? [1-3]\n" + colour.getType() + "\n>\t");
                output.flush();
                lvl = getWarehouseShelfLvl();
            }
            default -> output.println("Not possible");
        }
        return lvl;
    }

    private int getResponse(){
        int response;
        output.println("You can not add that resource on this shelf, try something else...");
        output.print("""
                     Choose how to proceed?
                     1) Discard the resource
                     2) Rearrange the warehouse
                     3) Put on an other shelf
                     >\040\040""");
        output.flush();
        response = input.nextInt();
        while(response < 1 || response > 3){
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

    private void showWarehouse(){
        printLine();
        output.println("At the moment the situation with the warehouse is this:");
        var boardController = getGameController().getBoardController();
        var content = boardController.getPlayer().checkWarehouseStatus();
        var i = 0;
        for(var pick : content)
            if(pick.getResource() == Colours.WHITE){
                output.println("This shelf is empty");
                i++;
            }
            else{
                var color = pick.getResource();
                var qnt = pick.getQnt();
                output.println("On shelf #" + ++i + " there are " + qnt + " " + color.getType() + " resources");
            }
        printLine();
    }

    private void showLibrary(){
        printLine();
        output.println("These are your board slots:");
        var boardController = getGameController().getBoardController();
        var slots = boardController.getPlayer().checkLibraryStatus();
        var i = 0;
        for(var thisSlot : slots)
            if(thisSlot.isEmpty()){
                output.println("This slot is empty");
                i++;
            }
            else{
                output.println("On slot #" + ++i + " there are this cards:");
                for(var card : thisSlot)
                    showCard(card);
            }
        printLine();
    }

    private void showStrongBox(){
        printLine();
        output.println("This is what's in your StrongBox:");
        var boardController = getGameController().getBoardController();
        var player = boardController.getPlayer();
        var map = player.checkStrongBoxStatus();
        map.keySet().stream().map(color -> color.getType() + " -> " + map.get(color)).forEach(output::println);
        printLine();
    }

    private Card getCorrectLeaderCardCode(String response){
        try{
            var code = Integer.parseInt(response);
            while(code < 1 || code > 16){
                output.println("Not a correct code");
                response = input.next().toUpperCase();
                code = Integer.parseInt(response);
            }
        } catch(NumberFormatException e){
            output.println("Not even a number");
            response = input.next().toUpperCase();
            getCorrectDevCardCode(response);
        }
        return CardLeader.getSpecificCard(response);
    }

    private boolean isFirstCharOk(String response){
        return response.charAt(0) == '1' || response.charAt(0) == '2' || response.charAt(0) == '3' || response.charAt(0) == '4';
    }

    private boolean isSecondCharOk(String response){
        return response.charAt(1) == 'B' || response.charAt(1) == 'G' || response.charAt(1) == 'P' || response.charAt(1) == 'Y';
    }

    private boolean isThirdCharOk(String response){
        return response.charAt(2) == '1' || response.charAt(2) == '2' || response.charAt(2) == '3';
    }

    private Card getCorrectDevCardCode(String response){
        while(!(response.length() == 3 && isFirstCharOk(response) && isSecondCharOk(response) && isThirdCharOk(response))){
            output.println("Not a correct code\nTry again");
            response = input.next().toUpperCase();
        }
        return CardDev.getSpecificCard(response);
    }

    private String drawMarket(Colours colours){
        return switch(colours){
            case RED -> ConsoleColors.RED + "███" + ConsoleColors.RESET;
            case BLUE -> ConsoleColors.BLUE + "███" + ConsoleColors.RESET;
            case GREY -> ConsoleColors.BLACK_BRIGHT + "███" + ConsoleColors.RESET;
            case WHITE -> ConsoleColors.WHITE + "███" + ConsoleColors.RESET;
            case YELLOW -> ConsoleColors.YELLOW + "███" + ConsoleColors.RESET;
            case PURPLE -> ConsoleColors.PURPLE + "███" + ConsoleColors.RESET;
        };
    }
}
