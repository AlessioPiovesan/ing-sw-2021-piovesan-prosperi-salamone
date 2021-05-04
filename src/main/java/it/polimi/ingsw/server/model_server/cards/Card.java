package it.polimi.ingsw.server.model_server.cards;

interface Card {
    int getWinPnt();

    boolean getFlipped();

    void flipCard();
}
