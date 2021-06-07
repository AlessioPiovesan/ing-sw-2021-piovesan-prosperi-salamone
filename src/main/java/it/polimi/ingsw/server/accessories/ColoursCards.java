package it.polimi.ingsw.server.accessories;

import it.polimi.ingsw.clients.viewer.ConsoleColors;

public enum ColoursCards{

    GREEN, CYAN, VIOLET, YELLOW;

    public static String colored(String colour){
        return switch(colour.toUpperCase()){
            case "GREEN" -> ConsoleColors.GREEN + "GREEN" + ConsoleColors.RESET;
            case "CYAN" -> ConsoleColors.CYAN + "CYAN" + ConsoleColors.RESET;
            case "VIOLET" -> ConsoleColors.PURPLE + "VIOLET" + ConsoleColors.RESET;
            case "YELLOW" -> ConsoleColors.YELLOW + "YELLOW" + ConsoleColors.RESET;
            default -> "";
        };
    }

}
