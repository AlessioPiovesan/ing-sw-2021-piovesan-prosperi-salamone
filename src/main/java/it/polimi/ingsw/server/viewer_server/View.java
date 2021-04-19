package it.polimi.ingsw.server.viewer_server;

import it.polimi.ingsw.server.model_server.gioco.Game;
import it.polimi.ingsw.server.model_server.Mod;


public abstract class View extends Observable<Game> implements Observer<Mod>{

    void showModel (Mod mod){
    }
}
