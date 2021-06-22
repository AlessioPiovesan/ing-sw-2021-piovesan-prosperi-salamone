package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.CLI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;
import it.polimi.ingsw.server.accessories.cards.Card;

public class DrawerCLI{

    private final CLI ui;

    public DrawerCLI(CLI ui){
        this.ui = ui;
    }

    /**
     * method showWarehouse is used to print out a reminder of the situation of the warehouse in case the player
     * needs it to make his decision.
     */
    public void showWarehouse(){
        printLine();
        ui.printMessage("At the moment the situation with the warehouse is this:");
        var boardController = getLocalGameController().getBoardController();
        var content = boardController.player().checkWarehouseStatus();
        var i = 0;
        for(var pick : content)
            if(pick.resource() == Colours.WHITE){
                ui.printMessage("This shelf is empty");
                i++;
            }
            else{
                var color = pick.resource();
                var qnt = pick.qnt();
                ui.printMessage("On shelf #" + ++i + " there are " + qnt + " " + color.getType() + " resources");
            }
        printLine();
    }

    private void printLine(){
        ui.printMessage("_________________________________________________");
    }

    private LocalGameController getLocalGameController(){
        return LocalGameController.getInstance();
    }

    /**
     * method showLibrary prints the situation of the Library
     */
    public void showLibrary(){
        printLine();
        ui.printMessage("These are your board slots:");
        var boardController = getLocalGameController().getBoardController();
        var slots = boardController.player().checkLibraryStatus();
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
     * method showCard shows a card;
     *
     * @param card
     */
    public void showCard(Card card){
        printLine();
        ui.printMessage(card.toString());
        printLine();
    }

    /**
     * method showStrongbox prints the situation of the Strongbox
     */
    public void showStrongBox(){
        printLine();
        ui.printMessage("This is what's in your StrongBox:");
        var boardController = getLocalGameController().getBoardController();
        var player = boardController.player();
        var map = player.checkStrongBoxStatus();
        map.keySet().stream().map(color -> color.getType() + " -> " + map.get(color)).forEach(ui::printMessage);
        printLine();
    }

    /**
     * method showMyCardLeader shows all of the Leader Cards owned by one player
     */
    public void showMyCardLeader(){
        printLine();
        ui.printMessage("These are your leader cards:");
        var boardController = getLocalGameController().getBoardController();
        var cards = boardController.player().getMyLeaderCards();
        for(var singleCard : cards) ui.printMessage(singleCard.toString());
        printLine();
    }

    /**
     * method showFaithTrack builds and prints the updated faith track of the game
     */
    public void showFaithTrack(Checker checker){
        final var pos = 0;
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
     * method srawFTBoxNumer is used to indicate the number of the tile.
     *
     * @param trackN
     * @param i
     */
    private void drawFTBoxNumber(StringBuilder trackN, int i){
        if(i < 9) trackN.append("██").append(i).append("\t");
        else trackN.append("█").append(i).append("\t");
    }

    private void drawFTBox(StringBuilder append, String s, StringBuilder append2){
        append.append(s);
        append2.append(s);
    }

    /**
     * method showFaithTrackSingle is used in case the game is in single player mode.
     * On the faith track there will be Lorenzo's pawn too.
     */
    public void showFaithTrackSingle(Checker checker){
        final var pos = getLocalGameController().getBoardController().player().checkFT();
        final var posL = getLocalGameController().getBoardController().lorenzo().checkFT();
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

    /**
     * method drawYouAndLorenzoBoxFT handles the moment the player and Lorenzo are on the same tile
     *
     * @param pf
     * @param trackTop
     * @param trackCen
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
     * method drawYourBoxFTSingle draws a red single player's box for his pawn
     *
     * @param pf
     * @param centralL
     * @param topL
     * @param trackTop
     * @param trackCen
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
     * method drawSpecialBoxFTSingle handles the moment a player is in a Pope's Favor Tile
     *
     * @param trackTop
     * @param redBold
     * @param yellowBoldBright
     */
    private void drawSpecialBoxFTSingle(StringBuilder trackTop, String redBold, String yellowBoldBright){
        trackTop.append("█").append(redBold).append("X").append(yellowBoldBright).append("█").append("\t");
    }

    /**
     * method showMarket is used to create and print the market in order for the player to visualize it
     */
    public void showMarket(){
        printLine();
        var marketTray = getLocalGameController().getGame().getMarket().getTray();
        var externalMarble = getLocalGameController().getGame().getMarket().getExternal();
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
     * method drawMarket prints the market graphically on the Command Line Interface
     *
     * @param colours
     * @return
     */
    public String drawMarket(Colours colours){
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
