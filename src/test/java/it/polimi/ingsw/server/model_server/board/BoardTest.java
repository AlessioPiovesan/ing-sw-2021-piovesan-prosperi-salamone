package it.polimi.ingsw.server.model_server.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest{

	@Test
	void testCheckDiscardedResources(){
		Board b = new Board(0, null, null, null, null);
		LinkedList<Colours> list = new LinkedList<>();
		list.add(Colours.BLUE);
		list.add(Colours.YELLOW);
		list.add(Colours.GREY);
		list.add(Colours.BLUE);
		assertEquals(4, list.size());

	}

	@Test
	void setDiscount(){
		Board b = new Board(0, null, null, null, null);
		b.setDiscount(CardLeader.CARDLEAD01.getSpecial());
		b.setDiscount(CardLeader.CARDLEAD02.getSpecial());
		assertEquals(CardLeader.CARDLEAD01.getSpecial(), b.discount[0]);
		assertEquals(CardLeader.CARDLEAD02.getSpecial(), b.discount[1]);
	}

	@Test
	void setProductAlt(){
		Board b = new Board(0, null, null, null, null);
		b.setProductAlt(CardLeader.CARDLEAD13.getSpecial());
		b.setProductAlt(CardLeader.CARDLEAD14.getSpecial());
		assertEquals(CardLeader.CARDLEAD13.getSpecial(), b.productAlt[0]);
		assertEquals(CardLeader.CARDLEAD14.getSpecial(), b.productAlt[1]);
	}

	@Test
	void setMarketAlt(){
		Board b = new Board(0, null, null, null, null);
		b.setMarketAlt(CardLeader.CARDLEAD09.getSpecial());
		b.setMarketAlt(CardLeader.CARDLEAD10.getSpecial());
		assertEquals(CardLeader.CARDLEAD09.getSpecial(), b.marketAlt[0]);
		assertEquals(CardLeader.CARDLEAD10.getSpecial(), b.marketAlt[1]);
	}
}