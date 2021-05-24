package it.polimi.ingsw.server.model_server.board;

import it.polimi.ingsw.server.accessories.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FaithTrackTest{

	@Test
	void popeFavorWinPntTest(){
		for(int i = 1; i < Constants.MAXTRACK.getVal(); i++){
			FaithTrack faithTrack = new FaithTrack();
			faithTrack.moveForward(i);
			assertEquals(i, faithTrack.getPosition());
			faithTrack.getWinPntCard();
			switch(i){
				case 5, 6, 7, 8 -> assertEquals(2, faithTrack.getPopeFavorWinPnt());
				case 12, 13, 14, 15, 16 -> assertEquals(3, faithTrack.getPopeFavorWinPnt());
				case 19, 20, 21, 22, 23, 24 -> assertEquals(4, faithTrack.getPopeFavorWinPnt());
				default -> assertEquals(0, faithTrack.getPopeFavorWinPnt());
			}
		}
	}

	@Test
	void getWinPntTest(){
		for(int i = 1; i < Constants.MAXTRACK.getVal(); i++){
			FaithTrack faithTrack = new FaithTrack();
			faithTrack.moveForward(i);
			switch(i){
				case 3, 4, 5 -> assertEquals(1, faithTrack.getWinPnt());
				case 6, 7, 8 -> assertEquals(2, faithTrack.getWinPnt());
				case 9, 10, 11 -> assertEquals(4, faithTrack.getWinPnt());
				case 12, 13, 14 -> assertEquals(6, faithTrack.getWinPnt());
				case 15, 16, 17 -> assertEquals(9, faithTrack.getWinPnt());
				case 18, 19, 20 -> assertEquals(12, faithTrack.getWinPnt());
				case 21, 22, 23 -> assertEquals(16, faithTrack.getWinPnt());
				case 24 -> assertEquals(20, faithTrack.getWinPnt());
				default -> assertEquals(0, faithTrack.getWinPnt());
			}
		}
	}
}