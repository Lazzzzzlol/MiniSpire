package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card14Interject extends EffectCard{
	
	String name = "Interject";
	int cost = 1;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}