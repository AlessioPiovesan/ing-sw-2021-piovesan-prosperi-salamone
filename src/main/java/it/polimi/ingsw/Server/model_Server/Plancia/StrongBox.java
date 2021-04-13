package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Pick;

import java.util.HashMap;

class StrongBox{

    HashMap<Colours, Integer> comp;

    StrongBox ( ){
        comp = new HashMap<>();
        comp.put(Colours.BLUE, 0);
        comp.put(Colours.GREY, 0);
        comp.put(Colours.PURPLE, 0);
        comp.put(Colours.YELLOW, 0);
    }

    protected void addResources (Pick res){

        comp.replace(res.getR(), comp.get(res) + res.getQnt());
    }

    public void checkStatus ( ){
        System.out.println(comp);
    }
}
