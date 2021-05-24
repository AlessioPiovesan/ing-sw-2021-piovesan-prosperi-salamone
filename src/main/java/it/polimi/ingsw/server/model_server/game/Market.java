package it.polimi.ingsw.server.model_server.game;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Constants;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Market{

	private final LinkedList<Colours> marbles = new LinkedList<>();
	private final Random random = new Random();
	protected Colours[][] tray;
	private Colours external;

	public Market(){
		/*creo le biglie*/
		IntStream.range(this.marbles.size(), Constants.MAXMARBLESB.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.BLUE));
		IntStream.range(this.marbles.size(), Constants.MAXMARBLESG.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.GREY));
		IntStream.range(this.marbles.size(), Constants.MAXMARBLESP.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.PURPLE));
		IntStream.range(this.marbles.size(), Constants.MAXMARBLESR.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.RED));
		IntStream.range(this.marbles.size(), Constants.MAXMARBLESW.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.WHITE));
		IntStream.range(this.marbles.size(), Constants.MAXMARBLESY.getVal() + this.marbles.size()).forEach(i -> this.marbles.add(Colours.YELLOW));
		/*metto le biglie nel mercato*/
		tray = fillMarket(marbles);
	}

	private Colours[][] fillMarket(LinkedList<Colours> marbles){
		LinkedList<Integer> list = new LinkedList<>();
		var m = new Colours[Constants.XMARKET.getVal()][Constants.YMARKET.getVal()];
		for(var j = 0; j < Constants.YMARKET.getVal(); j++){
			fillColumns(marbles, list, Constants.XMARKET.getVal(), Constants.YMARKET.getVal(), m, j);
		}
		findFirstExternal(marbles, list);
		return m;
	}

	private void fillColumns(LinkedList<Colours> marbles, LinkedList<Integer> list, int x, int y, Colours[][] m, int j){
		for(var i = 0; i < x; i++){
			int r = findNextMarble(list, x, y);
			list.add(r);
			m[i][j] = marbles.get(r);
		}
	}

	private void findFirstExternal(LinkedList<Colours> marbles, LinkedList<Integer> list){
		for(var i = 0; i < (Constants.XMARKET.getVal() * Constants.YMARKET.getVal()) + 1; i++)
			if(!list.contains(i)) external = marbles.get(i);
	}

	private int findNextMarble(LinkedList<Integer> list, int x, int y){
		var r = random.nextInt((x * y) + 1);
		if(list.contains(r)) while(list.contains(r)) r = random.nextInt((x * y) + 1);
		return r;
	}

	public Colours getExternal(){
		return external;
	}

	public void setExternal(Colours res){
		external = res;
	}

	public List<Colours> moveMarbles(int choice){
		LinkedList<Colours> res;
		res = getRes(choice);
		move(choice, external);
		return res;
	}

	private LinkedList<Colours> getRes(int choice){
		return switch(choice){
			case 0 -> getResX(0);
			case 1 -> getResX(1);
			case 2 -> getResX(2);
			case 6 -> getResY(0);
			case 5 -> getResY(1);
			case 4 -> getResY(2);
			case 3 -> getResY(3);
			default -> throw new IllegalStateException("Unexpected value: " + choice);
		};
	}

	private void move(int choice, Colours r1){
		switch(choice){
			case 0 -> operationMoveY(r1, 3);
			case 1 -> operationMoveY(r1, 2);
			case 2 -> operationMoveY(r1, 1);
			case 3 -> operationMoveX(r1, 1);
			case 4 -> operationMoveX(r1, 2);
			case 5 -> operationMoveX(r1, 3);
			case 6 -> operationMoveX(r1, 4);
			default -> throw new IllegalStateException("Unexpected value: " + choice);
		}
	}

	private LinkedList<Colours> getResX(int y){
		LinkedList<Colours> res = new LinkedList<>();
		for(var i = 0; i < Constants.XMARKET.getVal(); i++) res.add(tray[i][y]);
		return res;
	}

	private LinkedList<Colours> getResY(int x){
		return new LinkedList<>(Arrays.asList(tray[x]).subList(0, Constants.YMARKET.getVal()));
	}

	private void operationMoveY(Colours r1, int y){
		external = tray[0][Constants.YMARKET.getVal() - y];
		moveY(r1, y);
	}

	private void operationMoveX(Colours r1, int x){
		external = tray[Constants.XMARKET.getVal() - x][0];
		moveX(r1, x);
	}

	private void moveY(Colours r1, int y){
		Colours r2;
		for(int i = Constants.XMARKET.getVal(); i > 0; i--){
			r2 = tray[i - 1][Constants.YMARKET.getVal() - y];
			tray[i - 1][Constants.YMARKET.getVal() - y] = r1;
			r1 = r2;
		}
	}

	private void moveX(Colours r1, int x){
		Colours r2;
		for(int i = Constants.YMARKET.getVal(); i > 0; i--){
			r2 = tray[Constants.XMARKET.getVal() - x][i - 1];
			tray[Constants.XMARKET.getVal() - x][i - 1] = r1;
			r1 = r2;
		}
	}

}
