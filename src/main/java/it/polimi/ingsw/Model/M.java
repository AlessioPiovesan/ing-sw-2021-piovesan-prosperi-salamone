package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class M {

    class game {
        static final byte XMARKET = 4;
        static final byte YMARKET = 3;
        static final byte MAXTRACK = 20;
        static final byte MAXMARBLESW = 4;
        static final byte MAXMARBLESB = 2;
        static final byte MAXMARBLESG = 2;
        static final byte MAXMARBLESY = 2;
        static final byte MAXMARBLESP = 2;
        static final byte MAXMARBLESR = 1;

        /*final HashMap<Integer, card> CARDS = new HashMap<Integer, card>();*/

        board[] b;
        LinkedList<resources> r;
        market m;

        game ( int gamers ) {

            if (gamers > 1) {
                /* Board creation for each player */
                for (byte i = 0; i < gamers; i++) this.b[i] = new board(i);
            } else {
                /* SINGLE PLAYER */
            }
            /* Market creation for each game */
            this.m = new market(XMARKET, YMARKET);
            /* Resources creation for each game*/
            for (byte i = (byte) this.r.size(); i < MAXMARBLESB * 8 + this.r.size(); i++)
                this.r.add(new resources("blue"));
            for (byte i = (byte) this.r.size(); i < MAXMARBLESG * 8 + this.r.size(); i++)
                this.r.add(new resources("grey"));
            for (byte i = (byte) this.r.size(); i < MAXMARBLESP * 8 + this.r.size(); i++)
                this.r.add(new resources("purple"));
            for (byte i = (byte) this.r.size(); i < MAXMARBLESY * 8 + this.r.size(); i++)
                this.r.add(new resources("yellow"));
            /* Card creation for each game */
            /*...*/
        }
    }

    class board {
        byte idD;
        warehouse w;
        strongBox v;
        byte positionTF;
        library[] l;
        /*track[] t;*/

        board ( byte id ) {
            this.idD = id;
            /* Warehouse creation for a single board */
            this.w = new warehouse(id);
            /* Strongbox creation for a single board */
            this.v = new strongBox(id);
        }

            /*private class track {

                byte id;

                track ( byte id ) {
                    this.id = id;
                }
            }*/

        private class library {
            byte id;
            HashMap<Integer, card> l;

            library ( byte id ) {
                this.id = id;
                /*creazione scompartimenti per una libreria di una plancia*/
                 this.l = new HashMap<>();
                 l.put(1, null);
                 l.put(2, null);
                 l.put(3, null);
            }
        }

        private class strongBox {
            byte id;
            HashMap<String, Integer> comp;

            strongBox ( byte id ) {
                this.id = id;
                comp = new HashMap<>();
                comp.put("blue", 0);
                comp.put("grey", 0);
                comp.put("purple", 0);
                comp.put("yellow", 0);
            }
        }

        private class warehouse {
            int id;
            HashMap<Integer, resources> wareContent;

            warehouse ( int id ) {
                this.id = id;
                this.wareContent = new HashMap<>();
                wareContent.put(1, null);
                wareContent.put(2, null);
                wareContent.put(3, null);
            }
        }
    }

    public class resources {

        String colour;

        resources ( String color ) {
            this.colour = color;
        }

        public String getType ( String color ) {
            switch (color) {
                case "blue":
                    return "Shield";
                case "grey":
                    return "Stone";
                case "purple":
                    return "Servant";
                case "red":
                    return "Faith";
                case "white":
                    return "";
                case "yellow":
                    return "Coin";
                default:
                    return "ERROR!";
            }
        }
    }

    public class market {
        marble[][] matrix;
        marble external;
        LinkedList<marble> marbles;
        LinkedList<Integer> list;
        Random random = new Random();

        market ( int maxX, int maxY ) {
            /*creo le biglie*/
            for (byte i = (byte) this.marbles.size(); i < game.MAXMARBLESB + this.marbles.size(); i++)
                this.marbles.add(new marble("blue"));
            for (byte i = (byte) this.marbles.size(); i < game.MAXMARBLESG + this.marbles.size(); i++)
                this.marbles.add(new marble("grey"));
            for (byte i = (byte) this.marbles.size(); i < game.MAXMARBLESP + this.marbles.size(); i++)
                this.marbles.add(new marble("purple"));
            for (byte i = (byte) this.marbles.size(); i < game.MAXMARBLESR + this.marbles.size(); i++)
                this.marbles.add(new marble("red"));
            for (byte i = (byte) this.marbles.size(); i < game.MAXMARBLESW + this.marbles.size(); i++)
                this.marbles.add(new marble("white"));
            for (byte i = (byte) this.marbles.size(); i < game.MAXMARBLESY + this.marbles.size(); i++)
                this.marbles.add(new marble("yellow"));
            /*metto le biglie nel mercato*/
            this.matrix= fillMarket(maxX, maxY, marbles);
            /*segno qual è la 13sima biglia*/
            this.external=setExternal(list, marbles);
        }

        private marble[][] fillMarket ( int X, int Y, LinkedList<marble> marbles ) {
            this.list = new LinkedList<>();
            for (byte j = 0; j < Y; j++) {
                for (byte i = 0; i < X; i++) {
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

        private marble setExternal ( LinkedList<Integer> list, LinkedList<marble> marbles ) {
            /*cerca nella lista delle biglie la tredicesima biglia, ecludendo le dodici che stanno nella lista di biglie che sono nel mercato*/
            return external;
        }

        public class marble {
            resources res;

            marble ( String colour ) {
                /*costruttore della biglia*/
            }
        }
    }

    class card {
        int id;
        int pntWin;

        class cDev extends card {
            int lvl;
            resources cost;

            cDev ( int id ) {
                this.id = id;

            }
            /*get per i requisiti*/
            /*get per gli effetti*/
            /*get per i punti vittoria a fine partita*/
        }

        class cLead extends card {
            cDev req1;
            resources req2;
            String special;

            cLead ( int id ) {
                this.id = id;
            }

            /*get per i requisiti*/
            /*metodi per il potere speciale*/
            /*get per i punti vittoria a fine partita*/
        }

        class popeFav extends card {
            int pos;

            popeFav ( int position ) {
                this.pos = position;
            }
            /*get per i punti vittoria a fine partita*/
        }
    }

}
