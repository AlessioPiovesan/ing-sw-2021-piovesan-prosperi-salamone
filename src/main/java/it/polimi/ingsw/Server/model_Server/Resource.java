package it.polimi.ingsw.Server.model_Server;

public class Resource{

    Colours colour;

    Resource (Colours color){
        this.colour = color;
    }

    public Colours getType (Colours color){
        return Colours.valueOf (color.toString ());
    }
}
