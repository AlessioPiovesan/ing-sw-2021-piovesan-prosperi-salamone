package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.accessori.Colours;
import it.polimi.ingsw.server.accessori.Pick;
import it.polimi.ingsw.server.model_server.carte.CardLeader;

import java.util.ArrayList;

public class Warehouse{
    private final ArrayList<Pick> wareContent;

    public Warehouse ( ){
        wareContent = new ArrayList<Pick>();
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
        wareContent.add(new Pick(Colours.WHITE, 0));
    }

    public void setResources (Colours color, int qnt){
        /*ho già controllato che posso farlo
         * metto una quantità qnt di risorsa color nella posizione lvl della lista*/
        wareContent.add(pickLvl(color, qnt), new Pick(color, qnt));
    }

    public boolean checkAdd (int lvl, Colours res, int qnt){
        /*il controllo che si vogliono aggiungere meno di 3 risorse è già stato fatto*/
        /*dati scaffale, risorsa e quantità*/
        if (wareContent.isEmpty()) return true;
        /*controllo se su questo scaffale posso aggiungere questa quantità di risorse*/
        /*se sullo scaffale c'è già questo tipo di risorsa controllo se posso aggiungere e poi aggiungo*/
        return qnt < 3;
    }

    public Pick checkLvlStatus (int lvl){
        System.out.println("Sullo scaffale " + lvl + " ci sono " + wareContent.get(lvl).getQnt() + " risorse di tipo" + wareContent.get(lvl).getResource().getType());
    return new Pick(wareContent.get(lvl).getResource(), wareContent.get(lvl).getQnt());
    }

    protected void lvlExchange (int lvlStart, int lvlEnd){ /* DA IMPLERMENTARE */
        /*scambia le risorse che stanno su un livello del magazzino con quelle di un altro livello*/
    }

    private int pickLvl (Colours colour, int qnt){ /* DA IMPLERMENTARE */
        int choice = 1;
        return choice;
    }

    public ArrayList<Pick> checkStatus ( ){ /* DA IMPLERMENTARE */
        ArrayList<Pick> status = new ArrayList<>();
        for (int i = 0;i < 3;i++){
            status.set(i, checkLvlStatus(i));
        }
        return status;
    }

    public void addLeaderSpace (CardLeader card){
        switch (card){
            case card_Lead5:
                wareContent.add(new Pick(Colours.GREY, 0));
            case card_Lead6:
                wareContent.add(new Pick(Colours.PURPLE, 0));
            case card_Lead7:
                wareContent.add(new Pick(Colours.BLUE, 0));
            case card_Lead8:
                wareContent.add(new Pick(Colours.YELLOW, 0));
            default:
                System.out.println("ERROR");
        }
    }
}
