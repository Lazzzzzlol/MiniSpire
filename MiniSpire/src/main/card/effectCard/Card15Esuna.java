package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card15Esuna extends EffectCard{
	
	String name = "Esuna";
	String rarity = "rare";
	int cost = 2;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}