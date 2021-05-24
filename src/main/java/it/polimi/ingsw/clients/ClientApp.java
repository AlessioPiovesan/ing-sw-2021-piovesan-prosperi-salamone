package it.polimi.ingsw.clients;

import it.polimi.ingsw.clients.model_client.single.ModelView;
import it.polimi.ingsw.clients.view_client.CLI;
import it.polimi.ingsw.clients.view_client.GUI;
import it.polimi.ingsw.clients.web.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientApp{

	private final String ip;
	private final int port;

	/**
	 * /*
	 *
	 * @param ip   of type String - ip reference, should be 127.0.0.1
	 * @param port of type int - port reference, should be 12345
	 */
	public ClientApp(String ip, int port){
		this.ip = ip;
		this.port = port;
	}

	public ClientApp(){
		ip = "localhost";
		port = 12345;
	}

	public static void startCLI(int input){
		System.out.println("You selected the CLI interface, have fun!\nStarting...");
		if(input == 1){
			var cli = new CLI();
			new ModelView(cli);
			cli.run();
		}
	}

	public static void startGUI(int input){
		System.out.println("You selected the GUI interface, have fun!\nStarting...");
		if(input == 1){
			var gui = new GUI();
			new ModelView(gui);
			gui.run();
		}
	}

	public static void chooseUI(int input){
		var exit = true;
		while(exit){
			System.out.print("""
			                 How do you wanna play?
			                 0. COMMAND-LINE INTERFACE
			                 1. GRAPHICAL USER INTERFACE
			                 > Type the number of the desired option!
			                 >>\040""");
			var reader = new BufferedReader(new InputStreamReader(System.in));
			int ui = -1;
			try{
				ui = reader.read();
			} catch(IOException e){
				e.printStackTrace();
			} finally{
				if(ui == 0 || ui == 1){
					exit = false;
					/*DA CHIEDERE IP E PORTA*/
					var client = new Client("localhost", 12345, ui);
					try{
						client.run(input);
					} catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}


