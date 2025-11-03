package main.card.attackCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card07Ruination extends AttackCard{
	
	String name = "Ruination";
	int cost = 3;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}

