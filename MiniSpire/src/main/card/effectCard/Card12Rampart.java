package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card12Rampart extends EffectCard{
	
	String name = "Rampart";
	String rarity = "rare";
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}