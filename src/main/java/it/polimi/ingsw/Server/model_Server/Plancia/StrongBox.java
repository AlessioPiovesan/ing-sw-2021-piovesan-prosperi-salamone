package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;

import java.util.HashMap;

class StrongBox{

    HashMap<Colours, Integer> comp;

    StrongBox ( ){
        comp = new HashMap<> ();
        comp.put (Colours.BLUE, 0);
        comp.put (Colours.GREY, 0);
        comp.put (Colours.PURPLE, 0);
        comp.put (Colours.YELLOW, 0);
    }

    protected void addResources (Colours res, int add){
        comp.replace (res, comp.get (res) + add);
    }

    public void checkStatus ( ){
        System.out.println(comp);
    }
}
