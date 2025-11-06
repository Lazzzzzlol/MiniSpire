package main.card.passiveCard;

public class Card20OldRadiantLifegem extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Old Radiant Lifegem";
	String info = "When discarded, heal 12 hp and apply 3 round Recovery to seld;";
	String rarity = "rare";
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
