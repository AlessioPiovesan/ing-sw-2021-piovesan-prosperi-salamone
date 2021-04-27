package it.polimi.ingsw.server.model_server.gioco;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Constants;

import java.util.EnumMap;

public class Supply {
    EnumMap<Colours, Integer> cont;

    public Supply() {
        cont = new EnumMap<>(Colours.class);
        cont.put(Colours.BLUE, Constants.MAXMARBLESB.getVal() * 8);
        cont.put(Colours.GREY, Constants.MAXMARBLESG.getVal() * 8);
        cont.put(Colours.PURPLE, Constants.MAXMARBLESP.getVal() * 8);
        cont.put(Colours.YELLOW, Constants.MAXMARBLESY.getVal() * 8);
    }

    public void moveResources(Colours color, int qnt) {
        cont.replace(color, cont.get(color) + qnt);
    }

}
