package it.polimi.ingsw.server;

import it.polimi.ingsw.server.viewer_server.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.out.println("Impossible to start the server!\n" + e.getMessage());
        }

        System.out.println("Si accende!");

        /*crea controller e model*/
        /*riceve l'info dal client e dice al controller il numero di giocatori*/

    }

    /*public synchronized void lobby (SocketClientConnection c) throws InterruptedException{
        waiting.add(c);
        if (waiting.size() == 1){
            c.setPlayers(
                    new RequestPlayersNumber(
                            idMapClient.get(c.getClientID()).getNickname()
                                    + ", you are"
                                    + " the lobby host.\nChoose the number of players! [2/3]",
                            false));
        }
        else if (waiting.size() == totalPlayers){
            System.err.println(
                    Constants.getInfo() + "Minimum player number reached. The match is starting.");
            for (int i = 3;i > 0;i--){
                currentGame.sendAll(new CustomMessage("Match starting in " + i, false));
                TimeUnit.MILLISECONDS.sleep(500);
            }
            currentGame.sendAll(new CustomMessage("The match has started!", false));
            waiting.clear();
            PlayerColors.reset();
            currentGame.setup();
        }
        else{
            currentGame.sendAll(
                    new CustomMessage((totalPlayers - waiting.size()) + " slots left.", false));
        }
    }*/
}


