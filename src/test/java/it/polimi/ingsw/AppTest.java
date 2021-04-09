package it.polimi.ingsw;

import it.polimi.ingsw.Server.model_Server.Gioco.Market;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest{
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 9})
    void move (int value){
        Market market = new Market();
        MarketTest marketTest = new MarketTest();
        market.matrix = marketTest.matrixStart;
        market.external=marketTest.externalStart;
        assertEquals(marketTest.getResourcesTest(value), market.moveMarbles(value));
        assertEquals(marketTest.getExternal(value), market.external);
    }

}

