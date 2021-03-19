package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class M {

    static class game {
        static final int XMARKET = 4;
        static final int YMARKET = 3;
        static final int MAXTRACK = 20;
        static final int MAXMARBLESW = 4;
        static final int MAXMARBLESB = 2;
        static final int MAXMARBLESG = 2;
        static final int MAXMARBLESY = 2;
        static final int MAXMARBLESP = 2;
        static final int MAXMARBLESR = 1;

        final HashMap<Integer, card> CARDS = new HashMap<Integer, card>();

        board b[];
        resources r[];
        market m;

        game ( int gamers ) {

            if (gamers > 1) {
                for (int i = 0; i < gamers; i++) {
                    this.b[i] = new board(i);
                }
            } else {
            }
            this.m = new market(XMARKET, YMARKET);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    CARDS.put(i * j, new card());
                }
            }
        }

        class board {
            int idD;
            warehouse w;
            trunk v;
            int position;
            library l[];
            track t[];

            board ( int id ) {
                this.idD = id;
                this.w = new warehouse(id);
                this.v = new trunk(id);
            }

            private class track {
                int id;

                track ( int id ) {
                    this.id = id;
                }
            }

            private class library {
                int id;

                library ( int id ) {
                    this.id = id;
                    HashMap<Integer, card> b = new HashMap<>();
                }
            }

            private class trunk {
                int id;

                trunk ( int id ) {
                    this.id = id;
                    HashMap<Integer, HashMap<resources, Integer>> trunkContent = new HashMap<>();
                }
            }

            private class warehouse {
                int id;

                warehouse ( int id ) {
                    this.id = id;
                    HashMap<resources, Integer> wareContent = new HashMap<>();
                }
            }
        }

        enum resources {
            BLUE("Sheild"), GREY("Stone"), PURPLE("Servant"), RED("Faith"), WHITE(null), YELLOW("Coin");


            String type;

            resources ( String type ) {
                this.type=type;
            }

            }

        public class market {
            int[][] matrix;
            LinkedList<marble> marbles;
            Random random = new Random();

            market ( int maxX, int maxY ) {

                this.matrix = new int[maxX][maxY];
                for (int i = this.marbles.size(); i < MAXMARBLESB+this.marbles.size(); i++) this.marbles.add( new marble("blue"));
                for (int i = this.marbles.size(); i < MAXMARBLESG+this.marbles.size(); i++) this.marbles.add( new marble("grey"));
                for (int i = this.marbles.size(); i < MAXMARBLESP+this.marbles.size(); i++) this.marbles.add( new marble("purple"));
                for (int i = this.marbles.size(); i < MAXMARBLESR+this.marbles.size(); i++) this.marbles.add( new marble("red"));
                for (int i = this.marbles.size(); i < MAXMARBLESW+this.marbles.size(); i++) this.marbles.add( new marble("white"));
                for (int i = this.marbles.size(); i < MAXMARBLESY+this.marbles.size(); i++) this.marbles.add( new marble("yellow"));
                fillMarket(maxX, maxY, marbles);
            }

            private void fillMarket ( int X, int Y, LinkedList<marble> marbles ) {
                for (int j = 0; j < Y; j++) {
                    for (int i = 0; i < X; i++) {
                        matrix[i][j] = random.nextInt(j * i);
                    }
                }
            }

            public class marble {
                resources res;

                marble ( String colour ) {

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
            }

            class cLead extends card {
                cDev req1;
                resources req2;
                String special;

                cLead ( int id ) {
                    this.id = id;
                }
            }

            class popeFav extends card {
                int pos;

                popeFav ( int position ) {
                    this.pos = position;
                }
            }
        }

    }
}
