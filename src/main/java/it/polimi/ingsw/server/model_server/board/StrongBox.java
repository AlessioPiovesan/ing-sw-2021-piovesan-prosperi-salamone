package it.polimi.ingsw.server.model_server.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;

import java.util.EnumMap;
import java.util.Map;

public class StrongBox {


    private final EnumMap<Colours, Integer> comp;

    public StrongBox() {
        comp = new EnumMap<>(Colours.class);
        comp.put(Colours.BLUE, 0);
        comp.put(Colours.GREY, 0);
        comp.put(Colours.PURPLE, 0);
        comp.put(Colours.YELLOW, 0);
    }

    protected void moveResources(Pick res) {
        comp.replace(res.getResource(), comp.get(res.getResource()) + res.getQnt());
    }

    public Map<Colours, Integer> getComp() {
        return comp;
    }
}
