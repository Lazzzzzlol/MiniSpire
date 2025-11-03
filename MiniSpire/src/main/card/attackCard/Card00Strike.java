package main.card.attackCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card00Strike extends AttackCard{
	
	String name = "Strike";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		enemy.deductHp(6);
	}
	
	@Override
	public String getName() {
		return name;
	}
}

