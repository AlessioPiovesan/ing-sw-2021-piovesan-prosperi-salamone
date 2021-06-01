package it.polimi.ingsw.server.accessories.cards;

public interface Card{

    int getWinPnt();

    boolean getFlipped();

    void flipCard();

    String toString();
}
