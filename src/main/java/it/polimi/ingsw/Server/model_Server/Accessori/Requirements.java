package it.polimi.ingsw.Server.model_Server.Accessori;

public abstract class Requirements{

    public void getRequirements ( ){
    }

    public static class RequirementsCard extends Requirements{

        private final Colours type;
        private final int cardLvl;

        public RequirementsCard (int cardLvl, Colours c){
            this.cardLvl = cardLvl;
            this.type = c;
        }

        public int getCardLvl ( ){
            return cardLvl;
        }

        public Colours getType ( ){
            return type;
        }
    }

    public static class RequirementsResources extends Requirements{

        private final Pick[] picks;

        public RequirementsResources (Pick[] picks){
            this.picks = picks;
        }

        public Pick[] getType ( ){
            return picks;
        }
    }
}
