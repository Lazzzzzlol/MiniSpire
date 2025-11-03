package main.card.attackCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card03Upheaval extends AttackCard{
	
	String name = "Upheaval";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		
	}
	
	@Override
	public String getName() {
		return name;
	}
}

