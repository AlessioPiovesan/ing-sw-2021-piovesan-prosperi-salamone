package it.polimi.ingsw.server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class SocketServerToClient implements Runnable{

	private final Socket socket;
	private final Server server;

	private boolean active;

	public SocketServerToClient(Socket newSocket, Server server){
		this.socket = newSocket;
		this.server = server;
		active = true;
	}

	public void asyncSend(final Object message){
		new Thread(() -> send(message)).start();
	}

	public synchronized void send(Object message){
		try{
			var socketOut = new ObjectOutputStream(socket.getOutputStream());
			socketOut.reset();
			socketOut.writeObject(message);
			socketOut.flush();
		} catch(IOException e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void run(){
		String name;
		try{
			var socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			send("Welcome!\nWhat is your name?");
			String read = socketIn.readLine();
			name = read;
			server.lobby(this, name);
			while(isActive()){
				read = socketIn.readLine();
			}
		} catch(IOException|NoSuchElementException e){
			System.err.println("Error!" + e.getMessage());
		} finally{
			close();
		}
	}

	private synchronized boolean isActive(){
		return active;
	}

	private void close(){
		closeConnection();
		System.out.println("Deregistering client...");
		server.deregisterConnection(this);
		System.out.println("Done!");
	}

	public synchronized void closeConnection(){
		send("Connection closed!");
		try{
			socket.close();
		} catch(IOException e){
			System.err.println("Error when closing socket!");
		}
		active = false;
	}
}
