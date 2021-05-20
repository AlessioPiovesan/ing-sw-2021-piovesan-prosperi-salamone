package it.polimi.ingsw.server.web;

import it.polimi.ingsw.server.controller_server.Controller;
import it.polimi.ingsw.server.web.serialize.SerializedMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientHandler implements Runnable {

    private final Socket socket;

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            var in = new ObjectInputStream(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream());
            while (!socket.isClosed()) {
                try {
                    var input = (SerializedMessage) in.readObject();
                    if (input.message != null) {
                        var command = input.message;
                        //actionHandler(command);
                    } else if (input.action != null) {
                        var action = input.action;
                        //actionHandler(action);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //close connections
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void readAction(String action) {
        Controller.getInstance();
    }
}
