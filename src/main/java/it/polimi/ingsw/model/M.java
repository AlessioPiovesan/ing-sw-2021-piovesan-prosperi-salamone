package it.polimi.ingsw.model;

import java.util.*;

public class M {

    public class Game {
        static final int XMARKET = 4;
        static final int YMARKET = 3;
        static final int MAXTRACK = 20;
        static final int MAXMARBLESW = 4;
        static final int MAXMARBLESB = 2;
        static final int MAXMARBLESG = 2;
        static final int MAXMARBLESY = 2;
        static final int MAXMARBLESP = 2;
        static final int MAXMARBLESR = 1;

        /*final HashMap<Integer, Card> CARDS = new HashMap<Integer, Card>();*/

        Board[] b;
        LinkedList<Resources> r;
        Market m;

        Game ( int gamers ) {

            if (gamers > 1) {
                /* Board creation for each player */
                for (int i = 0; i < gamers; i++) this.b[i] = new Board(i);
            } else {
                /* SINGLE PLAYER */
            }
            /* Market creation for each Game */
            this.m = new Market(XMARKET, YMARKET);
            /* Resources creation for each Game */
            for (int i = (int) this.r.size(); i < MAXMARBLESB * 8 + this.r.size(); i++)
                this.r.add(new Resources(Colours.BLUE.toString()));
            for (int i = (int) this.r.size(); i < MAXMARBLESG * 8 + this.r.size(); i++)
                this.r.add(new Resources(Colours.GREY.toString()));
            for (int i = (int) this.r.size(); i < MAXMARBLESP * 8 + this.r.size(); i++)
                this.r.add(new Resources(Colours.PURPLE.toString()));
            for (int i = (int) this.r.size(); i < MAXMARBLESY * 8 + this.r.size(); i++)
                this.r.add(new Resources(Colours.YELLOW.toString()));
            /* Card creation for each Game */
            /*...*/
        }
    }

    class Board {
        int idD;
        Warehouse w;
        StrongBox v;
        int positionTF;
        Library[] l;
        FaithTrack t;

        Board ( int id ) {
            this.idD = id;
            /* Warehouse creation for a single Board */
            this.w = new Warehouse(id);
            /* Strongbox creation for a single Board */
            this.v = new StrongBox(id);
            /* Faith Track creation for a single board */
            this.t = new FaithTrack(id);
        }

            private class FaithTrack {

                int id;                     /* Da capire bene come funzionano gli ID*/
                int position;

                FaithTrack ( int id ) {
                    this.id = id;
                }
            }

        protected class Library {
            int id;
            HashMap<Integer, Card> l;

            Library ( int id ) {
                this.id = id;
                /*creazione scompartimenti per una libreria di una plancia*/
                this.l = new HashMap<>();
                l.put(1, null);
                l.put(2, null);
                l.put(3, null);
            }
        }

        private class StrongBox {
            int id;
            HashMap<Colours, Integer> comp;

            StrongBox ( int id ) {
                this.id = id;
                comp = new HashMap<>();
                comp.put(Colours.BLUE, 0);
                comp.put(Colours.GREY, 0);
                comp.put(Colours.PURPLE, 0);
                comp.put(Colours.YELLOW, 0);
            }

            private void addResources ( StrongBox s, Colours res, int add ) {
                s.comp.replace(res, s.comp.get(res) + add);
            }
        }

        private class Warehouse {
            private int id;
            private ArrayList<String> wareContent;

            Warehouse ( int id ) {
                this.id = id;
                this.wareContent = new ArrayList<String>(3);
            }

            private void setResources ( int lvl, Resources color, int qnt ) {
                /*ho già controllato che posso farlo
                 * metto una quantità qnt di risorsa color nella posizione lvl della lista*/
                if (wareContent.isEmpty()) wareContent.add(color.toString()); /*qui aggiungi risorse e tipo*/
            }

            public boolean checkAdd ( int lvl, Resources color, int qnt ) {
                /*il controllo che si vogliono aggiungere meno di 3 risorse è già stato fatto*/
                /*dati scaffale, risorsa e quantità*/
                if (wareContent.isEmpty()) return true;
                if (qnt < 3) {
                    /*controllo se su questo scaffale posso aggiungere questa quantità di risorse*/
                    /*se sullo scaffale c'è già questo tipo di risorsa controllo se posso aggiungere e poi aggiungo*/
                    return true;
                } else return false;
            }

            public void checkLvlStatus ( int lvl ) {
                System.out.println("Sullo scaffale " + lvl + " ci sono "/* N risorse*/);
            }

            private void lvlExchange ( int lvlStart, int lvlEnd ) {
                /*scambia le risorse che stanno su un livello del magazzino con quelle di un altro livello*/
            }
        }
    }

    enum Colours {
        BLUE("SHEILD"), GREEN(null), GREY("STONE"), PURPLE("SERVANT"), RED("FAITH"), WHITE(""), YELLOW("COIN");
        final String type;

        Colours ( String type ) {
            this.type = type;
        }

        public String getType (Colours c) {
            return c.type;
        }
    }

    public class Resources {

        String colour;

        Resources ( String color ) {
            this.colour = color;
        }

        public Colours getType ( String color ) {
            return Colours.valueOf(color.toUpperCase(Locale.ROOT));
        }
    }

    public class Supply {
        HashMap<String, Integer> cont;

        Supply () {
            cont = new HashMap<>();
            cont.put("blue", 0);
            cont.put("grey", 0);
            cont.put("purple", 0);
            cont.put("yellow", 0);
        }

        public void addResources ( String color, int qnt ) {
            cont.replace(color, cont.get(color) + qnt);
        }
    }

