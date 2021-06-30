package it.polimi.ingsw.server.model;

/**
 * Class Mod is a class that contains every single game's Model.
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public class Mod{

    private static int idGame = 1000;

    Mod(){}

    /**
     * Method newID returns the Game's ID.
     *
     * @return Game ID of type int
     */
    public static int newID(){
        return ++idGame;
    }
}
