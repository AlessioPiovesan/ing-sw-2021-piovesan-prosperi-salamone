package it.polimi.ingsw.Server.model_Server;

import java.util.ArrayList;
import java.util.HashMap;

class Board{
    int idD;
    Warehouse w;
    StrongBox v;
    int positionTF;
    Library[] l;
    FaithTrack t;

    Board (int id){
        this.idD = id;
        /* Warehouse creation for a single Board */
        this.w = new Warehouse (id);
        /* Strongbox creation for a single Board */
        this.v = new StrongBox (id);
        /* Faith Track creation for a single board */
        this.t = new FaithTrack (id);
    }

    protected class FaithTrack{

        private int id;                     /* Da capire bene come funzionano gli ID*/
        private int position;

        FaithTrack (int id){
            this.id = id;
        }

        public int getId ( ){
            return id;
        }

        public int getPosition ( ){
            return position;
        }

        public int getWinPnt ( ){/*prende la posizione e raccoglie i winPnt della casella precedente più vicina*/ return 1;}
        public boolean checkPopeSpace ( ){/**/
        /*switch (position):
            case 5: return true;
            case 10: return true;
            case 24: return true;*/
            return true;
        }


        class VaticanReport{
            int[] vaticanReport1;
            int[] vaticanReport2;
            int[] vaticanReport3;
        }

        class PopeTile{
            int winPnt;

            public int getWinPnt ( ){
                return winPnt;
            }
        }
    }



    protected class Library{
        int id;
        HashMap<Integer, CardDev> l;

        Library (int id){
            this.id = id;
            /*creazione scompartimenti per una libreria di una plancia*/
            this.l = new HashMap<> ();
            l.put (1, null);
            l.put (2, null);
            l.put (3, null);
        }
    }

    protected class StrongBox{
        int id;
        HashMap<Colours, Integer> comp;

        StrongBox (int id){
            this.id = id;
            comp = new HashMap<> ();
            comp.put (Colours.BLUE, 0);
            comp.put (Colours.GREY, 0);
            comp.put (Colours.PURPLE, 0);
            comp.put (Colours.YELLOW, 0);
        }

        private void addResources (StrongBox s, Colours res, int add){
            s.comp.replace (res, s.comp.get (res) + add);
        }
    }

    protected class Warehouse{
        private int id;
        private ArrayList<String> wareContent;

        Warehouse (int id){
            this.id = id;
            this.wareContent = new ArrayList<String> (3);
        }

        private void setResources (int lvl, Resource color, int qnt){
            /*ho già controllato che posso farlo
             * metto una quantità qnt di risorsa color nella posizione lvl della lista*/
            if (wareContent.isEmpty ()) wareContent.add (color.toString ()); /*qui aggiungi risorse e tipo*/
        }

        public boolean checkAdd (int lvl, Resource color, int qnt){
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
            System.out.println ("Sullo scaffale " + lvl + " ci sono "/* N risorse*/);
        }

        private void lvlExchange (int lvlStart, int lvlEnd){
            /*scambia le risorse che stanno su un livello del magazzino con quelle di un altro livello*/
        }
    }
}
