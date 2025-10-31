package main.card.attackCard;

import main.buff.debuff.BuffWeaken;
import main.enemy.Enemy;
import main.player.Player;

public class CardOnslaught extends AttackCard{

	String name = "Onslaught";
	int cost = 1;

	@Override
	public void onPlay(Player player, Enemy enemy) {
		enemy.deductHp(8);
		enemy.addBuff(new BuffWeaken(1), 1);
	}
	
	@Override
	public String getName() {
		return name;
	}
}
