package it.polimi.ingsw.clients;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.web.Local;

import java.io.InputStreamReader;
import java.util.Scanner;

public class ClientApp{

    private ClientApp(){}

    public static void chooseUI(int input){

        var scanner = new Scanner(new InputStreamReader(System.in));
        var ui = -1;

        System.out.print("""
                         How do you wanna play?
                         0. COMMAND-LINE INTERFACE
                         1. GRAPHICAL USER INTERFACE
                         > Type the number of the desired option!
                         >>\040\040""");
        ui = scanner.nextInt();
        while(ui != 0 && ui != 1){
            System.err.println("Command not valid, retry");
            ui = scanner.nextInt();
        }
        if(input == 1) new Local(ui).run();
        else{
            var client = new Client(ui);
            client.run();
            client.getUi().setClient(client);
        }
    }
}
