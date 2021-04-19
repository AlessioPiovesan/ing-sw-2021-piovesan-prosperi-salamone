package it.polimi.ingsw.server.model_server.plancia;

import it.polimi.ingsw.server.model_server.carte.CardPopeFav;

import java.util.ArrayList;
import java.util.List;


public class FaithTrack{

    private final ArrayList<Integer> popeFavorPos = new ArrayList<>();
    protected int position;
    protected CardPopeFav[] playerPFC = new CardPopeFav[3];

    public FaithTrack ( ){
        position = 0;
        popeFavorPos.add(8);
        popeFavorPos.add(16);
        popeFavorPos.add(24);
    }

    public void setPosition (int step){
        position += step;
    }

    public void setCard (List<CardPopeFav> cards){
        playerPFC[0] = cards.get(0);
        playerPFC[1] = cards.get(0);
        playerPFC[2] = cards.get(0);
    }

    public int getPosition ( ){
        return position;
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

    public CardPopeFav getCard (int position){
        return playerPFC[popeFavorPos.indexOf(position)];
    }
}
