package main.card.passiveCard;

public class Card21BlessingOfTheErdtree extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Blessing of the Erdtree";
	String info = "When discarded, apply 3 round Strengthened, 3 round Tough to self. ";
	String rarity = "epic";
	int cost = 0;
	
	@Override
	public void onDiscard() {
		
	}

	@Override
	public String getInfo(){
		return info;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
