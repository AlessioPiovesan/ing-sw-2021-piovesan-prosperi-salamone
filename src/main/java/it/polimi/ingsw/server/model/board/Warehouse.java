package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Warehouse describes the container where a player can store his resources.
 * It is composed by three shelves where the first one can store only one resource while
 * the second shelf has to have two resources of the same type
 * and the third one contains three resources of the same type.
 *
 * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
 */
public class Warehouse{

    private final ArrayList<Pick> wareContent;

    /**
     * Constructor Warehouse sets every shelf's values to default value which is colour: WHITE and quantity: 0
     *
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    public Warehouse(){
        wareContent = new ArrayList<>();
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
    }

    protected List<Pick> getWareContent(){
        return wareContent;
    }

    /**
     * Method moveResources is used to place a resource in a desired shelf
     *
     * @param lvl of type int - indicates the shelf where the resource is going to be stored
     * @param add of type Pick - the resource being added
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    protected void moveResources(int lvl, Pick add){
        int qntNow = wareContent.get(lvl).qnt();
        wareContent.set(lvl, new Pick(add.resource(), add.qnt() + qntNow));
        checkIfEmptied();
    }

    /**
     * Method checkIfEmptied is used to check if the shelf is empty.
     * If it is empty our default "empty" value is colour: WHITE and quantity: 0
     *
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    private void checkIfEmptied(){
        for(var i = 0; i < 3; i++)
            if(wareContent.get(i).qnt() == 0) wareContent.set(i, new Pick(Colours.WHITE, 0));
    }

    /**
     * Method checkAdd checks if a resource can be added in the Warehouse
     *
     * @param add of type Pick - resource
     * @return boolean
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see #isThereThatRes(Colours)
     */
    protected boolean checkAdd(Pick add){
        if(add.qnt() < 4){
            int lvl = isThereThatRes(add.resource());
            if(lvl > 0) return ((checkLvlStatus(lvl).qnt() + add.qnt()) <= lvl + 1);
            return isThereThatRes(Colours.WHITE) >= 0;
        }
        return false;
    }

    /**
     * Method isThereThatRes checks if a resource is in the warehouse,
     * and if there is one, it can tell on which shelf is stored
     *
     * @param color of type Colours
     * @return i of type int - shelf or "-1" if the resource is not in the warehouse
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    protected int isThereThatRes(Colours color){
        for(var i = 0; i < wareContent.size(); i++){
            var pick = wareContent.get(i);
            if(pick.resource() == color) return i;
        }
        return -1;
    }

    /**
     * Method Pick describes what is stored in a shelf
     *
     * @param lvl of type int - shelf
     * @return of type Pick - resources on the shelf
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see Pick#Pick(Colours, int)
     */
    protected Pick checkLvlStatus(int lvl){
        return wareContent.get(lvl);
    }

    /**
     * Method checkAdd checks if a resource can be stored in a specified shelf
     *
     * @param lvl of type int - number of shelf where you want to store the resource
     * @param add of type Pick - colour and quantity
     * @return boolean
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     * @see #isThereThatRes(Colours)
     * @see #checkLvlStatus(int)
     */
    protected boolean checkAdd(int lvl, Pick add){
        if(add.qnt() < 4){
            var i = isThereThatRes(add.resource());
            var j = checkLvlStatus(lvl);
            if(lvl == i) return ((j.qnt() + add.qnt()) <= (lvl + 1));
            else if(i > -1) return false;
            else return j.resource() == Colours.WHITE;
        }
        return false;
    }

    /**
     * Method lvlExchange is used to switch resources from two different shelves
     *
     * @param first  of type int - first shelf
     * @param second of type int - second shelf
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    protected void lvlExchange(int first, int second){
        var buffer = wareContent.get(first);
        wareContent.set(first, wareContent.get(second));
        wareContent.set(second, buffer);
    }

    protected void addLeaderSpace(Colours color){
        switch(color){
            case GREY -> wareContent.add(new Pick(Colours.GREY, 0));
            case PURPLE -> wareContent.add(new Pick(Colours.PURPLE, 0));
            case BLUE -> wareContent.add(new Pick(Colours.BLUE, 0));
            case YELLOW -> wareContent.add(new Pick(Colours.YELLOW, 0));
            default -> checkStatus();

        }
    }

    /**
     * Method checkStatus returns the status of the Warehouse
     *
     * @return status of the Warehouse
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    protected List<Pick> checkStatus(){
        var status = new ArrayList<Pick>();
        for(var i = 0; i < wareContent.size(); i++){
            status.add(i, checkLvlStatus(i));
        }
        return status;
    }
}
