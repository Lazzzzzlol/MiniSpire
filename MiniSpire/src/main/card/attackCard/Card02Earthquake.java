package main.card.attackCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card02Earthquake extends AttackCard{
	
	String name = "Earthquake";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		enemy.deductHp(3);
        enemy.deductHp(3);
        enemy.deductHp(3);
	}
	
	@Override
	public String getName() {
		return name;
	}
}

