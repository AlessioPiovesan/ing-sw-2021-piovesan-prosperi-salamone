package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Checker{

    private final UI ui;
    private final Scanner input;

    public Checker(UI ui){
        this.ui = ui;
        input = new Scanner(System.in);
    }

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection){
        List<T> result = new ArrayList<>(rawCollection.size());
        for(Object o : rawCollection){
            try{
                result.add(clazz.cast(o));
            } catch(ClassCastException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * The method isAddable checks if the player can actually store the card since you cannot store a level 2 or level three card
     * if there is not an inferior level card already on the slot
     *
     * @param card
     * @param slot
     * @return
     */
    public boolean isAddable(LocalGameController gameController, CardDev card, int slot){
        var slotCards = gameController.getBoardController().player().checkLibraryStatus().get(slot - 1);
        if(slotCards.isEmpty()) return card.getLvl() == 1;
        else return switch(card.getLvl()){
            case 2 -> slotCards.get(slotCards.size() - 1).getLvl() != 1;
            case 3 -> slotCards.get(slotCards.size() - 1).getLvl() != 2;
            default -> false;
        };
    }

    /**
     * method getCorrectLeaderCardCode is used to check if the two digit code typed by the user for the leader cards is correct.
     *
     * @param response
     * @return
     */
    public Card getCorrectLeaderCardCode(String response){
        try{
            var code = Integer.parseInt(response);
            while(code < 1 || code > 16){
                ui.printMessage("Not a correct code");
                response = input.next().toUpperCase();
                code = Integer.parseInt(response);
            }
        } catch(NumberFormatException e){
            ui.printMessage("Not even a number");
            response = input.next().toUpperCase();
            getCorrectDevCardCode(response);
        }
        return CardLeader.getSpecificCard(response);
    }

    /**
     * method getCorrectDevCardCode is used to check if the code typed by the user for the Dev Card is correct.
     *
     * @param response
     * @return
     */
    public Card getCorrectDevCardCode(String response){
        while(!(response.length() == 3 && isFirstCharOk(response) && isSecondCharOk(response) && isThirdCharOk(response))){
            ui.printMessage("Not a correct code\nTry again");
            response = input.next().toUpperCase();
        }
        return CardDev.getSpecificCard(response);
    }

    private boolean isFirstCharOk(String response){
        return response.charAt(0) == '1' || response.charAt(0) == '2' || response.charAt(0) == '3' || response.charAt(0) == '4';
    }

    private boolean isSecondCharOk(String response){
        return response.charAt(1) == 'B' || response.charAt(1) == 'G' || response.charAt(1) == 'P' || response.charAt(1) == 'Y';
    }

    private boolean isThirdCharOk(String response){
        return response.charAt(2) == '1' || response.charAt(2) == '2' || response.charAt(2) == '3';
    }

    public boolean isPopeFavor(int i){
        return i == 5 || i == 6 || i == 7 || i == 8 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || i == 19 || i == 20 || i == 21 || i == 22 || i == 23 || i == 24;
    }
}
