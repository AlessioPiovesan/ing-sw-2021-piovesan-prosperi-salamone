package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
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
            case 2 -> slotCards.get(slotCards.size() - 1).getLvl() == 1;
            case 3 -> slotCards.get(slotCards.size() - 1).getLvl() == 2;
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
        return response.charAt(1) == 'C' || response.charAt(1) == 'G' || response.charAt(1) == 'P' || response.charAt(1) == 'Y';
    }

    private boolean isThirdCharOk(String response){
        return response.charAt(2) == '1' || response.charAt(2) == '2' || response.charAt(2) == '3';
    }

    public boolean isPopeFavor(int i){
        return i == 5 || i == 6 || i == 7 || i == 8 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16 || i == 19 || i == 20 || i == 21 || i == 22 || i == 23 || i == 24;
    }

    /**
     * method canBuyAnyCardDev checks if a player can buy a Development Card
     *
     * @param cards
     * @return
     */
    public List<Card> canBuyAnyCardDev(List<CardDev> cards, LocalGameController gameController){
        var boardController = gameController.getBoardController();
        var player = boardController.player();
        var resAll = player.getResAll();
        var list = new LinkedList<Card>();
        for(var card : cards){
            var i = 0;
            for(var cost : card.getCost()){
                var costColor = cost.resource();
                var costQnt = cost.qnt();
                var availability = resAll.get(costColor);
                if(costQnt <= availability) i++;
            }
            if(i == card.getCost().length) list.add(card);
        }
        return list;
    }

    /**
     * method canUSeAnyCardDev checks if a player can use a Development Card in the production
     *
     * @param cards
     * @return
     */
    public List<Card> canUseAnyCardDev(List<CardDev> cards, LocalGameController gameController){
        var boardController = gameController.getBoardController();
        var player = boardController.player();
        var resAll = player.getResAll();
        var list = new LinkedList<Card>();
        var card = cards.get(cards.size() - 1);
        var i = 0;
        for(var cost : card.getResFrom())
            if(cost.qnt() <= resAll.get(cost.resource())) i++;
        if(i == card.getResFrom().length) list.add(card);
        return list;
    }

    /**
     * method canUseAnyCardLeader checks if a player can activate a Leader's power
     *
     * @param cards
     * @return
     */
    public List<Card> canUseAnyCardLeader(CardLeader[] cards, LocalGameController gameController){
        var boardController = gameController.getBoardController();
        var player = boardController.player();
        var resAll = player.getResAll();
        var slots = player.checkLibraryStatus();
        var list = new LinkedList<Card>();
        for(var card : cards)
            for(var req : card.getReq()){
                if(req.getClass() == Requirements.RequirementsResources.class)
                    checkOnRequiredRes(resAll, list, card, (Requirements.RequirementsResources)req);
                if(req.getClass() == Requirements.RequirementsCard.class)
                    checkOnRequiredCard(slots, list, card, (Requirements.RequirementsCard)req);
            }
        return list;
    }

    /**
     * method checkOnRequiredRes is another check on required Resources
     *
     * @param resAll
     * @param list
     * @param card
     * @param req
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
     */
    private void checkOnRequiredRes(Map<Colours, Integer> resAll, LinkedList<Card> list, CardLeader card, Requirements.RequirementsResources req){
        var colorRequired = req.getColour();
        var qntRequired = req.getQnt();
        var qntAvailable = (int)resAll.get(colorRequired);
        if(qntRequired <= qntAvailable) list.add(card);
    }

    /**
     * method checkOnRequiredCard checks if a player matches Card's Requirements
     *
     * @param slots
     * @param list
     * @param card
     * @param req
     * @author Alessio Piovesan, Pier Giorgio Prosperi, Matteo Salamone
     */
    private void checkOnRequiredCard(List<ArrayList<CardDev>> slots, LinkedList<Card> list, CardLeader card, Requirements.RequirementsCard req){
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
