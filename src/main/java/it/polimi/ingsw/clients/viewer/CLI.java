package it.polimi.ingsw.clients.viewer;

import it.polimi.ingsw.clients.singleplayer.controller.LocalGameController;
import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.Pick;
import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/*CONTROLLA CHE SIA TUTTO SCRITTO IN INGLESE*/

public class CLI implements Runnable, UI{

	private final BufferedReader input;
	private final PrintWriter output;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private String nickname;
	private boolean single;

	public CLI(){
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new PrintWriter(System.out, true);
	}

   /* public static void main(String[] args) {

        System.out.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
        System.out.println("by\n\u001B[34mAlessio Piovesan\u001B[0m,\n\u001B[32mPier Giorgio Prosperi\u001B[0m,\n\u001B[33mMatteo Salamone\u001B[0m,\n");
        //System.out.println("View full rules here: " + "URL delle regole" + "\n");
        var scanner = new Scanner(System.in);
        System.out.print(">\tInsert the server IP address\n>\t");
        ip = scanner.nextLine();
        while (!(InetAddressValidator.getInstance().isValid(ip))) {
            if (!(InetAddressValidator.getInstance().isValid(ip)))
                System.out.println("Server IP address is not valid");
            ip = scanner.nextLine();
        }
        System.out.print(">\tInsert the server port\n>\t");
        port = scanner.nextInt();
        while (port < 1023 || port > 65535) {
            if (port < 1023 || port > 65535)
                System.out.println("Server port is not valid");
            port = scanner.nextInt();
        }
        var cli = new CLI();
        cli.run();
    }*/

	@Override
	public void run(){
		setup();
		/*Qui dovrà passare tutto da controller creato Server*//*
        var gameController = new GameController(controller.getInstance().newID(), 1, new Game(new Market()));
        gameController.setGamers(1);*/
		/*Questa associazione la deve fare il Sever eventualmente*//*
        gameController.getActiveBoardController().addPropertyChangeListener(this);
        pcs.firePropertyChange("remoteView", "", gameController.getActiveBoardController().getPlayer().getRemoteView());
        gameController.getActiveBoardController().getPlayer().getRemoteView().addPropertyChangeListener(gameController.getActiveBoardController());
        gameController.startGame();*/
	}

