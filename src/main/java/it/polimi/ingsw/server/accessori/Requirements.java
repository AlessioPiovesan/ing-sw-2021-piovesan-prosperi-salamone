package it.polimi.ingsw.server.accessori;

public abstract class Requirements{

    public void getRequirements ( ){
    }

    public static class RequirementsCard extends Requirements{

        private final ColoursCards type;
        private final int cardLvl;

        public RequirementsCard (int cardLvl, ColoursCards c){
            this.cardLvl = cardLvl;
            this.type = c;
        }

        public int getCardLvl ( ){
            return cardLvl;
        }

        public ColoursCards getColour ( ){
            return type;
        }
    }

    public static class RequirementsResources extends Requirements{

        private Colours type;
        private int qnt;

        public RequirementsResources (Pick[] picks){
            for (Pick pick: picks){
                this.type = pick.getResource();
                this.qnt = pick.getQnt();
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
