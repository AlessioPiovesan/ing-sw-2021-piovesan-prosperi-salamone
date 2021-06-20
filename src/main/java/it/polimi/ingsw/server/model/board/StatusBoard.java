package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.cards.CardDev;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.List;
import java.util.Map;

/**
 * Class StatusBoard is a data structure that records a board's status for client's usage.
 */
public record StatusBoard(Colours[][] warehouse, int faithTrack, Map<Colours, Integer> strongBox, List<CardDev> library,
        Map<Colours, Integer> resAll, CardLeader[] myLeaderCards){}
