package it.polimi.ingsw.clients.viewer.utils;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Requirements;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest{

    @Test
    void getCorrectLeaderCardCode(){
        var checker = new Checker(null);
        assertEquals(CardLeader.CARDLEAD01, checker.getCorrectLeaderCardCode("01"));
    }

    @Test
    void getCorrectDevCardCode(){
        var checker = new Checker(null);
        assertEquals(CardDev.CARDDEV_1C1, checker.getCorrectDevCardCode("1C1"));
    }

    @Test
    void isPopeFavor(){
        var checker = new Checker(null);
        assertTrue(checker.isPopeFavor(5));
        assertFalse(checker.isPopeFavor(4));
    }

    @Test
    void canUseAnyCardDev(){
        var checker = new Checker(null);
        var list = new LinkedList<CardDev>();
        list.add(CardDev.CARDDEV_1C1);
        var resAll = new EnumMap<Colours, Integer>(Colours.class);
        resAll.put(Colours.BLUE, 2);
        resAll.put(Colours.YELLOW, 2);
        var newList = checker.canUseAnyCardDev(Utils.castList(CardDev.class, list), resAll);
        assertEquals(list, newList);
    }

    @Test
    void checkOnRequiredRes(){
        var checker = new Checker(null);
        var res = new EnumMap<Colours, Integer>(Colours.class);
        res.put(Colours.YELLOW, 5);
        var list = new LinkedList<CardLeader>();
        list.add(CardLeader.CARDLEAD05);
        var card = CardLeader.CARDLEAD05;
        var req = card.getReq();
        for(var r : req)
            checker.checkOnRequiredRes(res, list, card, (Requirements.RequirementsResources)r);
        assertEquals(CardLeader.CARDLEAD05, list.get(0));
    }

    @Test
    void checkOnRequiredCard(){
        var checker = new Checker(null);
        var slots = new LinkedList<ArrayList<CardDev>>();
        slots.add(new ArrayList<>());
        slots.add(new ArrayList<>());
        slots.get(0).add(CardDev.CARDDEV_1G1);
        slots.get(1).add(CardDev.CARDDEV_1Y1);
        var list = new LinkedList<CardLeader>();
        list.add(CardLeader.CARDLEAD01);
        var card = CardLeader.CARDLEAD01;
        var req = card.getReq();
        for(var r : req)
            checker.checkOnRequiredCard(slots, list, card, (Requirements.RequirementsCard)r);
        assertEquals(CardLeader.CARDLEAD01, list.get(0));
    }
}