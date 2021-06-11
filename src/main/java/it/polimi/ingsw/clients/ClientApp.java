package it.polimi.ingsw.clients;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.web.Local;

public class ClientApp{

    private ClientApp(){}

    public static void start(int input){
        Client client;
        if(input == 1) client = new Local(0);
        else client = new Client(0);
        client.run();
        client.getUi().setClient(client);

    }
}
