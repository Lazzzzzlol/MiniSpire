package main.card.attackCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card06LifeDrain extends AttackCard{
	
	String name = "Life Drain";
	int cost = 2;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}

