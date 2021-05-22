package it.polimi.ingsw.server.controller_server;

import it.polimi.ingsw.server.model_server.board.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardControllerTest {

    @Test
    void getterTest() {
        var board = new Board(0, null, null, null, null);
        var boardController = new BoardController(board);
        assertEquals(board, boardController.getPlayer());
    }

}