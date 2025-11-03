package main.card.attackCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card04AbdomenTear extends AttackCard{
	
	String name = "Abdomen Tear";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}

