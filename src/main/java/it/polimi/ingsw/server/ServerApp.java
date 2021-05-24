package it.polimi.ingsw.server;

import it.polimi.ingsw.server.web.Server;

import java.io.IOException;

public class ServerApp{

	private ServerApp(){
	}

	public static void start(){
		// Chiedere la porta?
		var port = 12345;
		Server server = null;
		try{
			server = new Server(port);
		} catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Si accende!");
		if(server != null){
			server.run();
		}
	}
}


