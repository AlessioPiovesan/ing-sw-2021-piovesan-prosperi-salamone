package it.polimi.ingsw.server.accessories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColoursTest{

	@Test
	void validTest(){
		for(Colours colours : Colours.values())
			if(Colours.valid().contains(colours)) assertTrue(Colours.valid().contains(colours));
			else assertFalse(Colours.valid().contains(colours));
	}

	@Test
	void fromStringTest(){
		for(Colours color : Colours.values()){
			assertEquals(color, Colours.fromString(color.name()));
			assertEquals(color, Colours.fromString(color.getType()));
		}
		assertNull(Colours.fromString("ciao"));
	}
}