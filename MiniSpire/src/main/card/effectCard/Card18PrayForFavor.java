package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card18PrayForFavor extends EffectCard{
	
	String name = "Pray for Favor";
	String rarity = "legendary";
	int cost = 4;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}