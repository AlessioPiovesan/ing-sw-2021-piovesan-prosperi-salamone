package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;
import it.polimi.ingsw.Server.model_Server.Accessori.Pick;

import java.util.ArrayList;

public class Warehouse{
    private ArrayList<Pick> wareContent;

    Warehouse ( ){
        wareContent = new ArrayList<Pick> ();
        wareContent.add (new Pick (Colours.WHITE, 0));
        wareContent.add (new Pick (Colours.WHITE, 0));
        wareContent.add (new Pick (Colours.WHITE, 0));
    }

    public void setResources (Colours color, int qnt){
        int lvl = pickLvl (color, qnt);
        /*ho già controllato che posso farlo
         * metto una quantità qnt di risorsa color nella posizione lvl della lista*/
        wareContent.get (lvl).setR (color);
        wareContent.get (lvl).setQnt (qnt);
    }

    public boolean checkAdd (int lvl, Colours res, int qnt){
        /*il controllo che si vogliono aggiungere meno di 3 risorse è già stato fatto*/
        /*dati scaffale, risorsa e quantità*/
        if (wareContent.isEmpty ()) return true;
        if (qnt < 3){
            /*controllo se su questo scaffale posso aggiungere questa quantità di risorse*/
            /*se sullo scaffale c'è già questo tipo di risorsa controllo se posso aggiungere e poi aggiungo*/
            return true;
        }
        else return false;
    }

    public void checkLvlStatus (int lvl){
        System.out.println ("Sullo scaffale " + lvl + " ci sono " + wareContent.get (lvl).getQnt () + " risorse di tipo" + wareContent.get (lvl).getR ().getType ());
    }

    protected void lvlExchange (int lvlStart, int lvlEnd){
        /*scambia le risorse che stanno su un livello del magazzino con quelle di un altro livello*/
    }

    private int pickLvl (Colours colour, int qnt){
        int choice = 1;
        return choice;
    }

    public void checkStatus ( ){
        System.out.println(wareContent);
    }
}
