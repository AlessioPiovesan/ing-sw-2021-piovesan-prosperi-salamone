package it.polimi.ingsw.Server.viewer_Server;

import it.polimi.ingsw.Server.model_Server.Mod;

public class RemoteView extends View{

    @Override
    public void update (Mod message){

    }

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            try{
                /*fare cose*/
            } catch (IllegalArgumentException e) {
                connection.send("Error! Make your move");
            }
        }
    }

    private Connection connection;

    public RemoteView(Mod mod, String opponent, Connection c){    }

    @Override
    protected void showModel(Mod mod) {
        connection.send("");
    }
}
