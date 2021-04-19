package it.polimi.ingsw.server.viewer_server;

import it.polimi.ingsw.server.model_server.Mod;

public class RemoteView extends View{

    private Connection connection;

    public RemoteView (Mod mod, String opponent, Connection c){
    }

    @Override
    public void update (Mod message){

    }

    @Override
    protected void showModel (Mod mod){
        connection.send("");
    }

    private class MessageReceiver implements Observer<String>{

        @Override
        public void update (String message){
            System.out.println("Received: " + message);
            try {
                /*fare cose*/
            } catch (IllegalArgumentException e) {
                connection.send("Error! Make your move");
            }
        }
    }
}
