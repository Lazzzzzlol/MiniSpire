package main.card.passiveCard;

public class Card19RecoveryStone extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Recovery Stone";
	String info = "When discarded, heal 6 hp. ";
	String rarity = "normal";
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
