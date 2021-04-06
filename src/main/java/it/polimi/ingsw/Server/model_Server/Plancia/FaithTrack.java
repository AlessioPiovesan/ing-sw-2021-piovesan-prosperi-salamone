package it.polimi.ingsw.Server.model_Server.Plancia;

import it.polimi.ingsw.Server.model_Server.Carte.CardPopeFav;


public class FaithTrack{

    private final int[] popeFavorPos = {8, 16, 24};
    protected int position;
    protected CardPopeFav[] playerPFC;

    protected FaithTrack ( ){
        position = 0;
        setCard();
    }

    public void setPosition (int step){
        position += step;
    }

    public void setCard ( ){
        for (int i = 0;i < 3;i++){
            System.out.println("Trova la carta e assegnala a myPFC[i]"); /*DA IMPLEMENTARE*/
        }
    }

    public int getWinPnt ( ){
        switch (position){
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
            case 5:
                return 1;
            case 6:
            case 7:
            case 8:
                return 2;
            case 9:
            case 10:
            case 11:
                return 4;
            case 12:
            case 13:
            case 14:
                return 6;
            case 15:
            case 16:
            case 17:
                return 9;
            case 18:
            case 19:
            case 20:
                return 12;
            case 21:
            case 22:
            case 23:
                return 16;
            case 24:
                return 20;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    public boolean checkPopeSpace ( ){
        switch (position){
            case 5:
            case 6:
            case 7:
            case 8:

            case 12:
            case 13:
            case 14:
            case 15:
            case 16:

            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return true;
            default:
                return false;
        }
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
