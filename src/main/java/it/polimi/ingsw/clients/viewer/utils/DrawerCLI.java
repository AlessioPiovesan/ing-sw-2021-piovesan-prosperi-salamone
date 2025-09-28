package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record DrawerCLI(CLI ui){

    /**
     * Method showWarehouse is used to print out a reminder of the situation of the warehouse in case the player
     * needs it to make his decision.
     *
     * @param content is all the resources in warehouse
     */
    public void showWarehouse(List<Pick> content){
        printLine();
        ui.printMessage("At the moment the situation with the warehouse is this:");
        var i = 0;
        for(var pick : content)
            if(pick.resource() == Colours.WHITE){
                ui.printMessage("This shelf is empty");
                ++i;
            }
            else{
                var colour = pick.resource();
                var qnt = pick.qnt();
                ui.printMessage("On shelf #" + ++i + " there are " + qnt + " " + Colours.getColoredType(colour) + " resources");
            }
        printLine();
    }

    private void printLine(){
        ui.printMessage("_________________________________________________");
    }

    /**
     * Method showLibrary prints the situation of the Library
     *
     * @param slots all the deck of cards of a certain player
     */
    public void showLibrary(List<ArrayList<CardDev>> slots){
        printLine();
        ui.printMessage("These are your board slots:");
        var i = 0;
        for(var thisSlot : slots)
            if(thisSlot.isEmpty()){
                ui.printMessage("This slot is empty");
                i++;
            }
            else{
                ui.printMessage("On slot #" + ++i + " there are this cards:");
                for(var card : thisSlot)
                    showCard(card);
            }
        printLine();
    }

    /**
     * Method showCard shows a card;
     *
     * @param card is a card
     */
    public void showCard(Card card){
        printLine();
        ui.printMessage(card.toString());
        printLine();
    }

    /**
     * Method showStrongbox prints the situation of the Strongbox
     *
     * @param map contains all the resources in the strongbox
     */
    public void showStrongBox(Map<Colours, Integer> map){
        printLine();
        ui.printMessage("This is what's in your StrongBox:");
        map.keySet().stream().map(colour -> Colours.getColoredType(colour) + " -> " + map.get(colour)).forEach(ui::printMessage);
        printLine();
    }

    /**
     * Method showMyCardLeader shows all of the Leader Cards owned by one player
     *
     * @param cards is a leader card
     */
    public void showMyCardLeader(CardLeader[] cards){
        printLine();
        ui.printMessage("These are your leader cards:");
        for(var singleCard : cards) ui.printMessage(singleCard.toString());
        printLine();
    }

    /**
     * Method showFaithTrack builds and prints the updated faith track of the game
     */
    public void showFaithTrack(Checker checker, int pos){
        var normal = "███";
        var central = ConsoleColors.RED_BOLD + "███" + ConsoleColors.RESET;
        var top = "█" + ConsoleColors.RED_BOLD + "█" + ConsoleColors.RESET + "█";
        var trackTop = new StringBuilder();
        var trackCen = new StringBuilder();
        var trackN = new StringBuilder();
        for(var i = 0; i < 25; i++){
            drawFTBoxNumber(trackN, i);
            if(checker.isPopeFavor(i)){
                drawFTBox(trackTop, ConsoleColors.YELLOW_BOLD_BRIGHT, trackCen);
            }
            if(i == pos){drawFTBox(trackTop.append(top), "\t", trackCen.append(central));}
            else{
                drawFTBox(trackTop.append(normal), "\t", trackCen.append(normal));
            }
            drawFTBox(trackTop, ConsoleColors.RESET, trackCen);
        }
        ui.printMessage(String.valueOf(trackN));
        ui.printMessage(String.valueOf(trackTop));
        ui.printMessage(String.valueOf(trackCen));
        ui.printMessage(String.valueOf(trackTop));
    }

    /**
     * Method drawFTBoxNumber is used to indicate the number of the tile.
     *
     * @param trackN is the tile's Number on the track
     * @param i      is a param of int type
     */
    private void drawFTBoxNumber(StringBuilder trackN, int i){
        if(i < 10) trackN.append("██").append(i).append("\t");
        else trackN.append("█").append(i).append("\t");
    }

    private void drawFTBox(StringBuilder append, String s, StringBuilder append2){
        append.append(s);
        append2.append(s);
    }

    /**
     * Method showFaithTrackSingle is used in case the game is in single player mode.
     * On the faith track there will be Lorenzo's pawn too.
     */
    public void showFaithTrackSingle(Checker checker){
        final var pos = getLocalGameController().getBoardController().player().getPositionFT();
        final var posL = getLocalGameController().getBoardController().lorenzo().getPositionFT();
        var normal = "███";
        var central = ConsoleColors.RED_BOLD + "███" + ConsoleColors.RESET;
        var top = "█" + ConsoleColors.RED_BOLD + "█" + ConsoleColors.RESET + "█";
        var centralL = ConsoleColors.BLACK_BOLD + "XXX" + ConsoleColors.RESET;
        var topL = "█" + ConsoleColors.BLACK_BOLD + "X" + ConsoleColors.RESET + "█";
        var trackTop = new StringBuilder();
        var trackCen = new StringBuilder();
        var trackN = new StringBuilder();
        for(var i = 0; i < 25; i++){
            drawFTBoxNumber(trackN, i);
            if(checker.isPopeFavor(i)){
                drawFTBox(trackTop, ConsoleColors.YELLOW_BOLD_BRIGHT, trackCen);
            }
            if(i == pos){
                if(pos == posL) drawYouAndLorenzoBoxFT(checker.isPopeFavor(i), trackTop, trackCen);
                else{
                    drawFTBox(trackTop.append(top), "\t", trackCen.append(central));
                }
            }
            else if(i == posL) drawYourBoxFTSingle(checker.isPopeFavor(i), centralL, topL, trackTop, trackCen);
            else{
                drawFTBox(trackTop.append(normal), "\t", trackCen.append(normal));
            }
            if(checker.isPopeFavor(i)){
                drawFTBox(trackTop, ConsoleColors.RESET, trackCen);
            }
        }
        ui.printMessage(String.valueOf(trackN));
        ui.printMessage(String.valueOf(trackTop));
        ui.printMessage(String.valueOf(trackCen));
        ui.printMessage(String.valueOf(trackTop));
    }

    private LocalGameController getLocalGameController(){
        return LocalGameController.getInstance();
    }

    /**
     * Method drawYouAndLorenzoBoxFT handles the moment the player and Lorenzo are on the same tile
     *
     * @param pf       indicates if is a pope favour tile
     * @param trackTop indicates three top and three bottom cells of the tile where the player is
     * @param trackCen indicates three central cells of the tile where the player is
     */
    private void drawYouAndLorenzoBoxFT(boolean pf, StringBuilder trackTop, StringBuilder trackCen){
        if(pf){
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.RED_BOLD, ConsoleColors.YELLOW_BOLD_BRIGHT);
        }
        else{
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.RED_BOLD, ConsoleColors.RESET);
        }
        trackCen.append(ConsoleColors.RED_BOLD + "XXX" + ConsoleColors.RESET).append("\t");
    }

    /**
     * Method drawYourBoxFTSingle draws a red single player's box for his pawn
     *
     * @param pf       indicates if is a pope favour tile
     * @param centralL indicates Lorenzo's three central cells of the tile
     * @param topL     indicates Lorenzo's three top and three bottom cells of the tile
     * @param trackTop indicates three top and three bottom cells of the tile where the player is
     * @param trackCen indicates three central cells of the tile where the player is
     */
    private void drawYourBoxFTSingle(boolean pf, String centralL, String topL, StringBuilder trackTop, StringBuilder trackCen){
        if(pf){
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.BLACK_BOLD, ConsoleColors.YELLOW_BOLD_BRIGHT);
        }
        else{
            trackTop.append(topL).append("\t");
        }
        trackCen.append(centralL).append("\t");
    }

    /**
     * Method drawSpecialBoxFTSingle handles the moment a player is in a Pope's Favor Tile
     *
     * @param trackTop         indicates three top and three bottom cells of the tile where the player is
     * @param redBold          colours the player's cross in red
     * @param yellowBoldBright colours the pope favour tile in yellow
     */
    private void drawSpecialBoxFTSingle(StringBuilder trackTop, String redBold, String yellowBoldBright){
        trackTop.append("█").append(redBold).append("X").append(ConsoleColors.RESET).append(yellowBoldBright).append("█").append("\t");
    }

    /**
     * Method showMarket is used to create and print the market in order for the player to visualize it
     *
     * @param marketTray     is the market matrix
     * @param externalMarble is the 13th marble
     */
    public void showMarket(Colours[][] marketTray, Colours externalMarble){
        printLine();
        var print = new StringBuilder();
        print.append(ConsoleColors.BLACK + "   \t   \t   \t" + ConsoleColors.RESET).append(drawMarket(externalMarble)).append("\n");
        for(var i = 0; i < Constants.XMARKET.getVal(); i++){
            for(var j = 0; j < Constants.YMARKET.getVal(); j++){
                print.append(drawMarket(marketTray[i][j])).append("\t");
            }
            print.append("\n");
        }
        ui.printMessage(String.valueOf(print));
        printLine();
    }

    /**
     * Method drawMarket prints the market graphically on the Command Line Interface
     *
     * @param colours all the possible colours on the game
     * @return a colour
     */
    private String drawMarket(Colours colours){
        return switch(colours){
            case RED -> ConsoleColors.RED + "███" + ConsoleColors.RESET;
            case BLUE -> ConsoleColors.BLUE + "███" + ConsoleColors.RESET;
            case GREY -> ConsoleColors.BLACK_BRIGHT + "███" + ConsoleColors.RESET;
            case WHITE -> ConsoleColors.WHITE + "███" + ConsoleColors.RESET;
            case YELLOW -> ConsoleColors.YELLOW + "███" + ConsoleColors.RESET;
            case PURPLE -> ConsoleColors.PURPLE + "███" + ConsoleColors.RESET;
        };
    }
}
