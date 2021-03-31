package it.polimi.ingsw.Server.model_Server;

abstract class Requirements{
    public void getRequirements ( ){
    }

    static class RequirementsCard extends Requirements{

        private Colours type;
        private int cardLvl;

        RequirementsCard(int cardLvl, Colours c){
            this.cardLvl=cardLvl;
            this.type=c;
        }

        public int getCardLvl ( ){
            return cardLvl;
        }
        public Colours getType ( ){
            return type;
        }
    }

    static class RequirementsResources extends Requirements{

        private Resource[] type;

        public Resource[] getType ( ){
            return type;
        }
    }
}
