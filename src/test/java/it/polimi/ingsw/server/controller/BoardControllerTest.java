package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.board.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardControllerTest{

    @Test
    void getterTest(){
        var board = new Board(0, null, null, null, null);
        var boardController = new BoardController(board);
        assertEquals(board, boardController.getPlayer());
    }
}