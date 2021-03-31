package it.polimi.ingsw.Server.model_Server.Accessori;

public abstract class Requirements{

    public void getRequirements( ){
    }

    public static class RequirementsCard extends Requirements{

        private Colours type;
        private int cardLvl;

        public RequirementsCard( int cardLvl, Colours c ){
            this.cardLvl = cardLvl;
            this.type = c;
        }

        public int getCardLvl( ){
            return cardLvl;
        }

        public Colours getType( ){
            return type;
        }
    }

    static class RequirementsResources extends Requirements{

        private Colours[] type;

        public RequirementsResources( Colours[] type ){
            this.type = type;
        }

        public Colours[] getType( ){
            return type;
        }
    }
}
