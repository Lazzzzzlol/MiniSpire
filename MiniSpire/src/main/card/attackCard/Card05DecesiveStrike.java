package main.card.attackCard;

import main.buff.debuff.BuffVulnerable;
import main.enemy.Enemy;
import main.player.Player;

public class Card05DecesiveStrike extends AttackCard{
	
	String name = "Decisive Strike";
	int cost = 2;

	@Override
	public void onPlay(Player player, Enemy enemy) {
		enemy.deductHp(15);
		enemy.addBuff(new BuffVulnerable(1), 1);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getCost() {
		return cost;
	}
}
