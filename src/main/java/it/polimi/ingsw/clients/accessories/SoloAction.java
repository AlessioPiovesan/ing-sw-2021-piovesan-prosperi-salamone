package it.polimi.ingsw.clients.accessories;

import it.polimi.ingsw.clients.controller_client.GameControllerOnCLient;
import it.polimi.ingsw.server.accessories.ColoursCards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum SoloAction{

	AS1,
	AS2,
	AS3,
	AS4,
	AS5,
	AS6,
	AS7;

	private static final Random random = new Random();

	public static void action(SoloAction sa){
		switch(sa){
			case AS1 -> GameControllerOnCLient.getInstance().tossCardDev(ColoursCards.GREEN);
			case AS2 -> GameControllerOnCLient.getInstance().tossCardDev(ColoursCards.YELLOW);
			case AS3 -> GameControllerOnCLient.getInstance().tossCardDev(ColoursCards.CYAN);
			case AS4 -> GameControllerOnCLient.getInstance().tossCardDev(ColoursCards.VIOLET);
			case AS5, AS6 -> GameControllerOnCLient.getInstance().getLorenzoController().getLorenzo().getFaithTrack().moveForward(2);
			case AS7 -> {
				GameControllerOnCLient.getInstance().getLorenzoController().getLorenzo().getFaithTrack().moveForward(1);
				shuffle();
			}
			default -> doNothing();
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

	private static void doNothing(){
		shuffle();
	}

	public static List<SoloAction> pickSoloAction(List<SoloAction> list){
		var sa = list.get(0);
		list.remove(sa);
		list.add(sa);
		return list;
	}
}
