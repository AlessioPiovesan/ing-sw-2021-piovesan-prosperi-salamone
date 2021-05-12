package it.polimi.ingsw.client.web;

import it.polimi.ingsw.client.view_client.ModelView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionSocket {
    private final String serverAddress;
    private final int serverPort;
    private final Logger logger = Logger.getLogger(getClass().getName());
    SocketListener listener;
    private ObjectOutputStream outputStream;

    public ConnectionSocket(String ip, int port) {
        this.serverAddress = ip;
        this.serverPort = port;
    }

    public boolean setup(String nickname, ModelView modelView) {
        try {
            System.out.println(
                    "\033[33m" + "Configuring socket connection..." + "\033[0m");
            System.out.println(
                    "\033[33m"
                            + "Opening a socket server communication on port "
                            + serverPort
                            + "..."
                            + "\033[0m");
            Socket socket;
            try {
                socket = new Socket(serverAddress, serverPort);
            } catch (SocketException | UnknownHostException e) {
                return false;
            }
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            var input = new ObjectInputStream(socket.getInputStream());
            listener = new SocketListener(socket, modelView, input);
            var thread = new Thread(listener);
            thread.start();
            return true;
        } catch (IOException e) {
            System.err.println("Error during socket configuration! Application will now close.");
            logger.log(Level.SEVERE, e.getMessage(), e);
            System.exit(0);
            return false;
        }
    }
}
