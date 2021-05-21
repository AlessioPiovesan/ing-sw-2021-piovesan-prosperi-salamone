package it.polimi.ingsw.server.accessories;

public enum ColoursCards {
    GREEN,
    CYAN,
    VIOLET,
    YELLOW;

    public static ColoursCards fromString(String colour) {
        return switch (colour.toUpperCase()) {
            case "GREEN" -> GREEN;
            case "CYAN" -> CYAN;
            case "VIOLET" -> VIOLET;
            case "YELLOW" -> YELLOW;
            default -> null;
        };
    }

}
