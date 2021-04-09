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

        public Colours getColour ( ){
            return type;
        }
    }

    public static class RequirementsResources extends Requirements{

        private Colours type;
        private int qnt;

        public RequirementsResources (Pick[] picks){
            for (Pick pick: picks){
                this.type = pick.r;
                this.qnt = pick.qnt;
            }
        }

        public int getQnt ( ){
            return qnt;
        }

        public Colours getColour ( ){
            return type;
        }
    }
}
