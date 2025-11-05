package main.card.passiveCard;

import main.card.Card;

public class PassiveCard implements Card{

	private Boolean canPlay = false;
	private String name = "Generic Passive Card";
	private int cost = 0;
	private String type = "Passive";
	private String rarity = "normal";
	
	public void onDiscard() {};
	
	@Override
	public Boolean getCanPlay() {
		return canPlay;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getRarity(){
		return rarity;
	}
}
