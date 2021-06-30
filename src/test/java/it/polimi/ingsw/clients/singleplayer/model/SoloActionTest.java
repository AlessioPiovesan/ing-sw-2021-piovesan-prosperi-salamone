package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.utils.ConsoleColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SoloActionTest{

    @Test
    void action1(){
        var game = LocalGameController.getInstance().getGame();
        for(var sa : SoloAction.values()){
            if(!(sa == SoloAction.AS5 || sa == SoloAction.AS6 || sa == SoloAction.AS7)){
                var oldNum = game.getCardDevDeck().size();
                SoloAction.action(sa);
                var newNum = game.getCardDevDeck().size();
                assertEquals(oldNum - 2, newNum);
            }
        }
    }

    @Test
    void action2(){
        for(var sa : SoloAction.values()){
            var lorenzo = LocalGameController.getInstance().getBoardController().lorenzo();
            if(sa == SoloAction.AS5){
                SoloAction.action(sa);
                assertEquals(2, lorenzo.getPositionFT());
            }
            if(sa == SoloAction.AS6){
                SoloAction.action(sa);
                assertEquals(4, lorenzo.getPositionFT());
            }
            if(sa == SoloAction.AS7){
                SoloAction.action(sa);
                assertEquals(5, lorenzo.getPositionFT());
            }
        }
    }

    @Test
    void shuffle(){
        var list1 = SoloAction.shuffle();
        var list2 = SoloAction.shuffle();
        assertNotEquals(list1, list2);
    }

    @Test
    void printSASingle(){
        assertEquals(ConsoleColors.BLACK_BACKGROUND_BRIGHT + "Lorenzo take 2 GREEN Development Cards" + ConsoleColors.RESET, SoloAction.printSASingle(SoloAction.AS1));
    }

    @Test
    void printSAString(){
        assertEquals("Lorenzo take 2 GREEN Development Cards", SoloAction.printSAString(SoloAction.AS1));
    }

    @Test
    void pickSoloAction(){
        var list = SoloAction.shuffle();
        var old = list.get(1);
        SoloAction.pickSoloAction(list);
        assertEquals(old, list.get(0));
    }
}