package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card10Comeuppance extends EffectCard{
	
	String name = "Comeuppance";
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