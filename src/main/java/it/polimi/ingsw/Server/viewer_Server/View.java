package it.polimi.ingsw.Server.viewer_Server;

import it.polimi.ingsw.Server.model_Server.Gioco.Game;
import it.polimi.ingsw.Server.model_Server.Mod;


public abstract class View extends Observable<Game> implements Observer<Mod> {

    void showModel(Mod mod){}
}
