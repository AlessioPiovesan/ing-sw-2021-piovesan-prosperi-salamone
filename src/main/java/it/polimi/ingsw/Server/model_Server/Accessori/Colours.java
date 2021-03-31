package it.polimi.ingsw.Server.model_Server.Accessori;

public enum Colours{
    BLUE ("SHEILD"), GREEN (null), GREY ("STONE"), PURPLE ("SERVANT"), RED ("FAITH"), WHITE (""), YELLOW ("COIN");
    final String type;

    Colours (String type){
        this.type = type;
    }

    public String getType (){
        return type;
    }

    public Colours selectPref(){
        Colours choice=Colours.BLUE;
        System.out.println ("Scegli la risorsa");
        return choice;
    }
}
