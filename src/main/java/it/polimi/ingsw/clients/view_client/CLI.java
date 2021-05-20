package it.polimi.ingsw.clients.view_client;

import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller_server.Controller;
import it.polimi.ingsw.server.controller_server.GameController;
import it.polimi.ingsw.server.model_server.game.Game;
import it.polimi.ingsw.server.model_server.game.Market;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CLI implements Runnable, UI, PropertyChangeListener {


    private final BufferedReader input;
    private final PrintWriter output;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String nickname;

    public CLI() {
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

    public void setup() {
        output.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
        output.println("by\n\u001B[34mAlessio Piovesan\u001B[0m,\n\u001B[32mPier Giorgio Prosperi\u001B[0m,\n\u001B[33mMatteo Salamone\u001B[0m,\n");
        var nickname = "";
        do {
            while (Objects.equals(nickname, "")) {
                output.println(">Insert your nickname: ");
                output.print(">\t");
                try {
                    nickname = input.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                output.println("> You chose: " + nickname);
                output.println("> Is it ok? [y/n] ");
                output.println(">\t");
                try {
                    String r = input.readLine();
                    if (!(r.equals("y"))) nickname = "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /*if (nameAll.contains(nickname)) {
                System.out.println("Nickname already taken!\nChange it!!");
            }
        } while (nameAll.contains(nickname));*/
        } while (nickname.equals(""));
        setNickname(nickname);
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private Card pickCard(Class T) {
        var ok = true;
        Card card = null;
        while (ok) {
            output.println("Code? (last digits)");
            try {
                String response = input.readLine().toUpperCase();
                if (T == CardLeader.class) {
                    if (Integer.parseInt(response) <= 0 || Integer.parseInt(response) >= 16) {
                        output.println("Not a correct code");
                    } else {
                        output.println("You choose " + response + ", right? [y/n]");
                        String i = input.readLine();
                        if (i.equals("y")) {
                            card = CardLeader.getSpecificCard(response);
                            ok = false;
                        }
                    }
                }
                if (T == CardDev.class) {
                    if (!(response.length() == 3 &&
                            (response.charAt(0) == '1') || response.charAt(0) == '2' || response.charAt(0) == '3' || response.charAt(0) == '4') &&
                            (response.charAt(1) == 'B' || response.charAt(1) == 'G' || response.charAt(1) == 'P' || response.charAt(1) == 'Y') &&
                            (response.charAt(2) == '1' || response.charAt(2) == '2' || response.charAt(2) == '3')) {
                        output.println("Not a correct code");
                    } else {
                        output.println("You choose " + response + ", right? [y/n]");
                        String i = input.readLine();
                        if (i.equals("y")) {
                            card = CardLeader.getSpecificCard(response);
                            ok = false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    private void chooseInitialLeaders(CardLeader[] cards) {
        var list = new ArrayList<CardLeader>();
        var finale = new CardLeader[2];
        Collections.addAll(list, cards);
        var i = 0;
        while (i < 2) {
            for (CardLeader card : list)
                showCards(card);
            var card = (CardLeader) pickCard(CardLeader.class);
            if (list.contains(card)) {
                list.remove(card);
                finale[i] = card;
                i++;
            } else output.println("Something went wrong");
        }
        pcs.firePropertyChange("myLeaderCards", "", finale);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "temporaryLeaderCards" -> chooseInitialLeaders((CardLeader[]) evt.getNewValue());

            default -> output.println("Nope");
        }
    }

    @Override
    public void run() {
        setup();
        /*Qui dovrà passare tutto da Controller creato Server*/
        var gameController = new GameController(Controller.newID(), 1, new Game(new Market()));
        gameController.setGamers(1);
        /*Questa associazione la deve fare il Sever eventualmente*/
        gameController.getActiveBoardController().addPropertyChangeListener(this);
        pcs.firePropertyChange("remoteView", "", gameController.getActiveBoardController().getPlayer().getRemoteView());
        gameController.getActiveBoardController().getPlayer().getRemoteView().addPropertyChangeListener(gameController.getActiveBoardController());
        gameController.startGame();
    }

    @Override
    public void showCards(Card card) {
        output.println(card.toString());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
