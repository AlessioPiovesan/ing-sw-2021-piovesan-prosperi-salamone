package it.polimi.ingsw.client.accessori;

public enum Colours{

    BLUE("SHEILD"),
    GREEN(null),
    GREY("STONE"),
    PURPLE("SERVANT"),
    RED("FAITH"),
    WHITE(""),
    YELLOW("COIN");

    final String type;

    Colours (String type){
        this.type = type;
    }

    public String getType ( ){
        return type;
    }

    public Colours selectPref ( ){
        Colours choice = Colours.WHITE;
        /* DA IMPLEMENTARE */
        /*Mostra le risorse tra cui scegliere e salva la scelta*/
        return choice;
    }
}
