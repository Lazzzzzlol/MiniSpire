package main.card.passiveCard;

public class Card21BlessingOfTheErdtree extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Blessing of the Erdtree";
	String rarity = "epic";
	int cost = 0;
	
	@Override
	public void onDiscard() {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}
