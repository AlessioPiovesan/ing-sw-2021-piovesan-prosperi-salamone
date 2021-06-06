package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;

import java.util.ArrayList;
import java.util.List;

public class Warehouse{

    private final ArrayList<Pick> wareContent;

    public Warehouse(){
        wareContent = new ArrayList<>();
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
    }

    protected List<Pick> getWareContent(){
        return wareContent;
    }

    protected void moveResources(int lvl, Pick add){
        /*prima di arrivare qui ho già controllato che posso farlo
         * metto una quantità qnt di risorsa di tipo color nella posizione lvl della lista*/
        int qntNow = wareContent.get(lvl).getQnt();
        wareContent.set(lvl, new Pick(add.getResource(), add.getQnt() + qntNow));
    }

    protected boolean checkAdd(Pick add){
        if(add.getQnt() < 4){
            int lvl = isThereThatRes(add.getResource());
            if(lvl > 0) return ((checkLvlStatus(lvl).getQnt() + add.getQnt()) <= lvl + 1);
            return isThereThatRes(Colours.WHITE) >= 0;
        }
        return false;
    }

    protected int isThereThatRes(Colours color){
        for(var i = 0; i < wareContent.size(); i++){
            if(wareContent.get(i).getResource() == color) return i;
        }
        return -1;
    }

    protected Pick checkLvlStatus(int lvl){
        return new Pick(wareContent.get(lvl).getResource(), wareContent.get(lvl).getQnt());
    }

    protected boolean checkAdd(int lvl, Pick add){
        if(add.getQnt() < 4){
            var i = isThereThatRes(add.getResource());
            var j = checkLvlStatus(lvl);
            if(lvl == i) return ((j.getQnt() + add.getQnt()) <= (lvl + 1));
            else if(i > -1) return false;
            else return j.getResource() == Colours.WHITE;
        }
        return false;
    }

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

    protected List<Pick> checkStatus(){
        var status = new ArrayList<Pick>();
        for(var i = 0; i < wareContent.size(); i++){
            status.add(i, checkLvlStatus(i));
        }
        return status;
    }
}
