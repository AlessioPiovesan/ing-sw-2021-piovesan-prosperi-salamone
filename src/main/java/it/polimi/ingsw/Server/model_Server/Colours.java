package it.polimi.ingsw.Server.model_Server;

enum Colours{
    BLUE ("SHEILD"), GREEN (null), GREY ("STONE"), PURPLE ("SERVANT"), RED ("FAITH"), WHITE (""), YELLOW ("COIN");
    final String type;

    Colours (String type){
        this.type = type;
    }

    public String getType (Colours c){
        return c.type;
    }
}
