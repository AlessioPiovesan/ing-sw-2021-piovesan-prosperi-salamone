package it.polimi.ingsw.server.accessories;

import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public enum Colours{

    BLUE("SHIELD"), GREY("STONE"), PURPLE("SERVANT"), RED("FAITH"), WHITE(""), YELLOW("COIN");

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

    /**
     * Method Colours associates the correct colours from a string
     *
     * @param color is a color
     * @return a color
     */
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

    /**
     * Method getColoredType creates a string where the resource name is colorized
     *
     * @param colour is a colour
     * @return the resource name colorized
     */
    public static String getColoredType(Colours colour){
        return switch(colour){
            case BLUE -> ConsoleColors.BLUE + colour.getType() + ConsoleColors.RESET;
            case GREY -> ConsoleColors.BLACK_BRIGHT + colour.getType() + ConsoleColors.RESET;
            case PURPLE -> ConsoleColors.PURPLE + colour.getType() + ConsoleColors.RESET;
            case RED -> ConsoleColors.RED + colour.getType() + ConsoleColors.RESET;
            case WHITE -> colour.getType();
            case YELLOW -> ConsoleColors.YELLOW + colour.getType() + ConsoleColors.RESET;
        };
    }

    public String getType(){
        return type;
    }
}
