package it.polimi.ingsw.server.accessories;

/**
 * Interface Requirements handles Card Leader's requirements
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 */
public interface Requirements{

    @Override
    String toString();

    /**
     * Class RequirementsCard handles Card Leader's requirements (Development Cards)
     *
     * @author Alessio Piovesan
     * @author Pier Giorgio Prosperi
     * @author Matteo Salamone
     * @see Requirements
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
     * Class RequirementsResources handles Cards' requirements (Resources)
     *
     * @author Alessio Piovesan
     * @author Pier Giorgio Prosperi
     * @author Matteo Salamone
     * @see Requirements
     */
    class RequirementsResources implements Requirements{

        private Colours type;
        private int qnt;

        public RequirementsResources(Pick[] picks){
            for(Pick pick : picks){
                this.type = pick.resource();
                this.qnt = pick.qnt();
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
