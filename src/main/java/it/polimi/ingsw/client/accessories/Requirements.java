package it.polimi.ingsw.client.accessories;

import it.polimi.ingsw.server.accessories.Colours;
import it.polimi.ingsw.server.accessories.ColoursCards;
import it.polimi.ingsw.server.accessories.Pick;

public interface Requirements {

    void getRequirements();

    @Override
    String toString();


    class RequirementsCard implements Requirements {

        private final it.polimi.ingsw.server.accessories.ColoursCards type;
        private final int cardLvl;

        public RequirementsCard(int cardLvl, it.polimi.ingsw.server.accessories.ColoursCards c) {
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

        }

        @Override
        public String toString() {
            return "{" +
                    "type=" + type +
                    ", cardLvl=" + cardLvl +
                    '}';
        }
    }

    class RequirementsResources implements Requirements {

        private it.polimi.ingsw.server.accessories.Colours type;
        private int qnt;

        public RequirementsResources(it.polimi.ingsw.server.accessories.Pick[] picks) {
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
