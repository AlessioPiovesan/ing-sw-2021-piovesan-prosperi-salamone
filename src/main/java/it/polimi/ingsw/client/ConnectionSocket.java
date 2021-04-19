package it.polimi.ingsw.client;

import java.io.ObjectOutputStream;

public class ConnectionSocket{
    private final String serverAddress;
    private final int serverPort;
    SocketListener listener;
    private ObjectOutputStream outputStream;

    public ConnectionSocket (String ip, int port){
        this.serverAddress = ip;
        this.serverPort = port;
    }
}
