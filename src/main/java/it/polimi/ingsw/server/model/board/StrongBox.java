package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class Strongbox describes a player's Strongbox, a container that can store an infinite amount of resources
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public class StrongBox{

    private final EnumMap<Colours, Integer> strongContent;

    /**
     * Constructor StrongBox sets default quantity of every resource to zero
     *
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public StrongBox(){
        strongContent = new EnumMap<>(Colours.class);
        strongContent.put(Colours.BLUE, 0);
        strongContent.put(Colours.GREY, 0);
        strongContent.put(Colours.PURPLE, 0);
        strongContent.put(Colours.YELLOW, 0);
    }

    /**
     * Method moveResources adds resources to the Strongbox
     *
     * @param res of type pick - Resource
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
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
