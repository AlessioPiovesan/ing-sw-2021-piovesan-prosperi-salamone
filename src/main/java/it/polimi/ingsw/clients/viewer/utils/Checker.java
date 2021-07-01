package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.clients.viewer.UI;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Requirements;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.*;

public class Checker{

    private final UI ui;
    private final Scanner input;

    public Checker(UI ui){
        this.ui = ui;
        input = new Scanner(System.in);
    }

    /**
     * method getCorrectLeaderCardCode is used to check if the two digit code typed by the user for the leader cards is correct.
     *
     * @param response is a message to the server
     * @return the selected card from the player
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
     * @param response is a message to the server
     * @return the selected card from the player
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
        return response.charAt(1) == 'C' || response.charAt(1) == 'G' || response.charAt(1) == 'P' || response.charAt(1) == 'Y';
    }

    private boolean isThirdCharOk(String response){
        return response.charAt(2) == '1' || response.charAt(2) == '2' || response.charAt(2) == '3';
    }

    /**
     * The method isAddable checks if the player can actually store the card since you cannot store a level 2 or level three card
     * if there is not an inferior level card already on the slot
     *
     * @param card is a development card
     * @param slot is the number of remainig development cards
     * @return a boolean
     */
    public boolean isAddable(CardDev card, int slot, List<ArrayList<CardDev>> lib){
        var slotCards = lib.get(slot - 1);
        if(slotCards.isEmpty()) return card.getLvl() == 1;
        else return switch(card.getLvl()){
            case 2 -> slotCards.get(slotCards.size() - 1).getLvl() == 1;
            case 3 -> slotCards.get(slotCards.size() - 1).getLvl() == 2;
            default -> false;
        };
    }

    public boolean isPopeFavor(int i){
        return i == 5 || i == 6 || i == 7 || i == 8 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || i == 19 || i == 20 || i == 21 || i == 22 || i == 23 || i == 24;
    }

    /**
     * method canUSeAnyCardDev checks if a player can use a Development Card in the production
     *
     * @param cards is a Development Card
     * @return the remaining list of Development Cards
     */
    public List<Card> canUseAnyCardDev(List<CardDev> cards, Map<Colours, Integer> resAll){
        var list = new LinkedList<Card>();
        var card = cards.get(cards.size() - 1);
        var i = 0;
        for(var cost : card.getResFrom())
            if(cost.qnt() <= resAll.get(cost.resource())) i++;
        if(i == card.getResFrom().length) list.add(card);
        return list;
    }


    /**
     * method checkOnRequiredRes is another check on required Resources
     *
     * @param resAll is all the player's resources
     * @param list   is all the player's leader cards
     * @param card   is the card the player is trying to activate
     * @param req    is the required resources to activate the leader cards
     */
    public void checkOnRequiredRes(Map<Colours, Integer> resAll, List<CardLeader> list, CardLeader card, Requirements.RequirementsResources req){
        var colorRequired = req.getColour();
        var qntRequired = req.getQnt();
        var qntAvailable = (int)resAll.get(colorRequired);
        if(qntRequired <= qntAvailable) list.add(card);
    }

    /**
     * method checkOnRequiredCard checks if a player matches Card's Requirements
     *
     * @param slots is the full deck of cards
     * @param list  is all the player's leader cards
     * @param card  is the card the player is trying to activate
     * @param req   is the required resources to activate the leader cards
     */
    public void checkOnRequiredCard(List<ArrayList<CardDev>> slots, List<CardLeader> list, CardLeader card, Requirements.RequirementsCard req){
        var colorRequired = req.getColour();
        var lvlRequired = req.getCardLvl();
        for(var slot : slots){
            for(var thisCard : slot){
                if(thisCard != null){
                    var colorCard = thisCard.getColour();
                    var lvlCard = thisCard.getLvl();
                    if(colorCard == colorRequired && lvlCard <= lvlRequired){
                        list.add(card);
                        break;
                    }
                }
            }
            if(list.contains(card)) break;
        }
    }
}
