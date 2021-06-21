package it.polimi.ingsw.clients;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.web.Local;

/**
 * Class ClientApp instantiates and starts the client
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class ClientApp{

    private ClientApp(){}

    /**
     * method start starts the client
     *
     * @param input of type int
     */
    public static void start(int input){
        Client client;
        if(input == 1) client = new Local(0);
        else client = new Client(0);
        client.getUi().setClient(client);
        client.run();
    }
}
