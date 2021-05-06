package it.polimi.ingsw.client.accessories;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;

public interface Requirements {


    void getRequirements();

    @Override
    String toString();


    class RequirementsCard implements it.polimi.ingsw.server.accessories.Requirements {

        private final ColoursCards type;
        private final int cardLvl;

        public RequirementsCard(int cardLvl, ColoursCards c) {
            this.cardLvl = cardLvl;
            this.type = c;
        }

        public int getCardLvl() {
            return cardLvl;
        }

        public ColoursCards getColour() {
            return type;
        }

        @Override
        public void getRequirements() {
            getCardLvl();
            getColour();
        }

        @Override
        public String toString() {
            return "{" +
                    "type=" + type +
                    ", cardLvl=" + cardLvl +
                    '}';
        }
    }

    class RequirementsResources implements it.polimi.ingsw.server.accessories.Requirements {

        private Colours type;
        private int qnt;

        public RequirementsResources(Pick[] picks) {
            for (Pick pick : picks) {
                this.type = pick.getResource();
                this.qnt = pick.getQnt();
            }
        }

        public int getQnt() {
            return qnt;
        }

        public Colours getColour() {
            return type;
        }

        @Override
        public void getRequirements() {
            getQnt();
            getColour();
        }

        @Override
        public String toString() {
            return "{" +
                    "type=" + type.getType() +
                    ", qnt=" + qnt +
                    '}';
        }
    }
}
