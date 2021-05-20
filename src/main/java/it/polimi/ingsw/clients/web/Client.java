package it.polimi.ingsw.clients.web;

import it.polimi.ingsw.clients.controller_client.GameControllerOnCLient;
import it.polimi.ingsw.clients.view_client.CLI;
import it.polimi.ingsw.clients.view_client.GUI;
import it.polimi.ingsw.clients.view_client.ModelView;
import it.polimi.ingsw.clients.view_client.UI;
import it.polimi.ingsw.server.model_server.board.Board;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private final String ip;
    private final int port;
    private UI ui;

    /*
     * @param ip of type String - ip reference, should be 127.0.0.1
     * @param port of type int - port reference, should be 12345
     */
    public Client(String ip, int port, int i) {
        this.ip = ip;
        this.port = port;
        if (i == 0) ui = new CLI();
        if (i == 1) ui = new GUI();
        if (ui != null) new ModelView(ui);
    }

    public UI getUi() {
        return ui;
    }

    public void run(int players) throws IOException {
        if (players == 1) {
            var gameController = GameControllerOnCLient.getInstance();
            for (Board board : gameController.getGame().getBoards())
                board.addPropertyChangeListener(ui);
            ui.setIfSingle(true);
            gameController.startGame();
        }
        var socket = new Socket(ip, port);
        System.out.println("Connection established");
        ui.run();
    }
}
