package it.polimi.ingsw.server.accessories;

import it.polimi.ingsw.clients.viewer.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public enum Colours{

    BLUE((ConsoleColors.BLUE + "SHIELD" + ConsoleColors.RESET)),
    GREY(ConsoleColors.BLACK_BRIGHT + "STONE" + ConsoleColors.RESET),
    PURPLE(ConsoleColors.PURPLE + "SERVANT" + ConsoleColors.RESET),
    RED(ConsoleColors.RED + "FAITH" + ConsoleColors.RESET), WHITE(""),
    YELLOW(ConsoleColors.YELLOW + "COIN" + ConsoleColors.RESET);

    private final String type;

    Colours(String type){
        this.type = type;
    }

    public static List<Colours> valid(){
        var list = new ArrayList<Colours>();
        list.add(BLUE);
        list.add(GREY);
        list.add(PURPLE);
        list.add(YELLOW);
        return list;
    }

    public static Colours fromString(String color){
        return switch(color.toUpperCase()){
            case "SHIELD", "BLUE" -> BLUE;
            case "STONE", "GREY" -> GREY;
            case "SERVANT", "PURPLE" -> PURPLE;
            case "FAITH", "RED" -> RED;
            case "", "WHITE" -> WHITE;
            case "COIN", "YELLOW" -> YELLOW;
            default -> null;
        };
    }

    public String getType(){
        return type;
    }
}
