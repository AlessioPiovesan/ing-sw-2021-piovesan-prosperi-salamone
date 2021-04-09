package it.polimi.ingsw.Server.model_Server.Carte;

import it.polimi.ingsw.Server.model_Server.Accessori.Requirements;

public interface ICardLeader{
    Requirements[] getReq ( );

    String getSpecial ( );
}
