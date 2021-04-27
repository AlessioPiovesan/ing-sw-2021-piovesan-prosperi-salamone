package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.model_server.carte.CardLeader;

import java.util.ArrayList;
import java.util.List;

public class Warehouse{
    private final ArrayList<Pick> wareContent;

    public Warehouse ( ){
        wareContent = new ArrayList<>();
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
    }

    public List<Pick> getWareContent ( ){
        return wareContent;
    }

    public void setResources (int lvl, Pick add){
        /*prima di arrivare qui ho già controllato che posso farlo
         * metto una quantità qnt di risorsa di tipo color nella posizione lvl della lista*/
        int qntNow = wareContent.get(lvl).getQnt();
        wareContent.set(lvl, new Pick(add.getResource(), add.getQnt() + qntNow));
    }

    public boolean checkAdd (Pick add){
        if (add.getQnt() < 4){
            int lvl = isThereThatRes(add.getResource());
            if (lvl > 0)
                return ((checkLvlStatus(lvl).getQnt() + add.getQnt()) <= lvl + 1);
            return isThereThatRes(Colours.WHITE) >= 0;
        }
        return false;
    }

    public boolean checkAdd (int lvl, Pick add){
        if (add.getQnt() < 4){
            if (lvl == isThereThatRes(add.getResource()))
                return ((checkLvlStatus(lvl).getQnt() + add.getQnt()) <= lvl + 1);
            return wareContent.get(lvl).getResource() == Colours.WHITE;
        }
        return false;
    }

    public int isThereThatRes (Colours color){
        for (var i = 0;i < wareContent.size();i++){
            if (wareContent.get(i).getResource() == color) return i;
        }
        return -1;
    }

    protected void lvlExchange (int lvlStart, int lvlEnd){
        Pick buffer = wareContent.set(lvlStart, wareContent.get(lvlEnd));
        wareContent.set(lvlEnd, buffer);
    }

    public List<Pick> checkStatus ( ){ /* DA IMPLERMENTARE */
        ArrayList<Pick> status = new ArrayList<>();
        for (var i = 0;i < wareContent.size();i++){
            status.add(i, checkLvlStatus(i));
        }
        return status;
    }

    public Pick checkLvlStatus (int lvl){
        //System.out.println("Sullo scaffale " + (lvl + 1) + " ci sono " + wareContent.get(lvl).getQnt() + " risorse di tipo " + wareContent.get(lvl).getResource().getType());
        return new Pick(wareContent.get(lvl).getResource(), wareContent.get(lvl).getQnt());
    }

    public void addLeaderSpace (CardLeader card){
        switch (card){
            case card_Lead5 -> wareContent.add(new Pick(Colours.GREY, 0));
            case card_Lead6 -> wareContent.add(new Pick(Colours.PURPLE, 0));
            case card_Lead7 -> wareContent.add(new Pick(Colours.BLUE, 0));
            case card_Lead8 -> wareContent.add(new Pick(Colours.YELLOW, 0));
            default -> System.out.println("ERROR");
        }
    }

    private boolean isLvlOk (int lvl){
        return wareContent.get(lvl).getQnt() <= lvl;
    }
}
