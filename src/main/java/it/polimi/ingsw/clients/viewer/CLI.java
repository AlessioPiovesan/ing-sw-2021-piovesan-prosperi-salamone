package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller.Controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

   /* public static void main(String[] args) {

        System.out.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
        System.out.println("by\n\u001B[34mAlessio Piovesan\u001B[0m,\n\u001B[32mPier Giorgio Prosperi\u001B[0m,\n\u001B[33mMatteo Salamone\u001B[0m,\n");
        //System.out.println("View full rules here: " + "URL delle regole" + "\n");
        var scanner = new Scanner(System.in);
        System.out.print(">\tInsert the server IP address\n>\t");
        ip = scanner.nextLine();
        while (!(InetAddressValidator.getInstance().isValid(ip))) {
            if (!(InetAddressValidator.getInstance().isValid(ip)))
                System.out.println("Server IP address is not valid");
            ip = scanner.nextLine();
        }
        System.out.print(">\tInsert the server port\n>\t");
        port = scanner.nextInt();
        while (port < 1023 || port > 65535) {
            if (port < 1023 || port > 65535)
                System.out.println("Server port is not valid");
            port = scanner.nextInt();
        }
        var cli = new CLI();
        cli.run();
    }*/

    public void setClient(Client client){this.client = client;}

    public void run(){
        setup();
        client.asyncWriteToSocket("nickname::" + nickname);
        client.asyncWriteToSocket("nplayers::" + client.getnPlayers());
        /*Qui dovrà passare tutto da controller creato Server*//*
        var gameController = new GameController(controller.getInstance().newID(), 1, new Game(new Market()));
        gameController.setGamers(1);*/
        /*Questa associazione la deve fare il Sever eventualmente*//*
        gameController.getActiveBoardController().addPropertyChangeListener(this);
        pcs.firePropertyChange("remoteView", "", gameController.getActiveBoardController().getPlayer().getRemoteView());
        gameController.getActiveBoardController().getPlayer().getRemoteView().addPropertyChangeListener(gameController.getActiveBoardController());
        gameController.startGame();*/
    }

    private void setup(){
        output.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
        output.println("by\n\u001B[34mAlessio Piovesan\u001B[0m,\n\u001B[32mPier Giorgio Prosperi\u001B[0m,\n\u001B[33mMatteo Salamone\u001B[0m,\n");
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
            case "marketBuffer" -> resourcesHandlerMarket(castList(Colours.class, (Collection<?>)evt.getNewValue()));
            case "ProductionBuffer" -> resourcesHandler(castList(Pick.class, (Collection<?>)evt.getNewValue()));
            default -> output.println("Nope");
        }
    }

    private void chooseInitialLeaders(CardLeader[] cards){
        var list = new ArrayList<CardLeader>();
        var finale = new CardLeader[2];
        Collections.addAll(list, cards);
        var i = 0;
        while(i < 2){
            for(CardLeader card : list)
                showCards(card);
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

    private void nextAction(){
        if(single && LocalGameController.getInstance().isLorenzoPlaying())
            LocalGameController.getInstance().moveLorenzo();
        else{
            try{
                output.println("""
                               What is your next action?
                               1) Activate production
                               2) Pick resources from the market
                               3) Buy a Development Card
                               4) Activate Leader Card
                               """);
                var action = Integer.parseInt(input.readLine());
                while(action < 0 || action > 5){
                    output.println("Command not valid unknown");
                    action = Integer.parseInt(input.readLine());
                }
                switch(action){
                    case 1 -> LocalGameController.getInstance().prodAction();
                    case 2 -> marketMove();
                    case 3 -> chooseCardDev();
                    case 4 -> wakeALeader();
                    default -> output.println("Not Possible");
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void resourcesHandlerMarket(List<Colours> bufferMarket){
        var keep = new ArrayList<Colours>();
        var loose = new ArrayList<Colours>();
        /*Mostro le risorse*/
        for(Colours colour : bufferMarket){
            try{
                output.println(colour + "Do you want to store it in your personal warehouse?\n0) No\n1)Yes\n>\t");
                var j = Integer.parseInt(input.readLine());
                while(j != 0 && j != 1){
                    output.println("Command not valid, unknown");
                    j = Integer.parseInt(input.readLine());
                }
                if(j == 0) loose.add(colour);
                else keep.add(colour);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        LocalGameController.getInstance().addFaithPnt(loose);
        for(Colours colours : keep)
            prepareResForWarehouse(colours, 1);
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

    private void resourcesHandler(List<Pick> bufferProd){
        output.println("Where do you want to pick them from?");
        try{
            for(Pick pick : bufferProd){
                output.println(pick);
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
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showCards(Card card){
        output.println(card.toString());
    }

    private Card pickCard(Class<? extends Card> clazz){
        Card card = null;
        try{
            output.println("Code? (last digits)");
            if(clazz == CardLeader.class) card = pickCardLeader();
            if(clazz == CardDev.class) card = pickCardDev();
        } catch(Exception e){
            e.printStackTrace();
        }

        return card;
    }

    private void marketMove(){
        try{
            var move = -1;
            showMarket();
            output.println("\n0 ) Row\n1) Column\n");
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
                    case 1 -> 3;
                    case 2 -> 4;
                    case 3 -> 5;
                    case 4 -> 6;
                    default -> -1;
                };
            }
            LocalGameController.getInstance().marketAction(move);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void chooseCardDev(){
        var ok = true;
        while(ok){
            try{
                var actual = LocalGameController.getInstance().getGame().getCardDevDeck();
                for(CardDev card : actual)
                    card.toString();
                output.println("Choose the card [the code is the last digits]");
                var j = input.readLine();
                output.println("Is " + j + " right? [y/n]");
                var i = input.readLine();
                if(i.equals("y")){
                    for(CardDev cardDev : actual)
                        if(j.equals(cardDev.name())) LocalGameController.getInstance().cardDevAction(cardDev);
                }
                else ok = false;
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void wakeALeader(){
        var ok = true;
        while(ok){
            try{
                var actual = LocalGameController.getInstance().getGame().getBoard(false).getMyLeaderCards();
                for(CardLeader card : actual)
                    card.toString();
                output.println("Choose the card [the code is the last digits]");
                var j = input.readLine();
                output.println("Is " + j + " right? [y/n]");
                var i = input.readLine();
                if(i.equals("y")){
                    for(CardLeader card : actual)
                        if(j.equals(card.name())) LocalGameController.getInstance().cardLeadAction(card);
                }
                else ok = false;
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void prepareResForWarehouse(Colours colour, int qnt){
        try{
            output.print("Level for this resource? [1-3]\n" + colour.getType() + "\n>\t");
            var lvl = Integer.parseInt(input.readLine()) - 1;
            /*MANCANO I CONTROLLI*/
            if(lvl > -1 && lvl < 3)
                LocalGameController.getInstance().getBoardController().warehouseResHandler(colour, qnt, lvl);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private Card pickCardLeader() throws IOException{
        var response = input.readLine().toUpperCase();
        while(Integer.parseInt(response) <= 0 || Integer.parseInt(response) >= 17){
            output.println("Not a correct code");
            response = input.readLine().toUpperCase();
        }
        return CardLeader.getSpecificCard(response);
    }

    private Card pickCardDev() throws IOException{
        var response = input.readLine().toUpperCase();
        while(!(response.length() == 3 && (response.charAt(0) == '1') || response.charAt(0) == '2' || response.charAt(0) == '3' || response.charAt(0) == '4') && (response.charAt(1) == 'B' || response.charAt(1) == 'G' || response.charAt(1) == 'P' || response.charAt(1) == 'Y') && (response.charAt(2) == '1' || response.charAt(2) == '2' || response.charAt(2) == '3')){
            output.println("Not a correct code");
            response = input.readLine().toUpperCase();
        }
        return CardDev.getSpecificCard(response);
    }

    private String drawMarket(Colours colours){
        switch(colours){
            case RED -> {return ConsoleColors.RED + "██";}
            case BLUE -> {return ConsoleColors.BLUE + "██";}
            case GREY -> {return ConsoleColors.BLACK_BRIGHT + "██";}
            case WHITE -> {return ConsoleColors.WHITE + "██";}
            case YELLOW -> {return ConsoleColors.YELLOW + "██";}
            case PURPLE -> {return ConsoleColors.PURPLE + "██";}
            default -> {return null;}
        }
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
}
