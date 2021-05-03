package it.polimi.ingsw.server.accessori;

public interface Requirements {

    void getRequirements();


    class RequirementsCard implements Requirements {

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

        }
    }

    class RequirementsResources implements Requirements {

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

        }
    }
}
