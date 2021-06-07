package it.polimi.ingsw.clients.singleplayer.model;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.FaithTrack;

public class Lorenzo extends Board{

    Lorenzo(int id, FaithTrack faithTrack){
        super(id, null, null, faithTrack, null);
    }
}
