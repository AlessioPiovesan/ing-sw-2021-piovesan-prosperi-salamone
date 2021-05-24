package it.polimi.ingsw.server.accessories.cards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CardDevTest{

	@Test
	void testToString(){
		for(var card : CardDev.values()){
			var name = card.name();
			var lvl = card.getLvl();
			var colour = card.getColour();
			var cost = card.getCost();
			var resTo = card.getResTo();
			var resFrom = card.getResFrom();
			var winPnt = card.getWinPnt();
			assertEquals(name + "\n" + "This is a " + lvl + "level " + colour + " card the gives " + winPnt + "points.\nIt costs" + "\t->\t" + Arrays.toString(cost) + "\nThis card allows you to produce\n" + Arrays.toString(resTo) + "\nStarting from\n" + Arrays.toString(resFrom), card.toString());
		}
	}

	@Test
	void getCardTest(){
		for(var card : CardDev.values())
			for(var i : new String[]{"1", "2", "3", "4"})
				for(var c : new String[]{"B", "G", "P", "Y"})
					for(var j : new String[]{"1", "2", "3"}){
						String code = new StringBuilder().append(i).append(c).append(j).toString();
						if(card.name().contains(code)) assertEquals(card, CardDev.getSpecificCard(code));
						else assertNotEquals(card, CardDev.getSpecificCard(code));
					}
	}

	@Test
	void flippedTest(){
		for(var card : CardDev.values()){
			assertTrue(card.getFlipped());
			card.flipCard();
			assertFalse(card.getFlipped());
		}

	}
}