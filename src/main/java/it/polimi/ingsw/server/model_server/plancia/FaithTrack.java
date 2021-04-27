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

    public void setCard (CardPopeFav[] cards){
        playerPFC[0] = cards[0];
        playerPFC[1] = cards[1];
        playerPFC[2] = cards[2];
    }

    public int getPosition ( ){
        return position;
    }

    public void setPosition (int step){
        position += step;
    }

    public int getWinPnt(){
        int win =0;
       win += getWinPntCard();
       win += getWinPntPosition();
       return win;
    }

    public int getWinPntPosition ( ){
        return switch (position){
            case 0, 1, 2 -> 0;
            case 3, 4, 5 -> 1;
            case 6, 7, 8 -> 2;
            case 9, 10, 11 -> 4;
            case 12, 13, 14 -> 6;
            case 15, 16, 17 -> 9;
            case 18, 19, 20 -> 12;
            case 21, 22, 23 -> 16;
            case 24 -> 20;
            default -> throw new IllegalStateException("Unexpected value: " + position);
        };
    }

    public int getWinPntCard ( ){
        int win = 0;
        for (CardPopeFav cardPopeFav: playerPFC){
            if (cardPopeFav.getFlipped())
                win += cardPopeFav.getWinPnt();
        }
        return win;
    }


    public int checkPopeSpace ( ){
        return switch (position){
            case 5, 6, 7, 8 -> 0;
            case 12, 13, 14, 15, 16 -> 1;
            case 19, 20, 21, 22, 23, 24 -> 2;
            default -> -1;
        };
    }

    public CardPopeFav getCard (int position){
        return playerPFC[popeFavorPos.indexOf(position)];
    }

}
