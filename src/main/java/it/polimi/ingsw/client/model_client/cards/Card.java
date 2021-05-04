package it.polimi.ingsw.client.model_client.cards;

interface Card {
    int getWinPnt();

    boolean getFlipped();

    void flipCard();
}
