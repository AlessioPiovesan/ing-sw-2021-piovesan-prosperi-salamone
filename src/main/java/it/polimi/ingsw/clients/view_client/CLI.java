package it.polimi.ingsw.clients.view_client;

import it.polimi.ingsw.clients.web.ConnectionSocket;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.controller_server.GameController;
import it.polimi.ingsw.server.model_server.game.Game;
import it.polimi.ingsw.server.model_server.game.Market;

import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.util.*;

public class CLI implements Runnable, UI {

    private static final String IP = "";
    private static final int PORT = 0;
    private final Scanner input;
    private final LinkedList<String> nameAll = new LinkedList<>();
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final PrintStream output;
    private final boolean activatedGame;
    private final ModelView modelView;
    private ConnectionSocket connectionSocket;

    public CLI() {
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        activatedGame = true;
        modelView = new ModelView(this);
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

    public static void main(String[] args) {
        ArrayList<CardLeader> cardLeaderDeck = new ArrayList<>();
        cardLeaderDeck.addAll(Arrays.asList(CardLeader.values()));
    }

    @Override
    public void run() {
        setup();
        GameController gameController = new GameController(new Game(new Market()));
        gameController.setGamers(nameAll.size());
        gameController.startGame();
    }

    public void setup() {
        var nickname = "";
        do {
            while (Objects.equals(nickname, "")) {
                System.out.println(">Insert your nickname: ");
                System.out.println(">");
                nickname = input.nextLine();
                System.out.println("> You chose: " + nickname);
                System.out.println("> Is it ok? [y/n] ");
                System.out.print(">");
                String r = input.nextLine();
                if (!(r.equals("y"))) nickname = null;
            }
            if (nameAll.contains(nickname)) {
                System.out.println("Nickname already taken!\nChange it!!");
            }
        } while (nameAll.contains(nickname));
        nameAll.add(nickname);
        /*connectionSocket = new ConnectionSocket(ip, port);
        try {
            if (!connectionSocket.setup(nickname, modelView)) {
                System.err.println("The entered IP/port doesn't match any active server or the server is not running. Please try again!");
                CLI.main(new String[0]);
            }
            System.out.println("Socket Connection setup completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void showCards(List<CardLeader> list) {
        for (CardLeader cardLeader : list) {
            System.out.println(cardLeader.toString());
        }
    }
}