	private void setup(){
		output.println("˜”*°•.˜”*°• Masters of Renaissance •°*”˜.•°*”˜");
		output.println("by\n\u001B[34mAlessio Piovesan\u001B[0m,\n\u001B[32mPier Giorgio Prosperi\u001B[0m,\n\u001B[33mMatteo Salamone\u001B[0m,\n");
		var nickname = "";
		do{
			while(Objects.equals(nickname, "")){
				output.println(">Insert your nickname: ");
				output.print(">\t");
				try{
					nickname = input.readLine();
				} catch(Exception e){
					e.printStackTrace();
				}
				output.println("> You chose: " + nickname);
				output.println("> Is it ok? [y/n] ");
				output.println(">\t");
				try{
					String r = input.readLine();
					if(!(r.equals("y"))) nickname = "";
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		} while(nickname.equals(""));
		setNickname(nickname);
	}

	private void setNickname(String nickname){
		this.nickname = nickname;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt){
		switch(evt.getPropertyName()){
			case "temporaryLeaderCards" -> chooseInitialLeaders((CardLeader[])evt.getNewValue());
			case "firstOp" -> {if(single) LocalGameController.getInstance().nextPlayer();}
			case "nextAction" -> nextAction();
			case "marketBuffer" -> resourcesHandlerMarket(castList(Colours.class, (Collection<?>)evt.getNewValue()));
			case "ProductionBuffer" -> resourcesHandler(castList(Pick.class, (Collection<?>)evt.getNewValue()));
			default -> output.println("Nope");
		}
	}

	private void chooseInitialLeaders(CardLeader[] cards){
		var list = new ArrayList<CardLeader>();
		var finale = new CardLeader[2];
		Collections.addAll(list, cards);
		var i = 0;
		while(i < 2){
			for(CardLeader card : list)
				showCards(card);
			var card = (CardLeader)pickCard(CardLeader.class);
			if(list.contains(card)){
				list.remove(card);
				finale[i] = card;
				i++;
			}
			else output.println("Something went wrong");
		}
		if(single) LocalGameController.getInstance().getBoardController().getPlayer().setMyLeaderCards(finale);
	}

	private void nextAction(){
		if(single && LocalGameController.getInstance().isLorenzoPlaying())
			LocalGameController.getInstance().moveLorenzo();
		else{
			var ok = true;
			while(ok){
				ok = false;
				try{
					output.println("""
					               Prossima azione?
					               1) Attivare la produzione in plancia
					               2) Prendere risorse dal mercato
					               3) Comprare una carta sviluppo
					               4) Attivare Carta Leader
					               """);
					var action = Integer.parseInt(input.readLine());
					output.println("Is " + action + " right? [y/n]");
					var i = input.readLine();
					if(i.equals("y")){
						switch(action){
							case 1 -> LocalGameController.getInstance().prodAction();
							case 2 -> marketMove();
							case 3 -> chooseCardDev();
							case 4 -> wakeALeader();
							default -> output.println("ERRORE");
						}
					}
					else ok = true;
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	private void resourcesHandlerMarket(List<Colours> bufferMarket){
		var keep = new ArrayList<Colours>();
		var loose = new ArrayList<Colours>();
		/*Mostro le risorse*/
		for(Colours colour : bufferMarket){
			try{
				var ok = true;
				while(ok){
					ok = false;
					output.println(colour + "la metto in magazzino?\n0) No\n1)Yes\n>\t");
					var j = Integer.parseInt(input.readLine());
					switch(j){
						case 0 -> loose.add(colour);
						case 1 -> keep.add(colour);
						default -> ok = true;
					}
				}
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		LocalGameController.getInstance().addFaithPnt(loose);
		for(Colours colours : keep)
			prepareResForWarehouse(colours, 1);
	}

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection){
		List<T> result = new ArrayList<>(rawCollection.size());
		for(Object o : rawCollection){
			try{
				result.add(clazz.cast(o));
			} catch(ClassCastException e){
				// log the exception or other error handling
			}
		}
		return result;
	}

	private void resourcesHandler(List<Pick> bufferProd){
		output.println("Da dove le prendo?");
		for(Pick pick : bufferProd){
			var ok = true;
			while(ok){
				ok = false;
				output.println(pick);
				output.println("1)Warehouse o 2)StrongBox?");
				try{
					var j = Integer.parseInt(input.readLine());
					switch(j){
						case 1 -> prepareResForWarehouse(pick.getResource(), -pick.getQnt());
						case 2 -> LocalGameController.getInstance().getBoardController().getPlayer().storeSB(new Pick[]{new Pick(pick.getResource(), -pick.getQnt())});
						default -> ok = true;
					}
				} catch(IOException e){
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public void showCards(Card card){
		output.println(card.toString());
	}

	private Card pickCard(Class<? extends Card> clazz){
		var ok = true;
		Card card = null;
		while(ok){
			output.println("Code? (last digits)");
			try{
				String response = input.readLine().toUpperCase();
				if(clazz == CardLeader.class){
					if(Integer.parseInt(response) <= 0 || Integer.parseInt(response) >= 16){
						output.println("Not a correct code");
					}
					else{
						output.println("You choose " + response + ", right? [y/n]");
						String i = input.readLine();
						if(i.equals("y")){
							card = CardLeader.getSpecificCard(response);
							ok = false;
						}
					}
				}
				if(clazz == CardDev.class){
					if(!(response.length() == 3 && (response.charAt(0) == '1') || response.charAt(0) == '2' || response.charAt(0) == '3' || response.charAt(0) == '4') && (response.charAt(1) == 'B' || response.charAt(1) == 'G' || response.charAt(1) == 'P' || response.charAt(1) == 'Y') && (response.charAt(2) == '1' || response.charAt(2) == '2' || response.charAt(2) == '3')){
						output.println("Not a correct code");
					}
					else{
						output.println("You choose " + response + ", right? [y/n]");
						String i = input.readLine();
						if(i.equals("y")){
							card = CardLeader.getSpecificCard(response);
							ok = false;
						}
					}
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return card;
	}

	private void marketMove(){
		try{
			output.println("Mostro il mercato"); /*DA IMPLEMENTARE*/
			/*Mostro il mercato*/
			output.println("Chiedo la mossa in maniera furba chiedendo riga/colonna");
			var j = Integer.parseInt(input.readLine());
			LocalGameController.getInstance().marketAction(j);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	private void chooseCardDev(){
		var ok = true;
		while(ok){
			try{
				var actual = LocalGameController.getInstance().getGame().getCardDevDeck();
				for(CardDev card : actual)
					card.toString();
				output.println("Choose the card [the code is the last digits]");
				var j = input.readLine();
				output.println("Is " + j + " right? [y/n]");
				var i = input.readLine();
				if(i.equals("y")){
					for(CardDev cardDev : actual)
						if(j.equals(cardDev.name())) LocalGameController.getInstance().cardDevAction(cardDev);
				}
				else ok = false;
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private void wakeALeader(){
		var ok = true;
		while(ok){
			try{
				var actual = LocalGameController.getInstance().getGame().getBoard(false).getMyLeaderCards();
				for(CardLeader card : actual)
					card.toString();
				output.println("Choose the card [the code is the last digits]");
				var j = input.readLine();
				output.println("Is " + j + " right? [y/n]");
				var i = input.readLine();
				if(i.equals("y")){
					for(CardLeader card : actual)
						if(j.equals(card.name())) LocalGameController.getInstance().cardLeadAction(card);
				}
				else ok = false;
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private void prepareResForWarehouse(Colours colour, int qnt){
		try{
			output.print("Level for this resource? [1-3]\n" + colour.getType() + "\n>\t");
			var lvl = Integer.parseInt(input.readLine()) - 1;
			/*MANCANO I CONTROLLI*/
			if(lvl > -1 && lvl < 3)
				LocalGameController.getInstance().getBoardController().warehouseResHandler(colour, qnt, lvl);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}
}
