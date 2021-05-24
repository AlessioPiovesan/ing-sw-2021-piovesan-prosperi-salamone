package it.polimi.ingsw.clients.model_client.single;

import it.polimi.ingsw.clients.controller_client.GameControllerOnCLient;
import it.polimi.ingsw.clients.view_client.UI;
import it.polimi.ingsw.server.accessories.cards.CardLeader;
import it.polimi.ingsw.server.viewer_server.RemoteView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ModelView implements PropertyChangeListener{

	private RemoteView remoteView;
	private boolean single = false;

	public ModelView(UI ui){
		ui.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt){
		System.out.println(evt);
		switch(evt.getPropertyName()){
			case "myLeaderCards" -> {
				if(single)
					GameControllerOnCLient.getInstance().getGame().getBoard(false).setMyLeaderCards((CardLeader[])evt.getNewValue());
				else remoteView.setMyLeaderCards((CardLeader[])evt.getNewValue());
			}
			case "remoteView" -> {
				setRemoteView((RemoteView)evt.getNewValue());
			}
			case "single" -> {
				single = (boolean)evt.getNewValue();
			}
			case "firstOp" -> {
				var a = 1;/*Qui dà le risorse nel partita multipleyer*/
			}
			default -> {
				doNothing();
			}
		}
	}

	public void setRemoteView(RemoteView remoteView){
		this.remoteView = remoteView;
	}

	private void doNothing(){
		try{
			wait(10);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
