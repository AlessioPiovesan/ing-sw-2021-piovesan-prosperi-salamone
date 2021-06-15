package it.polimi.ingsw.server.accessories;

public interface Requirements{

    @Override
    String toString();

    /**
     * class RequirementsCard handles Card Leader's requirements (Development Cards)
     * @see Requirements
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */

    class RequirementsCard implements Requirements{

        private final ColoursCards type;
        private final int cardLvl;

        public RequirementsCard(int cardLvl, ColoursCards c){
            this.cardLvl = cardLvl;
            this.type = c;
        }

        public int getCardLvl(){
            return cardLvl;
        }

        public ColoursCards getColour(){
            return type;
        }

        @Override
        public String toString(){
            return "A level " + cardLvl + " " + ColoursCards.colored(type.name()) + " color card";
        }
    }

    /**
     * class RequirementsResources handles Cards' requirements (resources)
     * @see Requirements
     * @author Alessio Piovesan, Pier Giorgio Prosperi and Matteo Salamone.
     */
    class RequirementsResources implements Requirements{

        private Colours type;
        private int qnt;

        public RequirementsResources(Pick[] picks){
            for(Pick pick : picks){
                this.type = pick.getResource();
                this.qnt = pick.getQnt();
            }
        }

        public int getQnt(){
            return qnt;
        }

        public Colours getColour(){
            return type;
        }

        @Override
        public String toString(){
            return qnt + " " + type.getType();
        }
    }
}
