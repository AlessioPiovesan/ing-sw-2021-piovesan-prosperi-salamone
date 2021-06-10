package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class StrongBox implements Serializable{

    private final EnumMap<Colours, Integer> strongContent;

    public StrongBox(){
        strongContent = new EnumMap<>(Colours.class);
        strongContent.put(Colours.BLUE, 0);
        strongContent.put(Colours.GREY, 0);
        strongContent.put(Colours.PURPLE, 0);
        strongContent.put(Colours.YELLOW, 0);
    }

    protected void moveResources(Pick res){
        var color = res.getResource();
        int old = strongContent.get(color);
        var add = res.getQnt();
        strongContent.put(color, old + add);
    }

    protected Map<Colours, Integer> getStrongContent(){
        return strongContent;
    }
}
