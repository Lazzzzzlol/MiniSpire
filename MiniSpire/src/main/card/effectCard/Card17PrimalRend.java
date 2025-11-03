package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card17PrimalRend extends EffectCard{
	
	String name = "Primal Rend";
	int cost = 3;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}