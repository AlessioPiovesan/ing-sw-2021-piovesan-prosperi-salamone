package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.server.accessories.Colours;

public class DrawerCLI{

    /**
     * method srawFTBoxNumer is used to indicate the number of the tile.
     *
     * @param trackN
     * @param i
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
     */
    public void drawFTBoxNumber(StringBuilder trackN, int i){
        if(i < 9) trackN.append("██").append(i).append("\t");
        else trackN.append("█").append(i).append("\t");
    }

    public void drawFTBox(StringBuilder append, String s, StringBuilder append2){
        append.append(s);
        append2.append(s);
    }

    /**
     * method drawYourBoxFTSingle draws a red single player's box for his pawn
     *
     * @param pf
     * @param centralL
     * @param topL
     * @param trackTop
     * @param trackCen
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
     */
    public void drawYourBoxFTSingle(boolean pf, String centralL, String topL, StringBuilder trackTop, StringBuilder trackCen){
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
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
     */
    public void drawSpecialBoxFTSingle(StringBuilder trackTop, String redBold, String yellowBoldBright){
        trackTop.append("█").append(redBold).append("X").append(yellowBoldBright).append("█").append("\t");
    }

    /**
     * method drawYouAndLorenzoBoxFT handles the moment the player and Lorenzo are on the same tile
     *
     * @param pf
     * @param trackTop
     * @param trackCen
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
     */
    public void drawYouAndLorenzoBoxFT(boolean pf, StringBuilder trackTop, StringBuilder trackCen){
        if(pf){
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.RED_BOLD, ConsoleColors.YELLOW_BOLD_BRIGHT);
        }
        else{
            drawSpecialBoxFTSingle(trackTop, ConsoleColors.RED_BOLD, ConsoleColors.RESET);
        }
        trackCen.append(ConsoleColors.RED_BOLD + "XXX" + ConsoleColors.RESET).append("\t");
    }

    /**
     * method drawMarket prints the market graphically on the Command Line Interface
     *
     * @param colours
     * @return
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
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
