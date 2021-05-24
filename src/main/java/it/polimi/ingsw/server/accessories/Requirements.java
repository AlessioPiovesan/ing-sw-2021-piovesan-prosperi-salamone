package it.polimi.ingsw.server.accessories;

public interface Requirements{

	void getRequirements();

	@Override
	String toString();


	class RequirementsCard implements Requirements{

		private final ColoursCards type;
		private final int cardLvl;

		public RequirementsCard(int cardLvl, ColoursCards c){
			this.cardLvl = cardLvl;
			this.type = c;
		}

		@Override
		public void getRequirements(){
			getCardLvl();
			getColour();
		}

		public int getCardLvl(){
			return cardLvl;
		}

		public ColoursCards getColour(){
			return type;
		}

		@Override
		public String toString(){
			return "{" + "type=" + type + ", cardLvl=" + cardLvl + '}';
		}
	}

	class RequirementsResources implements Requirements{

		private Colours type;
		private int qnt;

		public RequirementsResources(Pick[] picks){
			for(Pick pick : picks){
				this.type = pick.getResource();
				this.qnt = pick.getQnt();
			}
		}

		@Override
		public void getRequirements(){
			getQnt();
			getColour();
		}

		public int getQnt(){
			return qnt;
		}

		public Colours getColour(){
			return type;
		}

		@Override
		public String toString(){
			return "{" + "type=" + type.getType() + ", qnt=" + qnt + '}';
		}
	}
}
