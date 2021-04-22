package it.polimi.ingsw.server.accessori;

public enum Colours{

    BLUE("SHEILD"),
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
        /*facciamo la richiesta alla view che mostra le risorse tra cui scegliere*/
        System.out.println("Scegli la risorsa"); /* DA IMPLEMENTARE */
        /*la view parla al controller*/
        /*riceve scelta dal controller*/
        choice = Colours.BLUE;
        return choice;
    }
}
