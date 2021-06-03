package it.polimi.ingsw.clients;

import it.polimi.ingsw.clients.multiplayer.web.Client;
import it.polimi.ingsw.clients.singleplayer.web.Local;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientApp{

    private ClientApp(){}

    public static void chooseUI(int input){

        var reader = new BufferedReader(new InputStreamReader(System.in));
        var ui = -1;

        try{
            System.out.print("""
                             How do you wanna play?
                             0. COMMAND-LINE INTERFACE
                             1. GRAPHICAL USER INTERFACE
                             > Type the number of the desired option!
                             >>\040""");
            ui = Integer.parseInt(reader.readLine());
            while(ui != 0 && ui != 1){
                System.err.println("Command not valid unknown");
                ui = Integer.parseInt(reader.readLine());
            }
            if(input == 1) new Local(ui).run();
            else{
                System.out.print(">\tInsert the server IP address\n>\t");
                var ip = reader.readLine();
                while(!(InetAddressValidator.getInstance().isValid(ip))){
                    System.err.println("Server IP address is not valid");
                    ip = reader.readLine();
                }
                System.out.print(">\tInsert the server port\n>\t");
                var port = Integer.parseInt(reader.readLine());
                while(port < 1023 || port > 65535){
                    System.err.println("Server port is not valid");
                    port = Integer.parseInt(reader.readLine());
                }
                var client = new Client(ip, port, ui);
                System.out.print(">\tInsert the the number of players you want for this match [2-4]\n>\t");
                var players = Integer.parseInt(reader.readLine());
                while(players <= 1 || players >= 5){
                    System.err.println("We can not support that number of players");
                    players = Integer.parseInt(reader.readLine());
                }
                client.setNPlayers(players);
                client.run();
                client.getUi().setClient(client);
                client.getUi().run();
            }
        } catch(IOException e){
            System.err.println("Numeric format requested, application will now close...\n" + e.getMessage());
            System.exit(-1);
        }
    }
}
