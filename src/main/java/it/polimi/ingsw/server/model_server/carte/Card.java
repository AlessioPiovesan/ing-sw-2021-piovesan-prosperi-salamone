package it.polimi.ingsw.server.model_server.carte;

interface Card{
    int getWinPnt ( );

    boolean getFlipped ( );

    void flipCard ( );
}
