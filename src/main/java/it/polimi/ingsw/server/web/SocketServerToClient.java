package it.polimi.ingsw.server.web;

import java.io.IOException;
import java.io.ObjectInputStream;
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
        try(var socketIn = new ObjectInputStream(socket.getInputStream())){
            while(isActive()){
                var inputObject = socketIn.readObject();
                if(inputObject instanceof String){
                    System.out.println((String)inputObject); /*DA MANDARE A UN QUALCHE TIPO DI INTERPRETE*/
                }
					/*else if(inputObject instanceof Board){
						((Board)inputObject).print();
					}*/ /*ALTRE COSE CHE PUO' RICEVERE*/
                else{
                    throw new IllegalArgumentException();
                }
            }
        } catch(IOException|NoSuchElementException|ClassNotFoundException e){
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