    public class Market {
        Resources[][] matrix;
        Resources external;
        LinkedList<Resources> marbles;
        LinkedList<Integer> list;
        Random random = new Random();

        Market ( int maxX, int maxY ) {
            /*creo le biglie*/
            for (int i = (int) this.marbles.size(); i < Game.MAXMARBLESB + this.marbles.size(); i++)
                this.marbles.add(new Resources("blue"));
            for (int i = (int) this.marbles.size(); i < Game.MAXMARBLESG + this.marbles.size(); i++)
                this.marbles.add(new Resources("grey"));
            for (int i = (int) this.marbles.size(); i < Game.MAXMARBLESP + this.marbles.size(); i++)
                this.marbles.add(new Resources("purple"));
            for (int i = (int) this.marbles.size(); i < Game.MAXMARBLESR + this.marbles.size(); i++)
                this.marbles.add(new Resources("red"));
            for (int i = (int) this.marbles.size(); i < Game.MAXMARBLESW + this.marbles.size(); i++)
                this.marbles.add(new Resources("white"));
            for (int i = (int) this.marbles.size(); i < Game.MAXMARBLESY + this.marbles.size(); i++)
                this.marbles.add(new Resources("yellow"));
            /*metto le biglie nel mercato*/
            this.matrix = fillMarket(maxX, maxY, marbles);
            /*segno qual è la 13sima biglia*/
            this.external = setExternal(list, marbles);
            /*il mercato verrà gestito con la cella [0][0] in alto a destra*/
        }

        private Resources[][] fillMarket ( int X, int Y, LinkedList<Resources> marbles ) {
            this.list = new LinkedList<>();
            for (int j = 0; j < Y; j++) {
                for (int i = 0; i < X; i++) {
                    /*le biglie in tutto sono X * Y*/
                    int r = random.nextInt(X * Y);
                    while (!list.contains(r)) {
                        list.add(r);
                        matrix[i][j] = marbles.get(r);
                        r = random.nextInt(X * Y);
                    }
                }
            }
            return matrix;
        }

        protected Resources setExternal ( LinkedList<Integer> list, LinkedList<Resources> marbles ) {
            /*cerca nella lista delle biglie la tredicesima biglia, ecludendo le dodici che stanno nella lista di biglie che sono nel mercato*/
            return external;
        }

        private Resources[] moveMarbles ( Market market, int X, int Y, int choice ) {
            Resources[] res;
            Resources r1, r2;
            r1 = market.external;
            switch (choice) {
                case 0: {
                    market.external = matrix[X - 3][0];
                    for (int i = Y; i > 0; i--) {
                        r2 = matrix[0][i - 1];
                        matrix[0][i - 1] = r1;
                        r1 = r2;
                    }
                }
                case 1: {
                    market.external = matrix[X - 2][0];
                    for (int i = Y; i > 0; i--) {
                        r2 = matrix[1][i - 1];
                        matrix[1][i - 1] = r1;
                        r1 = r2;
                    }
                }
                case 2: {
                    market.external = matrix[X - 1][0];
                    for (int i = Y; i > 0; i--) {
                        r2 = matrix[2][i - 1];
                        matrix[2][i - 1] = r1;
                        r1 = r2;
                    }
                }
                case 3: {
                    market.external = matrix[0][Y - 1];
                    for (int i = X; i > 0; i--) {
                        r2 = matrix[i - 1][3];
                        matrix[i - 1][3] = r1;
                        r1 = r2;
                    }
                }
                case 4: {
                    market.external = matrix[0][Y - 2];
                    for (int i = X; i > 0; i--) {
                        r2 = matrix[i - 1][2];
                        matrix[i - 1][2] = r1;
                        r1 = r2;
                    }
                }
                case 5: {
                    market.external = matrix[0][Y - 3];
                    for (int i = X; i > 0; i--) {
                        r2 = matrix[i - 1][1];
                        matrix[i - 1][1] = r1;
                        r1 = r2;
                    }
                }
                case 6: {
                    market.external = matrix[0][Y - 4];
                    for (int i = X; i > 0; i--) {
                        r2 = matrix[i - 1][0];
                        matrix[i - 1][0] = r1;
                        r1 = r2;
                    }
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }


        }

    }

    /* Da Rivedere */

    abstract class Card {
        public int  id = 0;
        private final int pntWin = 0;
        public boolean flipped = true;

        public int getPntWin ( Card card ) {
            return card.pntWin;
        }

    }

    class CardDev extends Card {
        private int lvl;
        private Resources cost;

        public CardDev ( int id ) {
            this.id = id;

        }
        /*get per i requisiti*/
        public int getLvl(int id){
            CardDev c = null;
            /*cerca la carta giusta*/
            return c.lvl;
        }
        /*get per gli effetti*/
        /*get per i punti vittoria a fine partita ereditato dal fatto che è carta*/
    }

    class CardLead extends Card {
        CardDev req1;
        Resources req2;
        String special;

        CardLead ( int id ) {
            this.id = id;
        }
        public CardDev getReq1 (int id){
            CardLead c = null;
            /*cerca la carta giusta*/
            return c.req1;
        }
        public Resources getReq2(int id){
            CardLead c = null;
            /*cerca la carta giusta*/
            return c.req2;
        }

        /*get per i requisiti*/
        /*metodi per il potere speciale*/
        /*get per i punti vittoria a fine partita ereditato dal fatto che è carta*/
    }

    class PopeFav extends Card {
        int id;
        int pos;

        public void setPopeFav ( int id, int position ) {
            PopeFav c = null;
            /*cerca la carta giusta*/
            c.pos = position; /*set la posizione*/
        }
        /*get per i punti vittoria a fine partita ereditato dal fatto che è carta*/
    }
}
