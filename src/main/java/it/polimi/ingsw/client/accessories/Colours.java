package it.polimi.ingsw.client.accessories;

public enum Colours {

    BLUE("SHEILD"),
    GREY("STONE"),
    PURPLE("SERVANT"),
    RED("FAITH"),
    WHITE(""),
    YELLOW("COIN");

    final String type;

    Colours(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public it.polimi.ingsw.server.accessories.Colours selectPref() {
        it.polimi.ingsw.server.accessories.Colours choice = it.polimi.ingsw.server.accessories.Colours.BLUE;
        /*facciamo la richiesta alla view che mostra le risorse tra cui scegliere*/
        System.out.println("Scegli la risorsa"); /* DA IMPLEMENTARE */
        /*la view parla al controller*/
        /*riceve scelta dal controller*/
        return choice;
    }

    public Colours fromString(String color) {
        return switch (color.toUpperCase()) {
            case "SHEILD", "BLUE" -> BLUE;
            case "STONE", "GREY" -> GREY;
            case "SERVANT", "PURPLE" -> PURPLE;
            case "FAITH", "RED" -> RED;
            case "", "WHITE" -> WHITE;
            case "COIN", "YELLOW" -> YELLOW;
            default -> throw new IllegalStateException("Unexpected value: " + color.toUpperCase());
        };
    }
}
