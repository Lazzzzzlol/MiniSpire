package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class CardBloodbath extends EffectCard{
	
	String name = "Bloodbath";
	int cost = 0;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}
