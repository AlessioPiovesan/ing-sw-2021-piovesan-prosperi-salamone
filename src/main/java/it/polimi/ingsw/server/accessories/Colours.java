package it.polimi.ingsw.server.accessories;

import java.util.ArrayList;
import java.util.List;

public enum Colours{

	BLUE("SHIELD"),
	GREY("STONE"),
	PURPLE("SERVANT"),
	RED("FAITH"),
	WHITE(""),
	YELLOW("COIN");

	private final String type;

	Colours(String type){
		this.type = type;
	}

	public static List<Colours> valid(){
		ArrayList<Colours> list = new ArrayList<>();
		list.add(BLUE);
		list.add(GREY);
		list.add(PURPLE);
		list.add(YELLOW);
		return list;
	}

	public static Colours fromString(String color){
		return switch(color.toUpperCase()){
			case "SHIELD", "BLUE" -> BLUE;
			case "STONE", "GREY" -> GREY;
			case "SERVANT", "PURPLE" -> PURPLE;
			case "FAITH", "RED" -> RED;
			case "", "WHITE" -> WHITE;
			case "COIN", "YELLOW" -> YELLOW;
			default -> null;
		};
	}

	public String getType(){
		return type;
	}
}
