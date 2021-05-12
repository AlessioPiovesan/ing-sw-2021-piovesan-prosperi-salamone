package it.polimi.ingsw.server.accessories.cards;

interface Card {
    int getWinPnt();

    boolean getFlipped();

    void flipCard();
}
