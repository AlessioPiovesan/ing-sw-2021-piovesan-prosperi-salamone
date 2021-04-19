package it.polimi.ingsw.server.model_server.gioco;

import it.polimi.ingsw.server.accessori.Colours;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarketTest extends Market{

    MarketTest ( ){
        tray = new Colours[4][3];
        tray[0][0] = Colours.BLUE;
        tray[0][1] = Colours.RED;
        tray[0][2] = Colours.WHITE;
        tray[1][0] = Colours.GREY;
        tray[1][1] = Colours.BLUE;
        tray[1][2] = Colours.YELLOW;
        tray[2][0] = Colours.PURPLE;
        tray[2][1] = Colours.GREY;
        tray[2][2] = Colours.YELLOW;
        tray[3][0] = Colours.WHITE;
        tray[3][1] = Colours.PURPLE;
        tray[3][2] = Colours.WHITE;
        external = Colours.WHITE;
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

    public LinkedList<Colours> getResourcesTest (int val){
        LinkedList<Colours> list = new LinkedList<>();
        switch (val){
            case 0:
                list.add(Colours.BLUE);
                list.add(Colours.GREY);
                list.add(Colours.PURPLE);
                list.add(Colours.WHITE);
                break;
            case 1:
                list.add(Colours.RED);
                list.add(Colours.BLUE);
                list.add(Colours.GREY);
                list.add(Colours.PURPLE);
                break;
            case 2:
                list.add(Colours.WHITE);
                list.add(Colours.YELLOW);
                list.add(Colours.YELLOW);
                list.add(Colours.WHITE);
                break;
            case 3:
                list.add(Colours.WHITE);
                list.add(Colours.PURPLE);
                list.add(Colours.WHITE);
                break;
            case 4:
                list.add(Colours.PURPLE);
                list.add(Colours.GREY);
                list.add(Colours.YELLOW);
                break;
            case 5:
                list.add(Colours.GREY);
                list.add(Colours.BLUE);
                list.add(Colours.YELLOW);
                break;
            case 6:
                list.add(Colours.BLUE);
                list.add(Colours.RED);
                list.add(Colours.WHITE);
                break;
            default:
                list.add(null);
                break;
        }
        return list;
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    void move (int value){
        Market market = new Market();
        MarketTest marketTest = new MarketTest();
        market.tray = marketTest.tray;
        market.external = marketTest.external;
        List<Colours> resourcesExpected = marketTest.getResourcesTest(value);
        List<Colours> resourcesActual = market.moveMarbles(value);
        assertEquals(resourcesExpected, resourcesActual);
        assertEquals(marketTest.getExternal(value), market.external);
    }
}