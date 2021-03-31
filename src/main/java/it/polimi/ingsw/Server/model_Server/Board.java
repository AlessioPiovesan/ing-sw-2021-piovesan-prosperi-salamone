package it.polimi.ingsw.Server.model_Server;

import java.util.ArrayList;
import java.util.HashMap;

class Board{
    int idD;
    Warehouse warehouse;
    StrongBox strongBox;
    int positionTF;
    Library[] l;
    FaithTrack faithTrack;

    Board (int id){
        this.idD = id;
        /* Warehouse creation for a single Board */
        this.warehouse = new Warehouse ();
        /* Strongbox creation for a single Board */
        this.strongBox = new StrongBox ();
        /* Faith Track creation for a single board */
        this.faithTrack = new FaithTrack ();
        /* Set Faith Marker starting point */
        positionTF=0;
    }

    protected class FaithTrack{
        private int position;

        FaithTrack (){
            this.position = 0;
        }

        public int getPosition ( ){
            return position;
        }

        public void setPosition (int step){ position += step; }

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
        HashMap<Integer, CardDev> l;

        Library (){
            /*creazione scompartimenti per una libreria di una plancia*/
            this.l = new HashMap<> ();
            l.put (1, null);
            l.put (2, null);
            l.put (3, null);
        }
    }

    protected class StrongBox{
        HashMap<Colours, Integer> comp;

        StrongBox (){
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
        private ArrayList<Pick> wareContent;

        Warehouse (){
            wareContent = new ArrayList<Pick> ();
            wareContent.add (new Pick (Colours.WHITE, 0));
            wareContent.add (new Pick (Colours.WHITE, 0));
            wareContent.add (new Pick (Colours.WHITE, 0));
        }

        public void setResources (Colours color, int qnt){
            int lvl = pickLvl (color, qnt);
            /*ho già controllato che posso farlo
             * metto una quantità qnt di risorsa color nella posizione lvl della lista*/
            wareContent.get (lvl).r=color;
            wareContent.get (lvl).qnt=qnt;
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
            System.out.println ("Sullo scaffale " + lvl + " ci sono " + wareContent.get (lvl).qnt +" risorse di tipo" + wareContent.get (lvl).r.type);
        }

        private void lvlExchange (int lvlStart, int lvlEnd){
            /*scambia le risorse che stanno su un livello del magazzino con quelle di un altro livello*/
        }

        private int pickLvl(Colours colour, int qnt){
            int choice=0;
            return  choice;}
    }
}
