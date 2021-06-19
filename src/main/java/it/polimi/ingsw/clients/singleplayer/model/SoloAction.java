package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import it.polimi.ingsw.server.accessories.ColoursCards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum SoloAction{

    AS1, AS2, AS3, AS4, AS5, AS6, AS7;

    private static final Random random = new Random();

    public static void action(SoloAction sa){
        switch(sa){
            case AS1 -> LocalGameController.getInstance().tossCardDev(ColoursCards.GREEN);
            case AS2 -> LocalGameController.getInstance().tossCardDev(ColoursCards.YELLOW);
            case AS3 -> LocalGameController.getInstance().tossCardDev(ColoursCards.CYAN);
            case AS4 -> LocalGameController.getInstance().tossCardDev(ColoursCards.VIOLET);
            case AS5, AS6 -> LocalGameController.getInstance().getBoardController().lorenzo().moveFT(2);
            case AS7 -> {
                LocalGameController.getInstance().getBoardController().lorenzo().moveFT(1);
                shuffle();
            }
        }
    }

    public static List<SoloAction> shuffle(){
        var remain = new ArrayList<Integer>();
        var start = new ArrayList<>(Arrays.asList(SoloAction.values()));
        var heap = new ArrayList<SoloAction>();
        while(heap.size() < start.size()){
            var found = false;
            SoloAction s = null;
            while(!found){
                var i = random.nextInt(start.size());
                if(!(remain.contains(i))){
                    remain.add(i);
                    found = true;
                    s = start.get(i);
                }
            }
            heap.add(s);
        }
        return heap;
    }

    public static String printSASingle(SoloAction sa){
        return ConsoleColors.BLACK_BACKGROUND_BRIGHT + printSAString(sa) + ConsoleColors.RESET;
    }

    public static String printSAString(SoloAction sa){
        return switch(sa){
            case AS1 -> "Lorenzo take 2 GREEN Development Cards";
            case AS2 -> "Lorenzo take 2 YELLOW Development Cards";
            case AS3 -> "Lorenzo take 2 CYAN Development Cards";
            case AS4 -> "Lorenzo take 2 VIOLET Development Cards";
            case AS5, AS6 -> "Lorenzo's Black Cross token goes 2 spaces forward";
            case AS7 -> "Lorenzo's Black Cross token goes 1 spaces forward and a new stack of the Solo Actions is created";
        };
    }

    public static void pickSoloAction(List<SoloAction> list){
        var sa = list.get(0);
        list.remove(sa);
        list.add(sa);
    }
}
