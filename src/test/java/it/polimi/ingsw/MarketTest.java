package it.polimi.ingsw;

import it.polimi.ingsw.Server.model_Server.Accessori.Colours;

import java.util.LinkedList;

public class MarketTest{

    Colours[][] matrixStart;
    Colours externalStart;

    public MarketTest ( ){
        matrixStart = new Colours[4][3];
        matrixStart[0][0] = Colours.BLUE;
        matrixStart[0][1] = Colours.GREY;
        matrixStart[0][2] = Colours.PURPLE;
        matrixStart[1][0] = Colours.WHITE;
        matrixStart[1][1] = Colours.RED;
        matrixStart[1][2] = Colours.BLUE;
        matrixStart[2][0] = Colours.GREY;
        matrixStart[2][1] = Colours.PURPLE;
        matrixStart[2][1] = Colours.WHITE;
        matrixStart[3][0] = Colours.YELLOW;
        matrixStart[3][1] = Colours.YELLOW;
        matrixStart[3][2] = Colours.WHITE;
        externalStart = Colours.WHITE;
    }

    public LinkedList<Colours> getResourcesTest (int val){
        LinkedList<Colours> list = new LinkedList<>();
        switch (val){
            case 0:{
                list.add(Colours.BLUE);
                list.add(Colours.GREY);
                list.add(Colours.PURPLE);
                list.add(Colours.WHITE);
            }
            case 1:{
                list.add(Colours.RED);
                list.add(Colours.BLUE);
                list.add(Colours.GREY);
                list.add(Colours.PURPLE);
            }
            case 2:{
                list.add(Colours.WHITE);
                list.add(Colours.YELLOW);
                list.add(Colours.YELLOW);
                list.add(Colours.WHITE);
            }
            case 3:{
                list.add(Colours.WHITE);
                list.add(Colours.PURPLE);
                list.add(Colours.WHITE);
            }
            case 4:{
                list.add(Colours.PURPLE);
                list.add(Colours.GREY);
                list.add(Colours.YELLOW);
            }
            case 5:{
                list.add(Colours.GREY);
                list.add(Colours.BLUE);
                list.add(Colours.YELLOW);
            }
            case 6:{
                list.add(Colours.BLUE);
                list.add(Colours.RED);
                list.add(Colours.WHITE);
            }
            default:
                list.add(null);
        }
        return list;
    }

    public Colours getExternal (int val){
        switch (val){
            case 0:
                return Colours.BLUE;
            case 1:
                return Colours.RED;
            case 2:
                return Colours.WHITE;
            case 3:
                return Colours.WHITE;
            case 4:
                return Colours.PURPLE;
            case 5:
                return Colours.GREY;
            case 6:
                return Colours.BLUE;
            default:
                return null;
        }
    }
}
