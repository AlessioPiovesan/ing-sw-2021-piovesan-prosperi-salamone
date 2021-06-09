package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.Requirements;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller.Controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;

/*CONTROLLA CHE SIA TUTTO SCRITTO IN INGLESE*/

public class CLI implements Runnable, UI{

    private final BufferedReader input;
    private final PrintWriter output;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String nickname;
    private boolean single;
    private Client client;

    public CLI(){
        input = new BufferedReader(new InputStreamReader(System.in));
        output = new PrintWriter(System.out, true);
    }

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection){
        List<T> result = new ArrayList<>(rawCollection.size());
        for(Object o : rawCollection){
            try{
                result.add(clazz.cast(o));
            } catch(ClassCastException e){
                // log the exception or other error handling
            }
        }
        return result;
    }

    public void run(){
        giveNickname();
        client.asyncWriteToSocket("nickname::" + nickname);
        client.asyncWriteToSocket("nplayers::" + client.getnPlayers());
    }

    private void giveNickname(){
        nickname = "";
        do{
            while(Objects.equals(nickname, "")){
                output.println(">Insert your nickname: ");
                output.print(">\t");
                try{
                    nickname = input.readLine();
                } catch(Exception e){
                    e.printStackTrace();
                }
                output.println("> You chose: " + nickname);
                output.println("> Is it ok? [y/n] ");
                output.println(">\t");
                try{
                    String r = input.readLine();
                    if(!(r.equals("y"))) nickname = "";
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        } while(nickname.equals(""));
        setNickname(nickname);
    }

    private void setNickname(String nickname){
        this.nickname = nickname;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        try{
            switch(evt.getPropertyName()){
                case "temporaryLeaderCards" -> chooseInitialLeaders((CardLeader[])evt.getNewValue());
                case "firstOp" -> {
                    if(single) LocalGameController.getInstance().nextPlayer();
                    else Controller.getInstance().getGameControllerById(0).initialResourcesHandler();
                }
                case "nextPlayer" -> {
                    if(single) LocalGameController.getInstance().nextPlayer();
                    else Controller.getInstance().getGameControllerById(0);
                }
                case "nextAction" -> nextAction();
                case "marketBuffer", "bufferProd" -> resourcesHandlerMarket(castList(Colours.class, (Collection<?>)evt.getNewValue()));
                case "productionBuffer" -> resourcesHandler(castList(Pick.class, (Collection<?>)evt.getNewValue()));
                case "costCardDev" -> payForCardDev(castList(Pick.class, (Collection<?>)evt.getNewValue()));

                case "gameID" -> output.println("Game ID: " + evt.getNewValue() + "\nWait for the other players");
                default -> output.println(evt.getPropertyName() + " is not handled");
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void payForCardDev(List<Pick> cost) throws IOException{
        showWarehouse();
        showStrongBox();
        for(var pick : cost){
            var color = pick.getResource();
            var j = resourcesDestination(color, "\nDo you want to take this from\n0) Warehouse\n1)StrongBox\n>\t", "Wrong option");
            if(j == 0) LocalGameController.getInstance().getBoardController().takeFromWarehouse(color);
            else LocalGameController.getInstance().getBoardController().takeFromStrongbox(color);
        }
        LocalGameController.getInstance().nextPlayer();
    }

    private void chooseInitialLeaders(CardLeader[] cards){
        var list = new ArrayList<CardLeader>();
        var finale = new CardLeader[2];
        Collections.addAll(list, cards);
        var i = 0;
        while(i < 2){
            for(CardLeader card : list) showCards(card);
            var card = (CardLeader)pickCard(CardLeader.class);
            if(list.contains(card)){
                list.remove(card);
                finale[i] = card;
                i++;
            }
            else output.println("Something went wrong");
        }
        if(single) LocalGameController.getInstance().getBoardController().getPlayer().setMyLeaderCards(finale);
    }

    private void nextAction() throws IOException{
        if(single && LocalGameController.getInstance().isLorenzoPlaying())
            LocalGameController.getInstance().moveLorenzo();
        else{
            output.print("""
                         What is your next action?
                         1) Activate production
                         2) Pick resources from the market
                         3) Buy a Development Card
                         4) Activate Leader Card
                         >\040\040""");
            output.flush();
            var action = Integer.parseInt(input.readLine());
            while(action < 1 || action > 5){
                output.println("Please type a valid number [1-4]");
                action = Integer.parseInt(input.readLine());
            }
            if(action == 1 && LocalGameController.getInstance().getBoardController().getPlayer().isProducible()){
                output.println("You don't have any development card, you can't activate the production");
                nextAction();
            }
            switch(action){
                case 1 -> LocalGameController.getInstance().prodAction();
                case 2 -> marketMove();
                case 3 -> chooseCardDev();
                case 4 -> wakeALeader();
                default -> output.println("Not Possible");
            }
        }
    }

    private void resourcesHandlerMarket(List<Colours> bufferMarket) throws IOException{
        var keep = new ArrayList<Colours>();
        var loose = new ArrayList<Colours>();
        /*Mostro le risorse*/
        for(Colours colour : bufferMarket){
            if(colour == Colours.WHITE) loose.add(colour);
            else{
                var j = resourcesDestination(colour, "\nDo you want to store it in your personal warehouse?\n0) No\n1)Yes\n>\t", "Command not valid, unknown");
                if(j == 0) loose.add(colour);
                else keep.add(colour);
            }
        }
        LocalGameController.getInstance().addFaithPnt(loose);
        output.println("This resources need handling:");
        var colors = new StringBuilder();
        for(Colours colour : keep) colors.append(colour.getType()).append("\t");
        output.println(colors);
        for(Colours colours : keep)
            prepareResForWarehouse(colours, 1);
        showWarehouse();
        LocalGameController.getInstance().nextPlayer();
    }

    private int resourcesDestination(Colours colour, String s, String s2) throws IOException{
        output.print(colour.getType() + s);
        output.flush();
        var j = Integer.parseInt(input.readLine());
        while(j != 0 && j != 1){
            output.println(s2);
            j = Integer.parseInt(input.readLine());
        }
        return j;
    }

    private void resourcesHandler(List<Pick> bufferProd) throws IOException{
        output.println("Where do you want to pick them from?");
        for(Pick pick : bufferProd){
            output.println(pick.getResource().getType());
            output.println("0)Warehouse o 0)StrongBox?");
            var j = Integer.parseInt(input.readLine());
            while(j != 0 && j != 1){
                output.println("Command not valid, unknown");
                j = Integer.parseInt(input.readLine());
            }
            switch(j){
                case 0 -> prepareResForWarehouse(pick.getResource(), -pick.getQnt());
                case 1 -> LocalGameController.getInstance().getBoardController().getPlayer().storeSB(new Pick[]{new Pick(pick.getResource(), -pick.getQnt())});
                default -> output.println("Not possible");
            }
        }
        showWarehouse();
        LocalGameController.getInstance().nextPlayer();
    }

    @Override
    public void showCards(Card card){
        output.println(card.toString());
        output.println("_________________________________________________");
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void setSingle(boolean single){this.single = single;}

    @Override
    public void showMarket(){
        var market = LocalGameController.getInstance().getGame().getMarket().getTray();
        var print = new StringBuilder();
        for(var i = 0; i < Constants.XMARKET.getVal(); i++){
            for(var j = 0; j < Constants.YMARKET.getVal(); j++){
                print.append(drawMarket(market[i][j])).append("\t");
            }
            print.append("\n");
        }
        output.println(print);
    }

    @Override
    public void setClient(Client client){this.client = client;}

    private Card pickCard(Class<? extends Card> clazz){
        Card card = null;
        try{
            output.println("Code? (last digits)");
            var code = input.readLine();
            if(clazz == CardLeader.class) card = getCorrectLeaderCardCode(code);
            if(clazz == CardDev.class) card = getCorrectDevCardCode(code);
        } catch(Exception e){
            e.printStackTrace();
        }
        return card;
    }

    private void marketMove() throws IOException{
        var move = -1;
        showMarket();
        output.println("\n0 ) Row\n1 ) Column");
        var i = Integer.parseInt(input.readLine());
        if(i == 0){
            output.println("Which one? [1-3]");
            var j = Integer.parseInt(input.readLine());
            move = switch(j){
                case 1 -> 0;
                case 2 -> 1;
                case 3 -> 2;
                default -> -1;

            };
        }
        if(i == 1){
            output.println("Which one? [1-4]");
            var j = Integer.parseInt(input.readLine());
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
        else LocalGameController.getInstance().marketAction(move);
    }

    private void chooseCardDev() throws IOException{
        var actual = canUseCardDev(LocalGameController.getInstance().getGame().getCardDevDeck());
        if(actual.isEmpty()){
            output.println("You can not buy any card");
            nextAction();
        }
        else{
            for(var card : actual)
                showCards(card);
            var card = (CardDev)pickCard(CardDev.class);
            if(actual.contains(card)){
                try{
                    var slot = pickLibSlot(card);
                    if(slot >= 0) LocalGameController.getInstance().cardDevAction(card, slot);
                    else{
                        output.println("You don't hav a slot for this card");
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

    private int pickLibSlot(CardDev card) throws IOException{
        showLibrary();
        output.print("Slot for " + card.name() + "? [1-3]\n>\t");
        output.flush();
        var slot = Integer.parseInt(input.readLine());
        while(!(slot > -1 && slot < 4)){
            output.println("This is not a valid slot");
            slot = Integer.parseInt(input.readLine());
        }
        return slot - 1;
    }

    private List<Card> canUseCardDev(List<CardDev> cards){
        var resAll = LocalGameController.getInstance().getBoardController().getPlayer().getResAll();
        var list = new LinkedList<Card>();
        for(var card : cards){
            var i = 0;
            for(var cost : card.getCost())
                if(cost.getQnt() <= resAll.get(cost.getResource())) i++;
            if(i == card.getCost().length) list.add(card);
        }
        return list;
    }

    private List<Card> canUseCardLeader(CardLeader[] cards){
        var resAll = LocalGameController.getInstance().getBoardController().getPlayer().getResAll();
        var slots = LocalGameController.getInstance().getBoardController().getPlayer().checkLibraryStatus();
        var list = new LinkedList<Card>();
        for(var card : cards)
            for(var req : card.getReq()){
                if(req.getClass() == Requirements.RequirementsResources.class)
                    checkOnReqRes(resAll, list, card, (Requirements.RequirementsResources)req);
                if(req.getClass() == Requirements.RequirementsCard.class)
                    checkOnReqCard(slots, list, card, (Requirements.RequirementsCard)req);
            }
        return list;
    }

    private void checkOnReqCard(List<ArrayList<CardDev>> slots, LinkedList<Card> list, CardLeader card, Requirements.RequirementsCard req){
        for(var slot : slots){
            for(var thisCard : slot)
                if(thisCard != null && thisCard.getColour() == req.getColour() && thisCard.getLvl() <= req.getCardLvl()){
                    list.add(card);
                    break;
                }
            if(list.contains(card)) break;
        }
    }

    private void checkOnReqRes(Map<Colours, Integer> resAll, LinkedList<Card> list, CardLeader card, Requirements.RequirementsResources req){
        if(req.getQnt() <= resAll.get(req.getColour())) list.add(card);
    }

    private void wakeALeader() throws IOException{
        var actual = canUseCardLeader(LocalGameController.getInstance().getGame().getBoard(false).getMyLeaderCards());
        if(actual.isEmpty()){
            output.println("You can not activate any leader at the moment");
            nextAction();
        }
        else{
            for(var card : actual)
                showCards(card);
            var card = (CardLeader)pickCard(CardLeader.class);
            if(actual.contains(card)) LocalGameController.getInstance().cardLeadAction(card);
            else{
                output.println("You don't have this Leader");
                nextAction();
            }
        }
    }

    public void prepareResForWarehouse(Colours colour, int qnt) throws IOException{
        var newPick = new Pick(colour, qnt);
        var response = 0;
        if(colour == Colours.RED) LocalGameController.getInstance().getBoardController().moveFT(1);
        else{
            showWarehouse();
            output.print("Shelf for this resource? [1-3]\n" + colour.getType() + "\n>\t");
            output.flush();
            var lvl = getShelfLvl();
            var bc = LocalGameController.getInstance().getBoardController();
            while(!bc.getPlayer().checkWarehouseAdd(newPick, lvl)){
                output.println("You can not add that resource on this shelf, try something else...");
                output.print("""
                             Choose how to proceed?
                             1) Discard the resource
                             2) Rearrange the warehouse
                             3) Put on an other shelf
                             >\040\040""");
                output.flush();
                response = Integer.parseInt(input.readLine());
                while(response < 1 || response > 3){
                    output.println("Not a valid command\n>\t");
                    response = Integer.parseInt(input.readLine());
                }
                switch(response){
                    case 1 -> LocalGameController.getInstance().addFaithPnt(Collections.singletonList(colour));
                    case 2 -> {
                        output.println("Starting shelf?");
                        var first = getShelfLvl();
                        var firstQnt = bc.getPlayer().checkWarehouseStatus().get(first).getQnt();
                        output.println("Destination?");
                        var second = getShelfLvl();
                        var secondQnt = bc.getPlayer().checkWarehouseStatus().get(second).getQnt();
                        if(first + 1 >= secondQnt && second + 1 >= firstQnt) bc.rearrangeWarehouse(first, second);
                        else output.println("You can't do that");
                        showWarehouse();
                    }
                    case 3 -> {
                        showWarehouse();
                        output.print("Shelf for this resource? [1-3]\n" + colour.getType() + "\n>\t");
                        output.flush();
                        lvl = getShelfLvl();
                    }
                    default -> output.println("Not possible");
                }
                if(response == 1) break;
            }
            if(response != 1)
                LocalGameController.getInstance().getBoardController().warehouseResHandler(colour, qnt, lvl);
        }
    }

    private int getShelfLvl() throws IOException{
        var lvl = Integer.parseInt(input.readLine());
        while(!(lvl > 0 && lvl < 4)){
            output.println("This is not a valid shelf");
            lvl = Integer.parseInt(input.readLine());
        }
        return lvl - 1;
    }

    private void showWarehouse(){
        output.println("At the moment the situation with the warehouse is this:");
        var b = LocalGameController.getInstance().getBoardController().getPlayer();
        var i = 0;
        for(var pick : b.checkWarehouseStatus())
            if(pick.getResource() == Colours.WHITE){
                output.println("This shelf is empty");
                i++;
            }
            else
                output.println("On shelf #" + ++i + " there are " + pick.getQnt() + " " + pick.getResource().getType() + " resources");
    }

    private void showLibrary(){
        output.println("These are your board slots:");
        var b = LocalGameController.getInstance().getBoardController().getPlayer();
        var i = 0;
        for(var thisSlot : b.checkLibraryStatus())
            if(thisSlot.isEmpty()){
                output.println("This slot is empty");
                i++;
            }
            else{
                output.println("On slot #" + ++i + " there are this cards:\n");
                for(var card : thisSlot)
                    output.println("\t" + card.toString());
            }
    }

    private void showStrongBox(){
        output.println("This is what's in your StrongBox:");
        var b = LocalGameController.getInstance().getBoardController().getPlayer();
        var map = b.checkStrongBoxStatus();
        map.keySet().stream().map(color -> color.getType() + " -> " + map.get(color)).forEach(output::println);
    }

    private Card getCorrectLeaderCardCode(String response) throws IOException{
        while(Integer.parseInt(response) <= 1 || Integer.parseInt(response) >= 17){
            output.println("Not a correct code");
            response = input.readLine().toUpperCase();
        }
        return CardLeader.getSpecificCard(response);
    }

    private Card getCorrectDevCardCode(String response) throws IOException{
        response.toUpperCase();
        while(!(response.length() == 3
                && (response.charAt(0) == '1') ||
                response.charAt(0) == '2' ||
                response.charAt(0) == '3' ||
                response.charAt(0) == '4')
                && (response.charAt(1) == 'B' ||
                response.charAt(1) == 'G' ||
                response.charAt(1) == 'P' ||
                response.charAt(1) == 'Y') &&
                (response.charAt(2) == '1' ||
                response.charAt(2) == '2' ||
                        response.charAt(2) == '3')){
            output.println("Not a correct code");
            response = input.readLine().toUpperCase();
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
