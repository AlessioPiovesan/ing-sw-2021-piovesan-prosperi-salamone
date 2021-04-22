package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;

import java.util.EnumMap;

public class StrongBox{



    private EnumMap<Colours, Integer> comp;

    public StrongBox ( ){
        comp = new EnumMap<>(Colours.class);
        comp.put(Colours.BLUE, 0);
        comp.put(Colours.GREY, 0);
        comp.put(Colours.PURPLE, 0);
        comp.put(Colours.YELLOW, 0);
    }

    protected void addResources (Pick res){ comp.replace(res.getResource(), comp.get(res.getResource()) + res.getQnt()); }

    protected void takeResources (Pick res){ comp.replace(res.getResource(), comp.get(res.getResource()) - res.getQnt()); }

    public EnumMap<Colours, Integer> getComp ( ){
        return comp;
    }
}
