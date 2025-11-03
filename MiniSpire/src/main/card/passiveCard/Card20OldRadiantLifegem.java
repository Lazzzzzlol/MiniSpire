package main.card.passiveCard;

public class Card20OldRadiantLifegem extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Old Radiant Lifegem";
	int cost = 0;
	
	@Override
	public void onDiscard() {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}
