package main.card.passiveCard;

public class Card22DeathBrand extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Death Brand";
	String rarity = "normal";
	int cost = 0;
	
	@Override
	public void onDiscard() {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}
