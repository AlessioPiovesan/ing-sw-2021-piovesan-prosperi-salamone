package it.polimi.ingsw.Server.model_Server;

import java.util.HashMap;

public class Supply{
    HashMap<Colours, Integer> cont;

    Supply ( ){
        cont = new HashMap<> ();
        cont.put (Colours.BLUE, 0);
        cont.put (Colours.GREY, 0);
        cont.put (Colours.PURPLE, 0);
        cont.put (Colours.YELLOW, 0);
    }

    public void addResources (Colours color, int qnt){
        cont.replace (color, cont.get (color) + qnt);
    }
}
