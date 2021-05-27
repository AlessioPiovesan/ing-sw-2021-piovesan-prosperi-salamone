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

    public List<Pick> getWareContent(){
        return wareContent;
    }

    public void moveResources(int lvl, Pick add){
        /*prima di arrivare qui ho già controllato che posso farlo
         * metto una quantità qnt di risorsa di tipo color nella posizione lvl della lista*/
        int qntNow = wareContent.get(lvl).getQnt();
        wareContent.set(lvl, new Pick(add.getResource(), add.getQnt() + qntNow));
    }

    public boolean checkAdd(Pick add){
        if(add.getQnt() < 4){
            int lvl = isThereThatRes(add.getResource());
            if(lvl > 0) return ((checkLvlStatus(lvl).getQnt() + add.getQnt()) <= lvl + 1);
            return isThereThatRes(Colours.WHITE) >= 0;
        }
        return false;
    }

    public int isThereThatRes(Colours color){
        for(var i = 0; i < wareContent.size(); i++){
            if(wareContent.get(i).getResource() == color) return i;
        }
        return -1;
    }

    public Pick checkLvlStatus(int lvl){
        return new Pick(wareContent.get(lvl).getResource(), wareContent.get(lvl).getQnt());
    }

    public boolean checkAdd(int lvl, Pick add){
        if(add.getQnt() < 4){
            if(lvl == isThereThatRes(add.getResource()))
                return ((checkLvlStatus(lvl).getQnt() + add.getQnt()) <= lvl + 1);
            return wareContent.get(lvl).getResource() == Colours.WHITE;
        }
        return false;
    }

    public void lvlExchange(int lvlStart, int lvlEnd){
        Pick buffer = wareContent.set(lvlStart, wareContent.get(lvlEnd));
        wareContent.set(lvlEnd, buffer);
    }

    public void addLeaderSpace(Colours color){
        switch(color){
            case GREY -> wareContent.add(new Pick(Colours.GREY, 0));
            case PURPLE -> wareContent.add(new Pick(Colours.PURPLE, 0));
            case BLUE -> wareContent.add(new Pick(Colours.BLUE, 0));
            case YELLOW -> wareContent.add(new Pick(Colours.YELLOW, 0));
            default -> checkStatus();

        }
    }

    public List<Pick> checkStatus(){
        var status = new ArrayList<Pick>();
        for(var i = 0; i < wareContent.size(); i++){
            status.add(i, checkLvlStatus(i));
        }
        return status;
    }
}
