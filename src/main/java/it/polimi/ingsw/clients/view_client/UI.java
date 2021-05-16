package it.polimi.ingsw.clients.view_client;

import it.polimi.ingsw.server.accessories.cards.Card;
import it.polimi.ingsw.server.accessories.cards.CardLeader;

import java.util.List;

public interface UI {
    void showCards(List<CardLeader> list);
}
