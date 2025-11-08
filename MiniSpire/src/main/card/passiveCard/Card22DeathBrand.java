package main.card.passiveCard;

import main.player.Player;

public class Card22DeathBrand extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Death Brand";
	String info = "When discarded, take 4 damage. ";
	String rarity = "special";
	int cost = 0;
	
	@Override
	public void onDiscard() {
		Player player = Player.getInstance();
		player.deductHp(4);
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
