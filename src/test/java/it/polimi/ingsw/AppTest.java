package it.polimi.ingsw;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest{

    private static Stream<Integer>
    supplyOdds ( ){
        return Stream.of(1, 3, 5, 7, 9, 11);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 8, 120})
    void evens (int value){
        assertEquals(0, value % 2);
    }

    @ParameterizedTest
    @MethodSource("supplyOdds")
    void odds (int value){
        assertEquals(1, value % 2);
    }

}

