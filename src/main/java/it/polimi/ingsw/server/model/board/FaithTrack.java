package it.polimi.ingsw.server.model.board;

import java.util.Arrays;

public class FaithTrack{

    private final boolean[] popeFavor = new boolean[3];
    private int position;
    private int popeFavorWinPnt;

    public FaithTrack(){
        position = 0;
        popeFavorWinPnt = 0;
        Arrays.fill(popeFavor, false);
    }

    protected int getPopeFavorWinPnt(){
        return popeFavorWinPnt;
    }

    protected void moveForward(int step){
        position += step;
        if((position >= 8) && !popeFavor[0]) System.out.println("(Method Call): Check Vatican Report");
        if((position >= 16) && !popeFavor[1]) System.out.println("(Method Call): Check Vatican Report");
        if((position >= 24) && !popeFavor[2]) System.out.println("(Method Call): Check Vatican Report");
    }

    protected int getWinPnt(){
        var win = popeFavorWinPnt;
        win += getWinPntPosition();
        return win;
    }

    protected int getWinPntPosition(){
        return switch(position){
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

    protected void getWinPntCard(){
        switch(position){
            case 5, 6, 7, 8:
                if(!popeFavor[0]){
                    popeFavor[0] = true;
                    popeFavorWinPnt += 2;
                }
                break;
            case 12, 13, 14, 15, 16:
                if(!popeFavor[1]){
                    popeFavor[1] = true;
                    popeFavorWinPnt += 3;
                }
                break;
            case 19, 20, 21, 22, 23, 24:
                if(!popeFavor[2]){
                    popeFavor[2] = true;
                    popeFavorWinPnt += 4;
                }
                break;
            default:
                break;
        }
    }

    protected int getPosition(){
        return position;
    }
}
